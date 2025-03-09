package ru.sadykov.katacourse.PP3_1_2_Security.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.sadykov.katacourse.PP3_1_2_Security.models.User;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    void saveUser(User user);
    void updateUser(long id, User user);
    void registerUser(User user);
    Optional<User> getUser(long id);
    void removeUser(long id);
    Optional<User> findByUsername(String username);
}
