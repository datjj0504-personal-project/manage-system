# Project Status (Updated: 2026-07-12)

## Current Implementation Snapshot

- Backend compiles, tests pass, and Spring Boot starts successfully on the local profile.
- Security has been upgraded from HTTP Basic to stateless JWT authentication.
- Public endpoints currently allowed: health check, Swagger docs, and `/api/v1/auth/**`.
- Global API response wrapper exists (`ApiResponse`) with success and failure helpers.
- Global exception handler now maps validation, authentication, authorization, business, not-found, and unexpected errors.
- Health endpoint exists: `GET /api/v1/health`.
- Auth endpoints implemented:
	- `POST /api/v1/auth/register`
	- `POST /api/v1/auth/login`
	- `POST /api/v1/auth/refresh`
- User self-service endpoints implemented:
	- `GET /api/v1/users/me`
	- `PATCH /api/v1/users/me`
	- `PUT /api/v1/users/me/password`
- Project endpoints implemented:
	- `POST /api/v1/projects`
	- `GET /api/v1/projects/{projectId}`
	- `GET /api/v1/projects`
	- `PATCH /api/v1/projects/{projectId}`
	- `DELETE /api/v1/projects/{projectId}`
- Persistence baseline implemented for users:
	- `UserEntity`
	- `UserRepository`
	- `UserMapper`
	- Flyway migration `V1__init_user_and_auth_tables.sql`
- Persistence baseline implemented for projects:
	- `ProjectEntity`
	- `ProjectMemberEntity`
	- `ProjectMemberRole`
	- `ProjectRepository`
	- `ProjectMemberRepository`
	- `ProjectMapper`
	- Flyway migration `V2__init_project_and_membership_tables.sql`
- JWT support implemented:
	- token properties
	- token provider
	- authentication filter
	- custom user details service
- OpenAPI bearer scheme is configured and Swagger is reachable.
- Local startup hardening exists for PostgreSQL 18 and Windows timezone compatibility.
- Automated test coverage currently includes:
	- `AuthServiceImplTest`
	- `UserServiceImplTest`
	- `AuthControllerApiTest`
	- `ProjectServiceImplTest`
	- `ProjectControllerApiTest`

## Requirement Coverage

Planned features from requirement docs:

- Authentication (register, login, refresh token)
- User profile/avatar/password management
- Project CRUD
- Task CRUD
- Comment, attachment, search, pagination, sorting, filtering, admin

Current actual coverage:

- Server initialization: done
- Authentication module: implemented
- JWT security flow: implemented
- User profile/password flows: implemented
- Project module: implemented
- Task module: not started
- Comment/attachment/admin: not started
- Search/pagination/sorting/filtering: implemented for project listing; task-level not started

## Sprint Progress Reality Check

- Sprint 1 scope is complete.
- Sprint 2 core identity scope is implemented in source and verified locally.
- Sprint 3 project foundation and Project CRUD are implemented in source and validated by tests.
- Sprint 3 task module and Sprint 4 collaboration modules are not started yet.
- The next delivery gap is Task CRUD with project-bound authorization.

## Risks

- Documentation drift risk: `docs/07-project-management` still describes Sprint 2/Sprint 3 states that do not match current source reality.
- Security configuration warning remains during startup because a custom `AuthenticationProvider` is registered explicitly.
- Security error handlers currently write JSON responses directly instead of using centralized serialization.
- Database scope risk remains for upcoming Project/Task work because ADR-0010 (`UUID or BIGINT`) is still unresolved while current implementation already uses `UUID`.

## Immediate Execution Priority

1. Implement Task CRUD with project-bound membership authorization.
2. Add task assignee validation to ensure assignee belongs to the same project.
3. Add task-level pagination/sorting/filtering in list API.
4. Synchronize roadmap/progress/docs folders with actual Sprint 2 and Sprint 3 source state.
5. Expand automated tests to cover repository and additional protected endpoint scenarios.
