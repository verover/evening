package com.enigmacamp.evening.repository;

import com.enigmacamp.evening.entity.Role;
import com.enigmacamp.evening.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
