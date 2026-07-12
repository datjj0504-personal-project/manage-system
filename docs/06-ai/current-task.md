# Current Task (2026-07-12)

## Objective

Start Sprint 3 by implementing the project access foundation and Project CRUD module on top of the completed authentication and user baseline.

## In Scope

- Project membership foundation:
	- `Project` entity
	- `ProjectMember` entity
	- membership role model (`OWNER`, `ADMIN`, `MEMBER`)
- Project APIs:
	- Create project
	- Get project detail
	- List accessible projects
	- Update project
	- Delete project
- Authorization rules:
	- only authenticated users can access project APIs
	- only members can view project data
	- only owners can delete projects
- Persistence baseline:
	- project and membership repositories
	- project DTOs and mapper layer
	- Flyway migration for `projects` and `project_members`
- Validation:
	- request validation for project payloads
	- membership and ownership business validation
- Testing:
	- unit tests for project service rules
	- API-level tests for project endpoints

## Non-Goals (Current Iteration)

- Task CRUD
- Comment/attachment
- Notification
- Admin features

## Acceptance Checklist

- Project endpoints return consistent `ApiResponse` payloads.
- Authenticated users can create projects and become initial owners.
- Accessible project list returns only projects the current user belongs to.
- Non-members cannot access unrelated projects.
- Only owners can delete projects.
- Flyway migration for project tables applies cleanly on local/dev profiles.
- Unit tests for project service logic and API tests for project endpoints pass.
