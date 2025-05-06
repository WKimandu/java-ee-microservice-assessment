package com.assessment.controller;

import com.assessment.dto.JwtResponse;
import com.assessment.dto.LoginRequest;
import com.assessment.dto.RegisterRequest;
import com.assessment.security.JwtUtils; // Import JwtUtils
import com.assessment.security.UserDetailsServiceImpl; // Import UserDetailsServiceImpl
import com.assessment.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.AuditorAware; // Import AuditorAware
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO: Implement tests for AuthController endpoints
@WebMvcTest(value = AuthController.class, excludeAutoConfiguration = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Mock the AuthService dependency
    private AuthService authService;

    @MockBean // Mock JwtUtils as it's needed by security filter loaded by @WebMvcTest
    private JwtUtils jwtUtils;

    @MockBean // Mock UserDetailsServiceImpl as it's needed by security filter loaded by @WebMvcTest
    private UserDetailsServiceImpl userDetailsService;

    @MockBean // Mock AuditorAware to prevent JPA context issues in web tests
    private AuditorAware<String> auditorAware;

    @Autowired
    private ObjectMapper objectMapper;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private JwtResponse jwtResponse;

    @BeforeEach
    void setUp() {
        // TODO: Initialize test data
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setEmail("new@example.com");
        registerRequest.setPassword("password123");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        jwtResponse = new JwtResponse("dummy.jwt.token", 1L, "testuser", "test@example.com", Collections.singletonList("ROLE_USER"));
    }

    @Test
    void registerUser_WhenSuccessful_ShouldReturnOk() throws Exception {
        // TODO: Test POST /api/auth/register (success case)
        doNothing().when(authService).register(any(RegisterRequest.class));

        mockMvc.perform(post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));
    }

    @Test
    void registerUser_WhenUserExists_ShouldReturnBadRequest() throws Exception {
        // TODO: Test POST /api/auth/register (failure case - user exists)
        doThrow(new RuntimeException("Username already exists")).when(authService).register(any(RegisterRequest.class));

        mockMvc.perform(post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username already exists"));
    }

    @Test
    void authenticateUser_WhenCredentialsAreValid_ShouldReturnJwt() throws Exception {
        // TODO: Test POST /api/auth/login (success case)
        given(authService.login(any(LoginRequest.class))).willReturn(jwtResponse);

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("dummy.jwt.token"))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void authenticateUser_WhenCredentialsAreInvalid_ShouldReturnUnauthorized() throws Exception {
        // TODO: Test POST /api/auth/login (failure case - invalid credentials)
        given(authService.login(any(LoginRequest.class))).willThrow(new RuntimeException("Bad credentials")); // Simulate auth failure

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized()) // Expect 401 Unauthorized
                .andExpect(content().string("Invalid credentials"));
    }

    // TODO: Add tests for validation errors (e.g., missing fields in requests)
}

