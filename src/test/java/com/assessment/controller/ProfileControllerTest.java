package com.assessment.controller;

import com.assessment.dto.ProfileDto;
import com.assessment.security.JwtUtils; // Import JwtUtils
import com.assessment.security.UserDetailsServiceImpl; // Import UserDetailsServiceImpl
import com.assessment.service.ProfileService;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO: Implement tests for ProfileController endpoints
@WebMvcTest(value = ProfileController.class, excludeAutoConfiguration = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Mock the ProfileService dependency
    private ProfileService profileService;

    @MockBean // Mock JwtUtils as it's needed by security filter loaded by @WebMvcTest
    private JwtUtils jwtUtils;

    @MockBean // Mock UserDetailsServiceImpl as it's needed by security filter loaded by @WebMvcTest
    private UserDetailsServiceImpl userDetailsService;

    @MockBean // Mock AuditorAware to prevent JPA context issues in web tests
    private AuditorAware<String> auditorAware;

    @Autowired
    private ObjectMapper objectMapper;

    private ProfileDto profileDto1;

    @BeforeEach
    void setUp() {
        // TODO: Initialize test data
        profileDto1 = ProfileDto.builder()
                .id(1L)
                .userId(10L) // Assuming user ID 10
                .bio("Test bio")
                .build();
    }

    @Test
    @WithMockUser // Simulate authenticated user
    void getProfileByUserId_WhenProfileExists_ShouldReturnProfile() throws Exception {
        // TODO: Test GET /api/profiles/user/{userId} (success case)
        given(profileService.getProfileByUserId(10L)).willReturn(Optional.of(profileDto1));

        mockMvc.perform(get("/api/profiles/user/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(10))
                .andExpect(jsonPath("$.bio").value("Test bio"));
    }

    @Test
    @WithMockUser
    void getProfileByUserId_WhenProfileDoesNotExist_ShouldReturnNotFound() throws Exception {
        // TODO: Test GET /api/profiles/user/{userId} (not found case)
        given(profileService.getProfileByUserId(99L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/profiles/user/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    // Simulate user with ID 10 updating their own profile
    @WithMockUser(username = "user10", authorities = {"ROLE_USER"}) // Need principal.id matching {userId}
    // Note: Spring Security Test doesn't easily mock principal.id. Alternative: Mock Authentication object.
    // For simplicity, let's assume @PreAuthorize("hasRole(\'ADMIN\') or #userId == authentication.principal.id") works conceptually here
    // Or adjust test/security config for easier testing of #userId == principal.id
    void createOrUpdateProfile_WhenUpdatingSelf_ShouldReturnUpdatedProfile() throws Exception {
        // TODO: Test PUT /api/profiles/user/{userId}
        ProfileDto inputDto = ProfileDto.builder().bio("Updated bio").build();
        ProfileDto returnedDto = ProfileDto.builder().id(1L).userId(10L).bio("Updated bio").build();

        given(profileService.createOrUpdateProfile(eq(10L), any(ProfileDto.class))).willReturn(returnedDto);

        mockMvc.perform(put("/api/profiles/user/10")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bio").value("Updated bio"));
    }

    // TODO: Add tests for validation errors
    // TODO: Add tests for authorization failures (e.g., user trying to update another user's profile)
}

