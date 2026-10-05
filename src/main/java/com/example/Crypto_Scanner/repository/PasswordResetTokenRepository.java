package com.example.Crypto_Scanner.repository;

import com.example.Crypto_Scanner.model.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository
        extends MongoRepository<PasswordResetToken, String> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByEmail(String email);

    void deleteByToken(String token);
}