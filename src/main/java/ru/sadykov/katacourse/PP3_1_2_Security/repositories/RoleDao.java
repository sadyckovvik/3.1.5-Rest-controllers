package ru.sadykov.katacourse.PP3_1_2_Security.repositories;

import ru.sadykov.katacourse.PP3_1_2_Security.models.Role;

import java.util.List;


public interface RoleDao {
    List<Role> getAllRoles();

    List<Role> findsRolesByIds(List<Long> id);

    List<Role> findsRolesByName(List<String> roles);

    Role findRoleByName(String role);

    Role findRoleById(long id);
}
