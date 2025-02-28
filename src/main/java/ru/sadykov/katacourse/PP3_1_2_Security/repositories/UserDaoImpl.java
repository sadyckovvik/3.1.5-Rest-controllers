package ru.sadykov.katacourse.PP3_1_2_Security.repositories;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.sadykov.katacourse.PP3_1_2_Security.models.Role;
import ru.sadykov.katacourse.PP3_1_2_Security.models.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.merge(user);
    }


    @Override
    public Optional<User> findByUsername(String username) {
        List<User> users = entityManager.createQuery("SELECT u FROM User u left join fetch u.roles WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> getUser(long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public void removeUser(long id) {
        Query query = entityManager.createQuery("DELETE FROM User u WHERE u.id = :id");
        query.setParameter("id", id).executeUpdate();
    }
}
