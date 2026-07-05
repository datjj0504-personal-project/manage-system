# Backend

This module is the Spring Boot backend for the Task Management System.  
It provides REST APIs, validation, security foundation, database integration, Flyway migration support, and OpenAPI documentation.

---

## Prerequisites

Install the following tools before running the backend locally.

### 1) Java
- **Required version:** Java 21 (project currently compiles with Java 21 in `pom.xml`; align with team decision if needed)
- **Verify:**
```bash
java -version
```

### 2) Maven (or Maven Wrapper)
- **Required version:** Maven 3.9+
- **Verify:**
```bash
mvn -version
```

> You can use `./mvnw` (Linux/macOS) or `mvnw.cmd` (Windows) without installing Maven globally.

### 3) PostgreSQL
- **Required version:** PostgreSQL 15+ (recommended)
- **Verify:**
```bash
postgres --version
```

### 4) Git
- **Required version:** Any recent stable version
- **Verify:**
```bash
git --version
```

---

## Clone Repository

```bash
git clone <your-repository-url>
cd manage-system
cd backend
```

---

## Environment Configuration

The backend uses Spring Profiles and externalized configuration.

### Configuration files
- `src/main/resources/application.yml`
  - Base settings
  - Default profile is `local`
- `src/main/resources/application-local.yml`
  - Local developer settings
- `src/main/resources/application-dev.yml`
  - Development environment settings
- `src/main/resources/application-prod.yml`
  - Production environment settings

### Spring Profiles
- **Default:** `local`
- **Switch profile:**
  - JVM arg: `-Dspring.profiles.active=dev`
  - Env var: `SPRING_PROFILES_ACTIVE=dev`

### Environment variables
Set these before running the app:
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

Do not hardcode credentials in YAML files or source code.

You can copy from `.env.example`:
```bash
cp .env.example .env
```

---

## Create Database

Create a local database named `task_management`.

### PostgreSQL (project default)
```sql
CREATE DATABASE task_management
  WITH ENCODING='UTF8';
```

### MySQL (if your local setup uses MySQL)
```sql
CREATE DATABASE task_management
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

Do not use production credentials in local environments.

---

## Configure Database Connection

The main properties are:
- `spring.datasource.url`  
  JDBC URL, e.g. `jdbc:postgresql://localhost:5432/task_management`
- `spring.datasource.username`  
  Database username
- `spring.datasource.password`  
  Database password
- Active profile (`spring.profiles.active`)  
  Controls which `application-<profile>.yml` is loaded

Current profile files are configured with `org.postgresql.Driver`.  
If you use MySQL locally, update the profile datasource driver and URL accordingly.

---

## Install Dependencies

From `backend/` run:

```bash
./mvnw clean install
```

Or on Windows:

```powershell
.\mvnw.cmd clean install
```

Or with global Maven:

```bash
mvn clean install
```

This command cleans old build artifacts, resolves dependencies, compiles source code, runs tests, and packages the application.

---

## Run Application

### Method 1: IntelliJ IDEA
1. Open `backend` as a Maven project.
2. Wait for Maven import to finish.
3. Run `com.datnt.taskmanagement.Application`.

### Method 2: Maven Wrapper
```bash
./mvnw spring-boot:run
./mvnw spring-boot:run "-Dspring-boot.run.jvmArguments=-Duser.timezone=Asia/Ho_Chi_Minh"
```

Windows:
```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.jvmArguments=-Duser.timezone=Asia/Ho_Chi_Minh"
```

File JAR:
```bash
java -Duser.timezone=Asia/Ho_Chi_Minh -jar ./target/taskmanagement-0.0.1-SNAPSHOT.jar
```

Expected startup logs include:
- `Starting Application ...`
- `Tomcat initialized with port 8080 (http)`
- `Started Application ...`

---

## Verify Server

### Health API
- Endpoint: `GET /api/v1/health`
- URL: `http://localhost:8080/api/v1/health`
- Expected: `HTTP 200`

Example response:
```json
{
  "success": true,
  "message": "Application is running",
  "data": null
}
```

