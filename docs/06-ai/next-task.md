# Next Tasks

## Phase 1: Project Access Foundation

1. Implement `Project` and `ProjectMember` entities plus membership role model.
2. Add Flyway migration for `projects` and `project_members`.
3. Implement Project CRUD with owner/member authorization rules.
4. Add project DTOs, mapper, service, controller, and tests.

## Phase 2: Task Module

1. Implement `Task` entity, repository, DTOs, mapper, service, and controller.
2. Enforce project-bound membership access control for task APIs.
3. Support assignee validation within the same project.
4. Add task unit and API tests.

## Phase 3: Query Capabilities and Hardening

1. Add pagination, sorting, and filtering for project and task list endpoints.
2. Expand test coverage with repository-level and protected-endpoint checks.
3. Refine security configuration to remove startup warning and reduce duplication.

## Phase 4: Collaboration and Documentation Sync

1. Implement comments on tasks with membership checks.
2. Implement attachment upload metadata flow.
3. Align docs folders (`03-api`, `04-database`, `07-project-management`) with implemented features.
4. Finalize Docker runbook and compose validation.

## Cross-Cutting Rules

- Keep strict layered architecture.
- Do not expose entities directly in controllers.
- Keep all schema changes in Flyway migrations.
- Keep AI status files honest and synchronized with verified source code.
