package com.example.Crypto_Scanner.controller;

import com.example.Crypto_Scanner.dto.UpdateProfileRequest;
import com.example.Crypto_Scanner.dto.UserProfileResponse;
import com.example.Crypto_Scanner.model.User;
import com.example.Crypto_Scanner.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.getUserByEmail(email);

        UserProfileResponse response =
                new UserProfileResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole().name(),
                        user.isEnabled()
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest request) {

        String email = authentication.getName();

        User user = userService.updateUsername(
                email,
                request.getUsername()
        );

        UserProfileResponse response =
                new UserProfileResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole().name(),
                        user.isEnabled()
                );

        return ResponseEntity.ok(response);
    }
}