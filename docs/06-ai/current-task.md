# Current Task (2026-07-12)

## Objective

Continue Sprint 3 by implementing Task CRUD inside Project boundaries, on top of completed auth/user/project foundations.

## In Scope

- Task persistence and domain model:
	- `Task` entity
	- status model (`TODO`, `IN_PROGRESS`, `DONE`)
	- priority model (`LOW`, `MEDIUM`, `HIGH`)
- Task APIs:
	- Create task
	- Get task detail
	- List tasks in project
	- Update task
	- Delete task
- Authorization rules:
	- only authenticated users can access task APIs
	- only project members can access project tasks
	- assignment must stay within project membership
- Validation and business rules:
	- request validation for task payloads
	- due date and assignment validation
	- completed task modification rule (must reopen first)
- Persistence baseline:
	- task repository
	- task DTOs and mapper layer
	- Flyway migration for `tasks`
- Testing:
	- unit tests for task service rules
	- API-level tests for task endpoints

## Non-Goals (Current Iteration)

- Comment/attachment
- Notification
- Admin features
- Full collaboration module

## Acceptance Checklist

- Task endpoints return consistent `ApiResponse` payloads.
- Members can create/list/view tasks only in accessible projects.
- Non-members cannot access tasks in unrelated projects.
- Task assignee must be a member of the same project.
- Task list supports documented pagination/sorting/filtering.
- Completed task update rule is enforced.
- Flyway migration for task table applies cleanly on local/dev profiles.
- Unit tests for task service logic and API tests for task endpoints pass.
