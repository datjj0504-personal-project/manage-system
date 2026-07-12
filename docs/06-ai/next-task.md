# Next Tasks

## Phase 1: Complete Foundation Gap

1. Finish authentication and user management module.
2. Add role model (Owner/Admin/Member) groundwork for project membership.
3. Strengthen exception taxonomy and API error contracts.
4. Add baseline test suite structure (unit + integration).

## Phase 2: Sprint 2 Catch-up

1. Implement Project CRUD with ownership and member authorization checks.
2. Implement Task CRUD with project-bound access control.
3. Add pagination, sorting, and filtering for project/task listing endpoints.
4. Add mapping and validation coverage for all request/response DTOs.

## Phase 3: Sprint 3 Targets

1. Implement comments on tasks with membership checks.
2. Implement attachment upload metadata flow.
3. Evaluate notification architecture (event-driven candidate).

## Phase 4: Sprint 4 Hardening

1. Expand test coverage with MockMvc and repository/service tests.
2. Refactor for package cohesion and reduced duplication.
3. Finalize Docker runbook and compose validation.
4. Align all docs (`03-api`, `04-database`, `07-progress`) with implemented features.

## Cross-Cutting Rules

- Keep strict layered architecture.
- Do not expose entities directly in controllers.
- Keep all schema changes in Flyway migrations.
- Update sprint report immediately after finishing each milestone.