### Swagger UI
- Default URL: `http://localhost:8080/swagger-ui/index.html`
- Also available via configured path: `http://localhost:8080/swagger-ui.html`

---

## Project Structure

```text
backend/
├─ src/
│  ├─ main/
│  │  ├─ java/com/company/taskmanagement/
│  │  │  ├─ Application.java
│  │  │  ├─ config/
│  │  │  ├─ common/
│  │  │  ├─ controller/
│  │  │  ├─ dto/
│  │  │  ├─ entity/
│  │  │  ├─ exception/
│  │  │  ├─ mapper/
│  │  │  ├─ repository/
│  │  │  ├─ security/
│  │  │  ├─ service/
│  │  │  ├─ util/
│  │  │  ├─ validation/
│  │  │  └─ constant/
│  │  └─ resources/
│  │     ├─ application.yml
│  │     ├─ application-local.yml
│  │     ├─ application-dev.yml
│  │     ├─ application-prod.yml
│  │     └─ db/migration/
├─ pom.xml
└─ mvnw / mvnw.cmd
```

Folder purpose:
- `config`: Spring and framework configurations
- `controller`: API endpoints only
- `service`: business logic layer
- `repository`: database access layer
- `dto`: request/response models
- `entity`: JPA entities
- `mapper`: DTO/entity mapping
- `exception`: global and custom exception handling
- `common`: shared response objects and common components

---

## Common Problems

### Wrong Java version
- Symptom: build fails with source/target mismatch.
- Fix: install Java 21 and confirm `java -version`. If needed, align `pom.xml` Java version with team standard.

### Database connection failed
- Symptom: startup fails with datasource or connection timeout errors.
- Fix:
  1. Ensure DB server is running.
  2. Verify `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.
  3. Confirm the selected profile uses the correct datasource driver.

### Port already in use
- Symptom: `Port 8080 was already in use`.
- Fix:
  - Stop the process using port 8080, or
  - Run with another port:
    ```bash
    ./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
    ```

### Flyway migration failed
- Symptom: migration validation or script execution errors.
- Fix:
  1. Check `src/main/resources/db/migration`.
  2. Verify migration naming (e.g., `V1__init.sql`).
  3. Ensure database user has DDL permissions.

### Dependency download failure
- Symptom: Maven cannot resolve artifacts.
- Fix:
  1. Check internet/proxy settings.
  2. Retry with:
     ```bash
     ./mvnw -U clean install
     ```
  3. Clear corrupted cache if needed (`~/.m2/repository` entry).

### Swagger not opening
- Symptom: 404 or access denied for Swagger endpoint.
- Fix:
  1. Confirm application started successfully.
  2. Verify security config allows `/swagger-ui/**` and `/v3/api-docs/**`.
  3. Try both URLs:
     - `/swagger-ui/index.html`
     - `/swagger-ui.html`

---

## Development Workflow

Recommended workflow:
1. Create a branch from `develop`:
   - `feature/<short-description>`
   - `bugfix/<short-description>`
2. Follow commit types from `docs/05-rules/commit-rule.md`:
   - `feat`, `fix`, `refactor`, `docs`, `test`, `chore`, etc.
3. Run tests before commit:
```bash
./mvnw test
```
4. Open a PR with clear scope and review notes.

---

## Useful Commands

```bash
# Clean
./mvnw clean

# Build
./mvnw compile

# Run
./mvnw spring-boot:run

# Test
./mvnw test

# Package
./mvnw package
```

Windows equivalents use `.\mvnw.cmd`.

---

## Coding Standards

All backend code must follow the rules in:
- `docs/05-rules/java-style.md`
- `docs/05-rules/folder-rule.md`
- `docs/05-rules/api-rule.md`
- `docs/05-rules/exception-rule.md`
- `docs/05-rules/security-rule.md`
- `docs/05-rules/testing-rule.md`

Read and apply those documents before implementing features.

---

## Notes

- Default server port: `8080`
- Default profile: `local`
- Change profile:
  - `SPRING_PROFILES_ACTIVE=dev` (env var), or
  - `-Dspring.profiles.active=dev` (JVM argument)

