package com.example.Crypto_Scanner.controller;

import com.example.Crypto_Scanner.dto.RegisterRequest;
import com.example.Crypto_Scanner.dto.LoginRequest;
import com.example.Crypto_Scanner.dto.LoginResponse;
import com.example.Crypto_Scanner.dto.UserResponse;
import com.example.Crypto_Scanner.dto.ForgotPasswordRequest;
import com.example.Crypto_Scanner.dto.ResetPasswordRequest;

import com.example.Crypto_Scanner.model.User;

import com.example.Crypto_Scanner.service.AuthService;
import com.example.Crypto_Scanner.service.PasswordResetService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    public AuthController(
            AuthService authService,
            PasswordResetService passwordResetService) {

        this.authService = authService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody RegisterRequest request) {

        User user = authService.registerUser(request);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.isEnabled()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        LoginResponse response =
                authService.loginUser(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        String token =
                passwordResetService.createResetToken(
                        request.getEmail()
                );

        return ResponseEntity.ok(
                "Password reset token: " + token
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        passwordResetService.resetPassword(
                request.getToken(),
                request.getNewPassword()
        );

        return ResponseEntity.ok(
                "Password reset successfully"
        );
    }
}