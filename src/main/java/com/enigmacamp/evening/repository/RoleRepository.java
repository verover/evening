package com.enigmacamp.evening.repository;

import com.enigmacamp.evening.entity.Role;
import com.enigmacamp.evening.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRole(UserRole role);

    Boolean existsByRole(UserRole role);
}