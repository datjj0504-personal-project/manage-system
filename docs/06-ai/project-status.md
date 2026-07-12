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
- Persistence baseline implemented for users:
	- `UserEntity`
	- `UserRepository`
	- `UserMapper`
	- Flyway migration `V1__init_user_and_auth_tables.sql`
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
- Project module: not started
- Task module: not started
- Comment/attachment/admin/search/pagination/filtering beyond current user flows: not started

## Sprint Progress Reality Check

- Sprint 1 scope is complete.
- Sprint 2 core identity scope is implemented in source and verified locally, but project management docs have not been fully synchronized yet.
- Sprint 3 and Sprint 4 business modules are still not started.
- The next delivery gap is no longer auth/user; it is project membership and Project CRUD.

## Risks

- Documentation drift risk: `docs/07-project-management` and other planning artifacts still describe Sprint 2 as in progress.
- Security configuration warning remains during startup because a custom `AuthenticationProvider` is registered explicitly.
- Security error handlers currently write JSON responses directly instead of using centralized serialization.
- Database scope risk remains for upcoming Project/Task work because ADR-0010 (`UUID or BIGINT`) is still unresolved while current implementation already uses `UUID`.

## Immediate Execution Priority

1. Implement project membership groundwork and Project CRUD.
2. Add pagination and access filtering for project listing.
3. Implement Task CRUD after project authorization rules are established.
4. Synchronize roadmap/progress/docs folders with the actual Sprint 2 implementation state.
5. Expand automated tests to cover repository and protected endpoint scenarios.
