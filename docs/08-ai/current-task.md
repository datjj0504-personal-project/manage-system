# Current Task (2026-07-10)

## Objective

Implement the delayed Sprint 1 scope with production-style structure:

- Authentication
- JWT
- User Management (initial profile and password flows)

## In Scope

- Security foundation upgrade from HTTP Basic to JWT authentication.
- Auth APIs:
	- Register
	- Login
	- Refresh token
- Core user APIs:
	- Get profile
	- Update profile (safe fields)
	- Change password
- Persistence baseline:
	- User entity
	- User repository
	- Auth/User DTOs
	- Mapper layer
	- Flyway migration `V1__init_user_and_auth_tables.sql`
- Error handling:
	- Domain-specific exceptions and handler mapping
- Validation:
	- Request validation for auth/user payloads

## Non-Goals (Current Iteration)

- Project CRUD
- Task CRUD
- Comment/attachment
- Notification

## Acceptance Checklist

- Auth endpoints return consistent `ApiResponse` payloads.
- Passwords are hashed (BCrypt).
- JWT access/refresh token flow works with expiration controls.
- Protected endpoints require valid JWT.
- Flyway migration applies cleanly on local/dev profiles.
- Unit tests for service logic and integration tests for auth APIs pass.
- Swagger/OpenAPI displays the new endpoints and auth scheme.
