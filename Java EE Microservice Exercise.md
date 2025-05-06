# Java EE Microservice Exercise

This exercise tests your ability to build a secure, scalable microservice using Spring Boot and modern Java EE practices.

**Important Note on Existing Tests:** Please be aware that there are known test failures in `AuthControllerTest.java` related to HTTP 401 Unauthorized responses. You are **not** expected to fix these specific pre-existing test failures unless the assessment tasks explicitly ask you to work on authentication or authorization components. Focus on completing the tasks outlined in this README and writing new tests for your own code.

## Overview

This exercise tests your ability to build a secure, scalable microservice using Spring Boot and modern Java EE practices.

## Requirements

### 1. User Management System

*   User registration and authentication
*   Role-based access control
*   JWT token implementation
*   Password hashing and security

### 2. RESTful API

*   CRUD operations for user profiles
*   Input validation
*   Error handling
*   API documentation (Swagger/OpenAPI)

### 3. Database Integration

*   PostgreSQL database
*   JPA/Hibernate implementation
*   Database migrations (Flyway)
*   Connection pooling

### 4. Security

*   Spring Security implementation
*   CORS configuration
*   Rate limiting
*   Input sanitization

### 5. Testing

*   Unit tests (JUnit 5)
*   Integration tests
*   Test coverage > 80%
*   Mocking and test data

## Tech Stack

*   Java 17
*   Spring Boot 3.x
*   PostgreSQL 14+
*   Docker
*   Maven/Gradle
*   JUnit 5
*   Mockito

## Project Structure

    src/
    ├── main/
    │   ├── java/
    │   │   └── com/assessment/
    │   │       ├── config/
    │   │       ├── controller/
    │   │       ├── model/
    │   │       ├── repository/
    │   │       ├── service/
    │   │       ├── security/
    │   │       └── util/
    │   └── resources/
    │       ├── application.yml
    │       └── db/migration/
    └── test/
        └── java/
            └── com/assessment/
                ├── controller/
                ├── service/
                └── integration/

## Setup Instructions

1.  Clone the repository
2.  Set up PostgreSQL database
3.  Configure application.yml
4.  Run database migrations
5.  Start the application

## Evaluation Criteria

### Code Quality

*   Clean code principles
*   SOLID principles
*   Design patterns usage
*   Code organization

### Functionality

*   Feature completeness
*   Business logic correctness
*   Error handling
*   Input validation

### Testing

*   Test coverage
*   Test quality
*   Integration tests
*   Edge cases

### Security

*   Authentication/Authorization
*   Data protection
*   Input validation
*   Security best practices

## Time Limit

*   4 hours for implementation
*   1 hour for testing and documentation

## Submission Guidelines

1.  Push your code to your fork (or your feature branch in this repository if given collaborator access).
2.  Create a pull request to the `main` branch.
3.  Include test results (e.g., screenshots or a summary if tests are not run by CI).
4.  Add documentation for your changes/features.

## General Guidelines

*   Use Spring Boot best practices
*   Follow REST API design principles
*   Implement proper logging
*   Document your code
*   Write meaningful commit messages

## CI/CD

This repository is configured with GitHub Actions for continuous integration. All workflows use GitHub-hosted runners, providing a managed environment for building, testing, and deploying code.

This is a test note to verify the GitHub Actions workflow with self-hosted runner. \[Test run at 2025-03-31 07:45 UTC\]

