package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.entity.Role;
import com.enigmacamp.evening.entity.UserRole;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.repository.RoleRepository;
import com.enigmacamp.evening.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role create(String strRole) {
        if (strRole == null) {
            if (!roleRepository.existsByRole(UserRole.USER_ROLE)) {
                // buat baru ke database role sebagai USER_ROLE
                Role userRole = new Role(UserRole.USER_ROLE);
                return roleRepository.save(userRole);
            }
            return roleRepository.findByRole(UserRole.USER_ROLE)
                    .orElseThrow(() -> new NotFoundException("Error: Role not found"));
        } else {
            // user mengirimkan role admin atau tidak ke server
            if (strRole.equalsIgnoreCase("admin")) {
                // ada atau tidak di database
                if (!roleRepository.existsByRole(UserRole.ADMIN_ROLE)) {
                    Role adminRole = new Role(UserRole.ADMIN_ROLE);
                    return roleRepository.save(adminRole);
                }
                return roleRepository.findByRole(UserRole.ADMIN_ROLE)
                        .orElseThrow(() -> new NotFoundException("Error: Role not found"));
            } else {
                if (!roleRepository.existsByRole(UserRole.USER_ROLE)) {
                    // buat baru ke database role sebagai USER_ROLE
                    Role userRole = new Role(UserRole.USER_ROLE);
                    return roleRepository.save(userRole);
                }
                return roleRepository.findByRole(UserRole.USER_ROLE)
                        .orElseThrow(() -> new NotFoundException("Error: Role not found"));
            }
        }
    }
}
