package ru.sadykov.katacourse.PP3_1_2_Security.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.sadykov.katacourse.PP3_1_2_Security.models.User;

import java.util.List;


public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void saveUser(User user);

    void updateUser(Long id, User user);

    void registerUser(User user);

    User getUser(long id);

    void removeUser(long id);

    User findByUsername(String username);
}
