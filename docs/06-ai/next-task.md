# Next Tasks

## Phase 1: Task Module Core

1. Implement `Task` entity, enums, repository, DTOs, mapper, service, and controller.
2. Add Flyway migration for `tasks` with constraints and indexes.
3. Implement task CRUD under `/api/v1/projects/{projectId}/tasks`.
4. Enforce project membership authorization for all task APIs.
5. Enforce assignee membership validation within the same project.

## Phase 2: Task Query and Rule Hardening

1. Add pagination, sorting, and filtering for task list endpoint.
2. Enforce completed-task update rule and reopen behavior.
3. Add task unit tests and API-level tests for success/failure paths.
4. Add repository-level tests for task query behavior.

## Phase 3: Documentation and Progress Sync

1. Update `docs/03-api/task/*` to match implemented contracts.
2. Update `docs/04-database/tables/task.md` and migration notes.
3. Update `docs/07-project-management` to reflect Sprint 3 project completion state.

## Phase 4: Collaboration and Documentation Sync

1. Implement comments on tasks with membership checks.
2. Implement attachment upload metadata flow.
3. Finalize Docker runbook and compose validation.
4. Align docs folders with collaboration features after implementation.

## Cross-Cutting Rules

- Keep strict layered architecture.
- Do not expose entities directly in controllers.
- Keep all schema changes in Flyway migrations.
- Keep AI status files honest and synchronized with verified source code.
