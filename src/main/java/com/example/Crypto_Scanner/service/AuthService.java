package com.example.Crypto_Scanner.service;

import com.example.Crypto_Scanner.dto.LoginRequest;
import com.example.Crypto_Scanner.dto.LoginResponse;
import com.example.Crypto_Scanner.dto.RegisterRequest;
import com.example.Crypto_Scanner.model.Role;
import com.example.Crypto_Scanner.model.User;
import com.example.Crypto_Scanner.repository.UserRepository;
import com.example.Crypto_Scanner.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // =========================
    // REGISTER USER
    // =========================

    public User registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Encrypt password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Default role
        user.setRole(Role.USER);

        user.setEnabled(true);

        return userRepository.save(user);
    }

    // =========================
    // LOGIN USER
    // =========================

    public LoginResponse loginUser(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        // Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        // Check account status
        if (!user.isEnabled()) {
            throw new RuntimeException("User account is disabled");
        }

        // Generate JWT
        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getRole().name()
        );
    }
}