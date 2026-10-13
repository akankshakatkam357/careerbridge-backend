package com.example.academiaindustryportal.service;

import com.example.academiaindustryportal.dto.RegisterRequest;
import com.example.academiaindustryportal.entity.Role;
import com.example.academiaindustryportal.repository.UserRepository;
import com.example.academiaindustryportal.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest request(String email, Role role) {
        RegisterRequest request = new RegisterRequest();
        request.setEmail(email);
        request.setPassword("secret1");
        request.setFullName("Test User");
        request.setRole(role);
        return request;
    }

    @Test
    void registerRejectsAdminRole() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(request("admin@example.com", Role.ROLE_ADMIN))
        );

        assertEquals("Invalid role. Choose student, faculty or industry.", exception.getMessage());
    }

    @Test
    void registerRejectsDuplicateEmailIgnoringCaseAndWhitespace() {
        when(userRepository.existsByEmail("user@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(request("  User@Example.com ", Role.ROLE_STUDENT))
        );

        assertEquals("Email is already registered!", exception.getMessage());
    }
}
