# API Documentation

This folder contains the API design for the Task Management System.

## Structure

- Module-level files (`auth.md`, `user.md`, `project.md`, `task.md`) define the high-level contract for each API area.
- Subfolders (`auth/`, `user/`, `project/`, `task/`) contain endpoint-level documentation for each operation.

## Modules

- `auth.md`: Authentication API overview.
- `user.md`: Current authenticated `User` profile API overview.
- `project.md`: `Project` API overview and authorization rules.
- `task.md`: `Task` API overview and project-scoped business rules.

## Endpoint Folders

- `auth/`: Register, login, and refresh token endpoints.
- `user/`: Get profile, update profile, and change password endpoints.
- `project/`: Create, list, detail, update, and delete `Project` endpoints.
- `task/`: Create, list, detail, update, and delete `Task` endpoints.

## How To Use This Folder

- Read the module-level file first to understand shared scope and rules.
- Read the endpoint file next for request, response, and error details.
- Keep endpoint docs aligned with module-level contracts.

## Maintenance Rules

- Keep terminology consistent with `docs/00-project-context/glossary.md`.
- Update both module-level and endpoint-level docs when API behavior changes.
- Do not document endpoints here that are not planned in `docs/01-requirement`.
