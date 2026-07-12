# manage-system

Task management system with a Spring Boot backend, a React + Vite frontend, and PostgreSQL running through Docker Compose.

## Project Overview

- Backend: Spring Boot API that provides authentication, user profile, project management, and health endpoints.
- Frontend: React + TypeScript + Vite single-page app that consumes the backend APIs.
- Database: PostgreSQL 18 with Flyway-managed schema migration.
- Docker: Compose file for local PostgreSQL startup.

## Requirements

- Java 21
- Maven wrapper included in `backend/`
- Node.js 20.19.0 or newer
- npm
- Docker
- Docker Compose
- Git

## Project Structure

- [backend](backend) contains the Spring Boot application, Maven wrapper, and backend source code.
- [frontend](frontend) contains the React application, frontend build scripts, and UI source code.
- [docker](docker) contains the PostgreSQL Compose file used for local development.
- [docs](docs) contains business context, API contracts, design notes, and engineering rules.
- [knowledge](knowledge) contains local run notes and reference material.
- [info.txt](info.txt) contains additional repository notes.
- [structure.md](structure.md) documents the repository layout.
- [README.md](README.md) is this top-level run guide.

## Start Database

The repository stores the Compose file at [docker/compose.yaml](docker/compose.yaml).

From the repository root, start PostgreSQL with:

```bash
docker compose -f docker/compose.yaml up -d
```

This creates one container:

- `factory-dashboard-db` for PostgreSQL 18

It also creates one named volume and one bridge network:

- `pgdata`
- `backend-net`

The database listens on `localhost:5432` and uses these default credentials:

- database: `factory_dashboard`
- user: `dashboard_user`
- password: `changeme_in_prod`

## Start Backend

The backend uses the `local` Spring profile for development.

Windows PowerShell:

```powershell
Set-Location backend
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

macOS / Linux:

```bash
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

The local profile connects to PostgreSQL at `jdbc:postgresql://localhost:5432/factory_dashboard`.

Verify the backend is running with:

```text
http://localhost:8080/api/v1/health
```

Expected response message:

- `Application is running`

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

The OpenAPI document is available at:

```text
http://localhost:8080/v3/api-docs
```

## Start Frontend

Install dependencies and start the Vite dev server from the `frontend` directory:

```bash
cd frontend
npm install
npm run dev
```

The frontend uses this environment variable:

- `VITE_API_BASE_URL`: backend base URL, defaulting to `http://localhost:8080`

If you need a custom value, create `frontend/.env`:

```env
VITE_API_BASE_URL=http://localhost:8080
```

By default, Vite runs on:

```text
http://localhost:5173
```

## Verify Everything

Follow this order for a clean local startup:

1. Start the database with `docker compose -f docker/compose.yaml up -d`.
2. Start the backend with the local Spring profile.
3. Open Swagger UI at `http://localhost:8080/swagger-ui.html` and confirm the API loads.
4. Start the frontend with `npm run dev`.
5. Open `http://localhost:5173` and confirm you can reach the login and dashboard pages.

## Shutdown

Stop the stack in the reverse order:

```bash
docker compose -f docker/compose.yaml down
```

Then stop the backend and frontend terminals if they are still running.

## Common Problems

- Database connection: confirm PostgreSQL is running, the port `5432` is free, and the `local` backend profile is active.
- Flyway: if the backend fails during startup, check that the database is clean or that the existing schema matches the current migrations.
- Port conflicts: backend uses `8080`, frontend uses `5173`, and PostgreSQL uses `5432` by default.
- JWT: the backend reads JWT settings from environment variables or defaults in `application.yml`; if auth fails, confirm the backend is using the expected secret.
- CORS: if browser requests fail from the frontend, confirm the backend allows the frontend origin and that the frontend is pointing to the correct API base URL.
- Docker: if the database container will not start, verify Docker Desktop or the Docker daemon is running.

## Notes

- The backend is the source of truth for all implemented features.
- The frontend only consumes APIs that already exist in the repository.
- Task module UI is not included yet because the backend task implementation is still pending.
