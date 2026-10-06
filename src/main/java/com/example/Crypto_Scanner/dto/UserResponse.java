package com.example.Crypto_Scanner.dto;

public class UserResponse {

    private String id;
    private String username;
    private String email;
    private String role;
    private boolean enabled;

    public UserResponse() {
    }

    public UserResponse(String id, String username, String email,
                        String role, boolean enabled) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }
}