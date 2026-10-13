package com.example.academiaindustryportal.dto;

public class AuthResponse {
    private final String accessToken;
    private final String tokenType = "Bearer";
    private final String email;
    private final String fullName;
    private final String role;

    public AuthResponse(String accessToken, String email, String fullName, String role) {
        this.accessToken = accessToken;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }

    public String getAccessToken() { return accessToken; }
    public String getTokenType() { return tokenType; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }
}
