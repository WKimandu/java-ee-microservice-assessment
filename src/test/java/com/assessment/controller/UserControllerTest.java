package com.assessment.controller;

import com.assessment.dto.UserDto;
import com.assessment.security.JwtUtils; // Import JwtUtils
import com.assessment.security.UserDetailsServiceImpl; // Import UserDetailsServiceImpl
import com.assessment.service.UserService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Implement tests for UserController endpoints
@WebMvcTest(value = UserController.class, excludeAutoConfiguration = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class}) // Test only the UserController layer
// Note: Need to mock security or provide test security config if endpoints are secured
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Mock the UserService dependency
    private UserService userService;

    @MockBean // Mock JwtUtils as it's needed by security filter loaded by @WebMvcTest
    private JwtUtils jwtUtils;

    @MockBean // Mock UserDetailsServiceImpl as it's needed by security filter loaded by @WebMvcTest
    private UserDetailsServiceImpl userDetailsService;

    @MockBean // Mock AuditorAware to prevent JPA context issues in web tests
    private AuditorAware<String> auditorAware;

    @Autowired
    private ObjectMapper objectMapper; // For converting objects to JSON

    private UserDto user1;
    private UserDto user2;

    @BeforeEach
    void setUp() {
        // TODO: Initialize test data
        user1 = UserDto.builder().id(1L).username("user1").email("user1@example.com").build();
        user2 = UserDto.builder().id(2L).username("user2").email("user2@example.com").build();
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Example: Simulate an admin user
    void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        // TODO: Test GET /api/users endpoint
        given(userService.getAllUsers()).willReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].username").value("user1"));
    }

    @Test
    @WithMockUser // Example: Simulate a regular user
    void getUserById_WhenUserExists_ShouldReturnUser() throws Exception {
        // TODO: Test GET /api/users/{id} endpoint (success case)
        given(userService.getUserById(1L)).willReturn(Optional.of(user1));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    @WithMockUser
    void getUserById_WhenUserDoesNotExist_ShouldReturnNotFound() throws Exception {
        // TODO: Test GET /api/users/{id} endpoint (not found case)
        given(userService.getUserById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_ShouldReturnCreatedUser() throws Exception {
        // TODO: Test POST /api/users endpoint
        given(userService.createUser(any(UserDto.class))).willReturn(user1); // Assume service returns the created user

        mockMvc.perform(post("/api/users")
                        .with(csrf()) // Include CSRF token if CSRF protection is enabled (even if disabled in main config, test config might differ)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1))) // Send user data in request body
                .andExpect(status().isCreated()) // Expect 201 Created
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    @WithMockUser(username = "user1", roles = "USER") // Simulate user1 trying to update their own profile
    void updateUser_WhenUpdatingSelf_ShouldReturnUpdatedUser() throws Exception {
        // TODO: Test PUT /api/users/{id} endpoint (update self)
        UserDto updatedInfo = UserDto.builder().username("user1_updated").email("user1@example.com").build();
        UserDto returnedUser = UserDto.builder().id(1L).username("user1_updated").email("user1@example.com").build();

        given(userService.updateUser(eq(1L), any(UserDto.class))).willReturn(returnedUser);

        mockMvc.perform(put("/api/users/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1_updated"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_ShouldReturnNoContent() throws Exception {
        // TODO: Test DELETE /api/users/{id} endpoint
        // No need to mock service return value if it's void
        // Mockito.doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1")
                        .with(csrf()))
                .andExpect(status().isNoContent()); // Expect 204 No Content
    }

    // TODO: Add tests for validation errors (e.g., invalid input in POST/PUT)
    // TODO: Add tests for authorization failures (e.g., user trying admin actions)
}

