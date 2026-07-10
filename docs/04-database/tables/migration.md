# Migration Guide

## Tooling

- Flyway is the standard migration tool.
- Migration scripts are stored in `backend/src/main/resources/db/migration`.

## Naming Convention

- Versioned migration format: `V<version>__<description>.sql`
- Example: `V1__init_user_project_task.sql`

## Migration Rules

- Never edit an already applied migration.
- Create a new migration for every schema change.
- Keep migrations idempotent only when required by operation type.
- Include indexes for foreign keys and frequent filter fields.
- Validate migrations in local profile before merge.

## Initial Migration Scope (v1)

- users
- projects
- project_members
- tasks

## Documentation Alignment

- Update `user.md`, `project.md`, and `task.md` when migration structure changes.
- Keep ERD, table docs, and SQL migrations synchronized.
- Document any new relationship or constraint before merge.

## Rollback Strategy

- Prefer forward-fix migrations.
- For severe production issues, prepare explicit rollback script reviewed by maintainers.
