# Project Tables

## Purpose

Store `Project` data and the relationship between `User` and `Project` through membership.

## projects

| Column | Type | Required | Notes |
|---|---|---|---|
| id | UUID or BIGINT | Yes | Primary key |
| owner_id | UUID or BIGINT | Yes | FK to `users.id` |
| name | varchar | Yes | Project name |
| description | text | No | Project description |
| created_at | timestamp | Yes | Audit creation time |
| updated_at | timestamp | Yes | Audit update time |
| deleted_at | timestamp | No | Optional Soft Delete support |

## project_members

| Column | Type | Required | Notes |
|---|---|---|---|
| id | UUID or BIGINT | Yes | Primary key |
| project_id | UUID or BIGINT | Yes | FK to `projects.id` |
| user_id | UUID or BIGINT | Yes | FK to `users.id` |
| role | varchar | Yes | `OWNER`, `ADMIN`, `MEMBER` |
| created_at | timestamp | Yes | Membership creation time |

## Constraints

- Primary key on `projects.id`
- Primary key on `project_members.id`
- Foreign key `projects.owner_id -> users.id`
- Foreign key `project_members.project_id -> projects.id`
- Foreign key `project_members.user_id -> users.id`
- Unique constraint on `(project_id, user_id)` in `project_members`

## Index Guidance

- Index on `projects.owner_id`
- Index on `project_members.project_id`
- Composite index on `project_members(project_id, role)`

## Notes

- Each `Project` must have one `Owner`.
- Membership drives `Project` and `Task` authorization rules.
