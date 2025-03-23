package ru.sadykov.katacourse.PP3_1_2_Security.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sadykov.katacourse.PP3_1_2_Security.models.Role;
import ru.sadykov.katacourse.PP3_1_2_Security.repositories.RoleDao;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> findsRolesByIds(List<Long> id) {
        return roleDao.findsRolesByIds(id);
    }

    @Override
    public List<Role> findsRolesByName(List<String> roles) {
        return roleDao.findsRolesByName(roles);
    }
}
