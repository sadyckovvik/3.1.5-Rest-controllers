package ru.sadykov.katacourse.PP3_1_2_Security.repositories;



import ru.sadykov.katacourse.PP3_1_2_Security.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAllUsers();
    void saveUser(User user);
    Optional <User> getUser(long id);
    void removeUser(long id);
    Optional<User> findByUsername(String username);
}
