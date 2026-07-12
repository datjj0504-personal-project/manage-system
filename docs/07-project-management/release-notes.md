# Release Notes

This document records all public releases of the Task Management System backend.

Each release entry corresponds to a tagged version in version control and describes what changed, what was fixed, and any upgrade actions required.

---

## Unreleased — v0.2.0

**Target Sprint:** Sprint 2  
**Expected scope:** JWT authentication, user registration, login, refresh token, user profile management.

No release tag exists yet.

---

## v0.1.0 — Foundation Release

**Release Date:** 2026-07-04  
**Sprint:** Sprint 1  
**Type:** Initial release

### Summary

First runnable release of the Task Management System backend.

This release establishes the project foundation: a production-structured Spring Boot application with security baseline, OpenAPI documentation, configuration profiles, and a health check endpoint.

No business features are included.

### What's New

- Spring Boot 4.1.0 backend initialized with Maven Wrapper
- Layered package structure under `com.company.taskmanagement`
- Spring Profiles `local`, `dev`, `prod` with externalized datasource configuration
- `GET /api/v1/health` — health check endpoint, HTTP 200, no database dependency
- `ApiResponse<T>` generic response wrapper
- `GlobalExceptionHandler` skeleton
- Spring Security filter chain with public routes for health and Swagger
- Flyway migration folder initialized
- OpenAPI 3 / Swagger UI at `/swagger-ui/index.html`
- Developer onboarding README and `.env.example`

### Breaking Changes

None. This is the initial release.

### Upgrade Actions

None. Fresh setup only. Follow `backend/README.md` for local setup instructions.

### Known Limitations

- HTTP Basic authentication is the current security mode (JWT not yet implemented)
- No domain entities, repositories, services, or business controllers exist
- Flyway is disabled on `local` profile; no migration scripts yet
- `GlobalExceptionHandler` handles only generic `Exception`; domain-specific exceptions are not yet defined
