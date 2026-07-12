# Sprint 1 Report — Foundation

**Sprint:** 1  
**Goal:** Initialize a runnable, production-structured Spring Boot backend with no business features.  
**Status:** ✅ Complete

---

## Delivered

### Project Initialization

- Spring Boot 4.1.0 project generated with Maven Wrapper
- Build tool: Maven 3.9+
- Java version: 21 (compiler target aligned with `pom.xml`)
- Group ID: `com.company`, Artifact ID: `taskmanagement`
- Main class: `com.company.taskmanagement.Application`

### Dependencies Added

| Dependency | Purpose |
|---|---|
| `spring-boot-starter-web` | REST API layer |
| `spring-boot-starter-validation` | Jakarta Validation |
| `spring-boot-starter-security` | Security filter chain |
| `spring-boot-starter-data-jpa` | Persistence layer |
| `spring-boot-starter-flyway` | Database migration |
| `flyway-core` | Flyway runtime |
| `postgresql` | PostgreSQL driver (runtime) |
| `mysql-connector-j` | MySQL driver (runtime) |
| `springdoc-openapi-starter-webmvc-ui` | OpenAPI 3 / Swagger UI |
| `lombok` | Boilerplate reduction (annotation processor) |
| `mapstruct` | Compile-time DTO/entity mapping (annotation processor) |
| `spring-boot-devtools` | Local hot reload |
| `spring-boot-starter-test` | JUnit 5 + MockMvc (test scope) |
| `mockito-core` | Mocking (test scope) |

### Package Structure

All packages created under `com.company.taskmanagement`:

```
config
common
controller
dto
entity
exception
mapper
repository
security
service
util
validation
constant
```

### Configuration Files

| File | Purpose |
|---|---|
| `application.yml` | Base configuration; default profile set to `local` |
| `application-local.yml` | Local developer config with fallback DB URL via env var |
| `application-dev.yml` | Dev environment config; Flyway enabled |
| `application-prod.yml` | Production config; Flyway enabled |

No hardcoded credentials. All datasource values read from environment variables: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.

### Endpoints

| Method | Path | Description |
|---|---|---|
| GET | `/api/v1/health` | Returns server status. No database. No service. |

### Supporting Code

- `ApiResponse<T>` — Generic response envelope (`success`, `message`, `data`)
- `GlobalExceptionHandler` — `@RestControllerAdvice` with generic `Exception` handler
- `SecurityConfig` — Permits `/api/v1/health`, `/swagger-ui/**`, `/v3/api-docs/**`; all other routes require authentication

### Flyway

- Migration folder created: `src/main/resources/db/migration/`
- No SQL scripts yet; folder initialized with `.gitkeep`

### Swagger

- Available at: `http://localhost:8080/swagger-ui/index.html`
- API docs at: `http://localhost:8080/v3/api-docs`

### Documentation

- `backend/README.md` — Full local setup and onboarding guide
- `backend/.env.example` — Environment variable template

---

## Acceptance Criteria Verification

| Criteria | Result |
|---|---|
| Project builds successfully | ✅ |
| Server starts successfully (`./mvnw spring-boot:run`) | ✅ |
| `GET /api/v1/health` returns HTTP 200 with `ApiResponse` | ✅ |
| Swagger UI accessible | ✅ |
| Layered package structure exists | ✅ |
| All configuration files exist | ✅ |
| No business code exists | ✅ |
| No hardcoded credentials | ✅ |

---

## Notes

- The `application-local.yml` disables Flyway and sets `hikari.initialization-fail-timeout: 0` so the server starts without a live database connection in local profile.
- `spring-boot-starter-webmvc` from the Spring Initializr was replaced with the standard `spring-boot-starter-web` artifact for clarity.
- MySQL driver is included for future flexibility; PostgreSQL is the project default.

---

## Carry-Over

None. Sprint 1 scope was fully delivered.

**Next:** Sprint 2 — Authentication and User Management.
