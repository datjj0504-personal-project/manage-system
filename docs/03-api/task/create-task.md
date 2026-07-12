# Create Task API

## Endpoint

- Method: `POST`
- Path: `/api/v1/projects/{projectId}/tasks`
- Authentication: Required

## Purpose

Create a new `Task` within a `Project`.

## Path Parameters

| Param | Type | Required | Description |
|---|---|---|---|
| projectId | string | Yes | Parent `Project` identifier |

## Request Body

| Field | Type | Required | Rules |
|---|---|---|---|
| title | string | Yes | 3-200 characters |
| description | string | No | maximum 5000 characters |
| status | string | No | `TODO`, `IN_PROGRESS`, `DONE` |
| priority | string | No | `LOW`, `MEDIUM`, `HIGH` |
| dueDate | string | No | ISO-8601 date-time |
| assigneeId | string | No | Must be `Member` of `Project` |

## Success Response

Status: `201 Created`

`data` fields:

| Field | Type | Description |
|---|---|---|
| id | string | `Task` identifier |
| projectId | string | Parent `Project` identifier |
| title | string | Task title |
| description | string/null | Task description |
| status | string | Current `Status` |
| priority | string | Current `Priority` |
| dueDate | string/null | Due date-time |
| assigneeId | string/null | `Assignee` (`User`) identifier |
| createdAt | string | ISO-8601 timestamp |
| updatedAt | string | ISO-8601 timestamp |

## Validation and Error Cases

| Status | When |
|---|---|
| 400 | Request Validation failed |
| 401 | Missing or invalid JWT |
| 403 | Authenticated `User` is not a `Member` of the `Project` |
| 404 | `Project` not found |
| 500 | Unexpected server error |

## Notes

- Only a `Member` can create a `Task` inside a `Project`.
- Response must follow the standard API response envelope defined in `docs/03-api/task.md`.