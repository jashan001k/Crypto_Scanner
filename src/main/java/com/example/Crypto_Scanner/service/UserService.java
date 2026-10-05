package com.example.Crypto_Scanner.service;

import com.example.Crypto_Scanner.model.User;
import com.example.Crypto_Scanner.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
    public User updateUsername(String email, String newUsername) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setUsername(newUsername);

        return userRepository.save(user);
    }
}