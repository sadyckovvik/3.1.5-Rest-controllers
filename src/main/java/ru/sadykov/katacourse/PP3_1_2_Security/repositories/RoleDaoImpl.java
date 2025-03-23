package ru.sadykov.katacourse.PP3_1_2_Security.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.sadykov.katacourse.PP3_1_2_Security.models.Role;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public List<Role> findsRolesByIds(List<Long> id) {
        return id.stream()
                .map(this::findRoleById)
                .collect(Collectors.toList());
    }

    @Override
    public Role findRoleById(long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> findsRolesByName(List<String> roles) {
        return roles.stream()
                .map(this::findRoleByName)
                .collect(Collectors.toList());
    }

    @Override
    public Role findRoleByName(String role) {
            return entityManager
                    .createQuery("select r FROM Role r WHERE r.name = :role", Role.class)
                    .setParameter("role", role).getSingleResult();
    }
}


