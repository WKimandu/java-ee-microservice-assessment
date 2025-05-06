package com.assessment.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * Test configuration to provide a mock AuditorAware bean,
 * effectively disabling JPA auditing attempts in tests where the full JPA context is not available.
 */
@TestConfiguration
// We don't need @EnableJpaAuditing here if the goal is just to provide the mock bean
// to satisfy the dependency created by the main @EnableJpaAuditing
public class TestJpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        // Return a mock AuditorAware that does nothing
        return () -> Optional.of("testUser"); // Or return Optional.empty()
    }
}

