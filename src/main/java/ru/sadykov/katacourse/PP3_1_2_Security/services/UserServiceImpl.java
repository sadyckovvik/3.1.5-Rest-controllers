package ru.sadykov.katacourse.PP3_1_2_Security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sadykov.katacourse.PP3_1_2_Security.models.Role;
import ru.sadykov.katacourse.PP3_1_2_Security.models.User;
import ru.sadykov.katacourse.PP3_1_2_Security.repositories.UserDao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, @Lazy PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void saveOrUpdateUser(User user) {
        Optional <User> userFromDB = findByUsername(user.getUsername());
        if (userFromDB.isEmpty()) {
            registerUser(user);
        } else {
            user.setRoles(userFromDB.get().getRoles());
            userDao.saveUser(user); // Сохраняем обновленного пользователя
        }
    }

    @Transactional
    @Override
    public void registerUser(User user) {
        Optional <User> userFromDB = findByUsername(user.getUsername());
        if(userFromDB.isEmpty()) {
            user.setRoles(Collections.singletonList(new Role(1L, "ROLE_USER")));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.saveUser(user);
        } else {
            throw new RuntimeException("Пользователь с таким именем уже занят");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUser(long id) {
        return userDao.getUser(id);
    }

    @Transactional
    @Override
    public void removeUser(long id) {
        userDao.removeUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
