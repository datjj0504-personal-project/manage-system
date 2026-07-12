# Roadmap

This document defines the planned delivery phases for the Task Management System backend.

Each sprint has a defined scope, goal, and success criteria. Scope may be adjusted based on actual progress at the end of each sprint.

---

## Sprint 1 — Foundation

**Goal:** Initialize a runnable, production-structured Spring Boot backend with no business features.

**Scope:**
- Spring Boot project initialization with Maven Wrapper
- Layered package structure (`config`, `controller`, `service`, `repository`, `dto`, `entity`, `mapper`, `exception`, `common`, `security`, `util`, `validation`, `constant`)
- Spring Profiles (`local`, `dev`, `prod`)
- Environment variable–based database configuration (no hardcoded credentials)
- Health check endpoint: `GET /api/v1/health`
- Generic API response wrapper: `ApiResponse<T>`
- `GlobalExceptionHandler` skeleton
- Flyway migration folder initialized
- OpenAPI/Swagger UI enabled
- Spring Security baseline (HTTP Basic, public routes for health and Swagger)

**Status:** ✅ Complete

---

## Sprint 2 — Authentication and User Management

**Goal:** Implement JWT-based authentication and the core user management flows.

**Scope:**
- Replace HTTP Basic with JWT `SecurityFilterChain`
- Auth APIs:
  - `POST /api/v1/auth/register`
  - `POST /api/v1/auth/login`
  - `POST /api/v1/auth/refresh`
- User APIs:
  - `GET /api/v1/users/me`
  - `PUT /api/v1/users/me`
  - `PUT /api/v1/users/me/password`
- `User` entity and Flyway migration `V1__init_user_and_auth_tables.sql`
- BCrypt password hashing
- JWT access token + refresh token flow with expiry controls
- Auth and User DTOs, mapper, service, repository, controller
- Domain-specific exceptions (Validation, Authentication, ResourceNotFound)
- Request Validation for all auth/user payloads
- Unit tests for auth/user service layer
- Integration tests for auth API endpoints
- Swagger/OpenAPI updated with JWT bearer scheme and all new endpoints

**Status:** 🔲 In Progress

---

## Sprint 3 — Project and Task Management

**Goal:** Implement the core work management domain with access control.

**Scope:**
- Project CRUD:
  - `POST /api/v1/projects`
  - `GET /api/v1/projects/{id}`
  - `GET /api/v1/projects` (paginated list of accessible projects)
  - `PUT /api/v1/projects/{id}` (Owner only)
  - `DELETE /api/v1/projects/{id}` (Owner only)
- Task CRUD within a Project:
  - `POST /api/v1/projects/{projectId}/tasks`
  - `GET /api/v1/projects/{projectId}/tasks/{taskId}`
  - `GET /api/v1/projects/{projectId}/tasks` (paginated, sortable, filterable)
  - `PUT /api/v1/projects/{projectId}/tasks/{taskId}`
  - `DELETE /api/v1/projects/{projectId}/tasks/{taskId}`
- Assignee assignment and reassignment
- Status and Priority update flow
- Membership access enforcement (non-Member cannot access Project or Task)
- Search, Pagination, Sorting, Filtering for Project and Task lists
- Flyway migration for Project and Task tables
- Service-layer unit tests and MockMvc integration tests for all new endpoints

**Status:** 🔲 Not Started

---

## Sprint 4 — Collaboration and Hardening

**Goal:** Add collaborative features, expand test coverage, and finalize Docker and documentation.

**Scope:**
- Comment module:
  - `POST /api/v1/projects/{projectId}/tasks/{taskId}/comments`
  - `GET /api/v1/projects/{projectId}/tasks/{taskId}/comments` (paginated)
  - `DELETE /api/v1/projects/{projectId}/tasks/{taskId}/comments/{commentId}`
- Attachment metadata module:
  - `POST /api/v1/projects/{projectId}/tasks/{taskId}/attachments`
  - `GET /api/v1/projects/{projectId}/tasks/{taskId}/attachments`
- Docker Compose validation and local runbook finalization
- Test coverage expansion: repository layer (`@DataJpaTest`), service layer, controller layer
- Refactor for cohesion and reduced duplication across all modules
- Full API documentation synchronization with `docs/03-api/`
- Schema documentation synchronization with `docs/04-database/`

**Status:** 🔲 Not Started

---

## Post Sprint 4 — Future Work

Items planned after the four core sprints are complete.

| Item | Description |
|---|---|
| Notification | Real-time or async notifications for Task events |
| Admin features | Elevated user management and system governance APIs |
| CI/CD pipeline | Automated build, test, and deployment pipeline |
| Frontend | React/TypeScript UI consuming all backend APIs |
