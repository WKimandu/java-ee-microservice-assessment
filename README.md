# Java EE Microservice Exercise

## Overview
This exercise tests your ability to build a secure, scalable microservice using Spring Boot and modern Java EE practices.

## Requirements

### Core Features
1. User Management System
   - User registration and authentication
   - Role-based access control
   - JWT token implementation
   - Password hashing and security

2. RESTful API
   - CRUD operations for user profiles
   - Input validation
   - Error handling
   - API documentation (Swagger/OpenAPI)

3. Database Integration
   - PostgreSQL database
   - JPA/Hibernate implementation
   - Database migrations (Flyway)
   - Connection pooling

4. Security
   - Spring Security implementation
   - CORS configuration
   - Rate limiting
   - Input sanitization

5. Testing
   - Unit tests (JUnit 5)
   - Integration tests
   - Test coverage > 80%
   - Mocking and test data

### Technical Requirements
- Java 17
- Spring Boot 3.x
- PostgreSQL 14+
- Docker
- Maven/Gradle
- JUnit 5
- Mockito

## Project Structure
```
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
```

## Getting Started

1. Clone the repository
2. Set up PostgreSQL database
3. Configure application.yml
4. Run database migrations
5. Start the application

## Evaluation Criteria

### Code Quality (30%)
- Clean code principles
- SOLID principles
- Design patterns usage
- Code organization

### Functionality (30%)
- Feature completeness
- Business logic correctness
- Error handling
- Input validation

### Testing (20%)
- Test coverage
- Test quality
- Integration tests
- Edge cases

### Security (20%)
- Authentication/Authorization
- Data protection
- Input validation
- Security best practices

## Time Limit
- 4 hours for implementation
- 1 hour for testing and documentation

## Submission
1. Push your code to your fork
2. Create a pull request
3. Include test results
4. Add documentation

## Notes
- Use Spring Boot best practices
- Follow REST API design principles
- Implement proper logging
- Document your code
- Write meaningful commit messages 

## Note on CI/CD

This repository is configured with GitHub Actions for continuous integration. Your code will be automatically built and tested when you submit a pull request.

## Test Note
This is a test note to verify the GitHub Actions workflow with self-hosted runner. [Test run at 2025-03-31 07:45 UTC]
