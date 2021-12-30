package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.entity.User;
import com.enigmacamp.evening.entity.UserDevice;
import com.enigmacamp.evening.event.OnUserLogoutSuccessEvent;
import com.enigmacamp.evening.exception.ResourceNotFoundException;
import com.enigmacamp.evening.exception.UserLogoutException;
import com.enigmacamp.evening.repository.UserDeviceRepository;
import com.enigmacamp.evening.repository.UserRepository;
import com.enigmacamp.evening.request.LogOutRequest;
import com.enigmacamp.evening.response.ApiResponse;
import com.enigmacamp.evening.response.UserProfile;
import com.enigmacamp.evening.service.CurrentUser;
import com.enigmacamp.evening.service.RefreshTokenService;
import com.enigmacamp.evening.service.UserDeviceService;
import com.enigmacamp.evening.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
        
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @Autowired
    private UserDeviceService userDeviceService;
    
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @GetMapping("/profile")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfile> getUserProfile(@RequestParam(value = "email", required = false) Optional<String> email) {
    	List<UserProfile> userProfiles = new ArrayList<>();
        if (email.isPresent()) {
    		User user = userRepository.findByEmail(email.get())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", email.get()));
    		UserProfile userProfile = new UserProfile(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    user.getBirthDate(),
                    user.getActive(),
                    user.getCreatedAt(),
                    user.getUpdatedAt());
    		userProfiles.add(userProfile);
    	} else {
    		List<User> users = userRepository.findAll();
    		for (User user: users) {
    			UserProfile userProfile = new UserProfile(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getBirthDate(),
                        user.getActive(),
                        user.getCreatedAt(),
                        user.getUpdatedAt());
    			userProfiles.add(userProfile);
    		}
    	}
        return userProfiles;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserProfile getUserProfileById(@PathVariable("id") String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        UserProfile userProfile = new UserProfile(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getBirthDate(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt());

        return userProfile;
    }

    @PutMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deactivateUserById(@PathVariable("id") String id) {
    	User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.deactivate();
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "User deactivated successfully!"));  
    }

    @PutMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> activateUserById(@PathVariable("id") String id) {
       User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.activate();
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "User activated successfully!"));
    }

        
    @PutMapping("/logout")
    public ResponseEntity<ApiResponse> logoutUser(@CurrentUser UserPrincipal currentUser,
    		@Valid @RequestBody LogOutRequest logOutRequest) {
        String deviceId = logOutRequest.getDeviceInfo().getDeviceId();
        UserDevice userDevice = userDeviceService.findByUserId(currentUser.getId())
                .filter(device -> device.getDeviceId().equals(deviceId))
                .orElseThrow(() -> new UserLogoutException(logOutRequest.getDeviceInfo().getDeviceId(), "Invalid device Id supplied. No matching device found for the given user "));
        refreshTokenService.deleteById(userDevice.getRefreshToken().getId());
        
        OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(currentUser.getEmail(), logOutRequest.getToken(), logOutRequest);
        applicationEventPublisher.publishEvent(logoutSuccessEvent);
        return ResponseEntity.ok(new ApiResponse(true, "User has successfully logged out from the system!"));
    }

}
