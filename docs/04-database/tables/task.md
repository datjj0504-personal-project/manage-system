# Task Table

## Purpose

Store `Task` records belonging to a `Project` and optionally assigned to an `Assignee`.

## Proposed Columns

| Column | Type | Required | Notes |
|---|---|---|---|
| id | UUID or BIGINT | Yes | Primary key |
| project_id | UUID or BIGINT | Yes | FK to `projects.id` |
| assignee_id | UUID or BIGINT | No | FK to `users.id` |
| title | varchar | Yes | Task title |
| description | text | No | Task description |
| status | varchar | Yes | `TODO`, `IN_PROGRESS`, `DONE` |
| priority | varchar | Yes | `LOW`, `MEDIUM`, `HIGH` |
| due_date | timestamp | No | Task due date-time |
| created_at | timestamp | Yes | Audit creation time |
| updated_at | timestamp | Yes | Audit update time |
| deleted_at | timestamp | No | Optional Soft Delete support |

## Constraints

- Primary key on `id`
- Foreign key `project_id -> projects.id`
- Foreign key `assignee_id -> users.id`
- `title` must not be null
- `status` must be limited to supported values
- `priority` must be limited to supported values

## Index Guidance

- Index on `project_id`
- Index on `assignee_id`
- Composite index on `(project_id, status, priority)`

## Notes

- `assignee_id` must reference a `User` who is a `Member` of the same `Project`.
- Business rules for completed `Task` updates are enforced at application layer.
