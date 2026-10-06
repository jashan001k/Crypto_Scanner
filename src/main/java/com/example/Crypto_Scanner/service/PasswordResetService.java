package com.example.Crypto_Scanner.service;

import com.example.Crypto_Scanner.model.PasswordResetToken;
import com.example.Crypto_Scanner.model.User;
import com.example.Crypto_Scanner.repository.PasswordResetTokenRepository;
import com.example.Crypto_Scanner.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(
            UserRepository userRepository,
            PasswordResetTokenRepository tokenRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String createResetToken(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Remove old tokens
        tokenRepository.deleteByEmail(email);

        String token = UUID.randomUUID().toString();

        LocalDateTime expiryDate =
                LocalDateTime.now().plusMinutes(15);

        PasswordResetToken resetToken =
                new PasswordResetToken(
                        token,
                        user.getEmail(),
                        expiryDate
                );

        tokenRepository.save(resetToken);

        return token;
    }

    public void resetPassword(
            String token,
            String newPassword) {

        PasswordResetToken resetToken =
                tokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid reset token"
                                ));

        if (resetToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            tokenRepository.deleteByToken(token);

            throw new RuntimeException(
                    "Reset token has expired"
            );
        }

        User user = userRepository
                .findByEmail(resetToken.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));

        user.setPassword(
                passwordEncoder.encode(newPassword)
        );

        userRepository.save(user);

        // Token can only be used once
        tokenRepository.deleteByToken(token);
    }
}