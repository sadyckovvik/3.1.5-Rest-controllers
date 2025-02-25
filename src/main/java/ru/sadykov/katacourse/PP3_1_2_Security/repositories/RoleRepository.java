package ru.sadykov.katacourse.PP3_1_2_Security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sadykov.katacourse.PP3_1_2_Security.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
