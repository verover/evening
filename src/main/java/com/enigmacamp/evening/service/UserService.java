package com.enigmacamp.evening.service;

import com.enigmacamp.evening.entity.Role;
import com.enigmacamp.evening.entity.User;
import com.enigmacamp.evening.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {
    UserResponse create(User user, Set<Role> roles);
}
