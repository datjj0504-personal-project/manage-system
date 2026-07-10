# Tables (v1)

## users

- id (PK)
- email (unique)
- password_hash
- full_name
- avatar_url
- created_at
- updated_at
- deleted_at (optional soft delete)

## projects

- id (PK)
- owner_id (FK -> users.id)
- name
- description
- created_at
- updated_at
- deleted_at (optional soft delete)

## project_members

- id (PK)
- project_id (FK -> projects.id)
- user_id (FK -> users.id)
- role (`OWNER`, `ADMIN`, `MEMBER`)
- created_at

Unique constraint: `(project_id, user_id)`

## tasks

- id (PK)
- project_id (FK -> projects.id)
- assignee_id (FK -> users.id, nullable)
- title
- description
- status (`TODO`, `IN_PROGRESS`, `DONE`)
- priority (`LOW`, `MEDIUM`, `HIGH`)
- due_date
- created_at
- updated_at
- deleted_at (optional soft delete)

## Planned Tables

- comments
- attachments

## Index Guidance

- projects(owner_id)
- project_members(project_id, role)
- tasks(project_id, status, priority)
- tasks(assignee_id)
