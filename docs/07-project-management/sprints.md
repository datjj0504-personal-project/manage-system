# Sprint Definitions

This document defines the scope, goals, and acceptance criteria for each sprint.

Use this as the authoritative sprint contract. Actual delivery progress is tracked in `reports/sprint-XX.md`.

---

## Sprint 1 — Foundation

**Period:** Sprint 1  
**Goal:** Initialize a runnable, production-structured Spring Boot backend.

### Scope

- Spring Boot project with Maven Wrapper and all required dependencies
- Layered package skeleton with empty packages
- Spring Profiles: `local`, `dev`, `prod`
- Environment variable–based datasource configuration
- `GET /api/v1/health` — no database, no service, direct controller response
- `ApiResponse<T>` generic response wrapper
- `GlobalExceptionHandler` (generic `Exception` handler only)
- Flyway `db/migration` folder initialized
- OpenAPI/Swagger UI enabled and accessible
- Spring Security baseline with permitted public routes

### Acceptance Criteria

- `./mvnw spring-boot:run` starts the server without error
- `GET /api/v1/health` returns HTTP 200 with `ApiResponse` envelope
- Swagger UI opens at `/swagger-ui/index.html`
- No business entities, services, or repositories exist
- No hardcoded credentials

### Status: ✅ Complete

---

## Sprint 2 — Authentication and User Management

**Period:** Sprint 2  
**Goal:** Implement stateless JWT authentication and core user self-service flows.

### Scope

**Security**
- Replace HTTP Basic with JWT `SecurityFilterChain`
- JWT filter for request authentication
- BCrypt `PasswordEncoder` bean

**Auth Module**
- `POST /api/v1/auth/register` — create account, return tokens
- `POST /api/v1/auth/login` — authenticate, return tokens
- `POST /api/v1/auth/refresh` — exchange refresh token for new access token
- Access token and refresh token with configurable expiry

**User Module**
- `GET /api/v1/users/me` — get own profile
- `PUT /api/v1/users/me` — update allowed profile fields
- `PUT /api/v1/users/me/password` — change password with current password verification

**Persistence**
- `User` entity with audit columns
- Flyway migration: `V1__init_user_and_auth_tables.sql`

**Supporting Infrastructure**
- Auth and User request/response DTOs (record-based)
- MapStruct mapper for User entity ↔ DTO
- Domain-specific exceptions: `AuthenticationException`, `ResourceNotFoundException`, `ValidationException`
- Request Validation on all payloads

**Testing**
- Unit tests: `AuthService`, `UserService`
- Integration tests: auth API endpoints with MockMvc
- Swagger updated with JWT bearer scheme and all new endpoints

### Acceptance Criteria

- Registration with valid payload succeeds; duplicate email returns 409
- Login with valid credentials returns access and refresh token pair
- Refresh with valid refresh token returns new access token
- Accessing `GET /api/v1/users/me` without token returns 401
- Accessing `GET /api/v1/users/me` with valid token returns profile
- Password change with wrong current password returns 400
- All responses conform to `ApiResponse` envelope
- Flyway migration applies cleanly on `dev` profile

### Status: 🔲 In Progress

---

## Sprint 3 — Project and Task Management

**Period:** Sprint 3  
**Goal:** Deliver core work management with ownership and membership access control.

### Scope

**Project Module**
- `POST /api/v1/projects`
- `GET /api/v1/projects` (paginated, accessible only)
- `GET /api/v1/projects/{id}`
- `PUT /api/v1/projects/{id}` (Owner only)
- `DELETE /api/v1/projects/{id}` (Owner only)
- Non-Member receives 403 on any Project access

**Task Module**
- `POST /api/v1/projects/{projectId}/tasks`
- `GET /api/v1/projects/{projectId}/tasks` (paginated, sortable, filterable by Status/Priority/Assignee)
- `GET /api/v1/projects/{projectId}/tasks/{taskId}`
- `PUT /api/v1/projects/{projectId}/tasks/{taskId}`
- `DELETE /api/v1/projects/{projectId}/tasks/{taskId}`
- Assignee must be a Member of the same Project

**Persistence**
- Flyway migration for `projects` and `tasks` tables

**Testing**
- Unit tests for `ProjectService` and `TaskService`
- MockMvc integration tests for all new endpoints

### Acceptance Criteria

- All Project and Task APIs enforce membership access control
- Only Owner can update or delete a Project
- Task Pagination, Sorting, and Filtering return correct subsets
- Assigning a Task to a non-Member returns a validation error
- All responses conform to `ApiResponse` envelope

### Status: 🔲 Not Started

---

## Sprint 4 — Collaboration and Hardening

**Period:** Sprint 4  
**Goal:** Add collaboration features, finalize Docker, and harden test coverage.

### Scope

**Comment Module**
- `POST /api/v1/projects/{projectId}/tasks/{taskId}/comments`
- `GET /api/v1/projects/{projectId}/tasks/{taskId}/comments` (paginated)
- `DELETE /api/v1/projects/{projectId}/tasks/{taskId}/comments/{commentId}` (author only)

**Attachment Module**
- `POST /api/v1/projects/{projectId}/tasks/{taskId}/attachments`
- `GET /api/v1/projects/{projectId}/tasks/{taskId}/attachments`

**Docker**
- `docker-compose.yml` validation for local development
- Local runbook documentation finalized

**Testing and Quality**
- `@DataJpaTest` repository tests
- Expanded service and controller test coverage to 80% target
- Refactor for package cohesion and DRY compliance

**Documentation**
- Synchronize `docs/03-api/` with all implemented endpoints
- Synchronize `docs/04-database/` with final schema

### Acceptance Criteria

- Comment creation requires Membership; deletion requires authorship
- Attachment metadata records are stored correctly
- Docker Compose starts backend and database cleanly
- All automated tests pass
- API and schema documentation match implementation

### Status: 🔲 Not Started
