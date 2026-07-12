# Changelog

All notable changes to this project are recorded here.

Format follows [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).  
Versioning follows [Semantic Versioning](https://semver.org/).

---

## [Unreleased]

### In Progress

- JWT authentication module (register, login, refresh token)
- User profile and password management APIs
- Flyway migration `V1__init_user_and_auth_tables.sql`
- BCrypt password encoding
- Domain-specific exception hierarchy
- Auth and User DTOs, service, repository, mapper, controller layers

---

## [0.1.0] — Sprint 1 — Foundation

### Added

- Spring Boot 4.1.0 project initialized with Maven Wrapper
- Layered package structure under `com.company.taskmanagement`
  - `config`, `common`, `controller`, `dto`, `entity`, `exception`, `mapper`, `repository`, `security`, `service`, `util`, `validation`, `constant`
- Spring Profiles: `local`, `dev`, `prod`
- Environment variable–based datasource configuration (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`)
- `GET /api/v1/health` — server status endpoint with no database dependency
- `ApiResponse<T>` — generic API response wrapper record
- `GlobalExceptionHandler` — `@RestControllerAdvice` with generic exception handling
- `SecurityConfig` — JWT-ready filter chain skeleton; health and Swagger routes permitted
- Flyway migration folder: `src/main/resources/db/migration/`
- OpenAPI 3 / Swagger UI enabled at `/swagger-ui/index.html`
- `backend/README.md` — full local setup and developer onboarding guide
- `backend/.env.example` — environment variable template

### Dependencies

| Artifact | Version |
|---|---|
| Spring Boot | 4.1.0 |
| springdoc-openapi-starter-webmvc-ui | 2.8.13 |
| MapStruct | 1.6.3 |
| PostgreSQL driver | managed by Spring Boot BOM |
| MySQL connector | managed by Spring Boot BOM |
| Lombok | managed by Spring Boot BOM |
| Flyway Core | managed by Spring Boot BOM |
