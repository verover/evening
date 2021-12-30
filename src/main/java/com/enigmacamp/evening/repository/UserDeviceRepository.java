package com.enigmacamp.evening.repository;

import com.enigmacamp.evening.entity.RefreshToken;
import com.enigmacamp.evening.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, String> {

    @Override
    Optional<UserDevice> findById(String id);

    Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken);

    Optional<UserDevice> findByUserId(String userId);
}
