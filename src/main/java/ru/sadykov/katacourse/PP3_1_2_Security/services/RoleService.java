package ru.sadykov.katacourse.PP3_1_2_Security.services;

import ru.sadykov.katacourse.PP3_1_2_Security.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    List<Role> findsRolesByIds(List<Long> id);

    List<Role> findsRolesByName(List<String> roles);
}
