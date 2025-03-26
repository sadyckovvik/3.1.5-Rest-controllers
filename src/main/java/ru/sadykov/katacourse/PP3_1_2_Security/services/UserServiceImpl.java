package ru.sadykov.katacourse.PP3_1_2_Security.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sadykov.katacourse.PP3_1_2_Security.models.Role;
import ru.sadykov.katacourse.PP3_1_2_Security.models.User;
import ru.sadykov.katacourse.PP3_1_2_Security.repositories.UserDao;
import ru.sadykov.katacourse.PP3_1_2_Security.util.UserNotCreatedException;
import ru.sadykov.katacourse.PP3_1_2_Security.util.UserIdNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserDao userDao, @Lazy PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional(rollbackFor = UserNotCreatedException.class)
    @Override
    public void saveUser(User user) {
        Optional<User> userFromDB = userDao.findByUsername(user.getUsername());
        if (userFromDB.isEmpty()) {
            List<String> rolesName = user.getRoles().stream().map(Role::getName).toList();
            user.setRoles(roleService.findsRolesByName(rolesName));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.saveUser(user);
        } else {
            throw new UserNotCreatedException();
        }
    }

    @Transactional
    @Override
    public void registerUser(User user) {
        Optional<User> userFromDB = userDao.findByUsername(user.getUsername());
        if (userFromDB.isEmpty()) {
            user.setRoles(Collections.singletonList(new Role(1L, "ROLE_USER")));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.saveUser(user);
        } else {
            throw new UserNotCreatedException();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(long id) {
        Optional<User> user = userDao.getUser(id);
        return user.orElseThrow(UserIdNotFoundException::new);
    }

    @Transactional
    @Override
    public void removeUser(long id) {
        userDao.removeUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        Optional<User> user = userDao.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }

    @Transactional
    @Override
    public void updateUser(Long id, User user) {
        User userFromDb = getUser(id);
        if (userFromDb != null) {
            userFromDb.setUsername(user.getUsername());
            userFromDb.setAge(user.getAge());
            List<String> rolesName = user.getRoles().stream().map(Role::getName).toList();
            userFromDb.setRoles(roleService.findsRolesByName(rolesName));
            if (!userFromDb.getPassword().equals(user.getPassword())) {
                userFromDb.setPassword((passwordEncoder.encode(user.getPassword())));
            }
        } else {
            // Обработка случая, когда пользователь не найден
            throw new UserIdNotFoundException();
        }
    }
}
