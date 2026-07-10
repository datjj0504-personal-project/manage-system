# Get Task Detail API

## Endpoint

- Method: `GET`
- Path: `/api/v1/projects/{projectId}/tasks/{taskId}`
- Authentication: Required

## Purpose

Return detail for one `Task` within a `Project`.

## Path Parameters

| Param | Type | Required | Description |
|---|---|---|---|
| projectId | string | Yes | Parent `Project` identifier |
| taskId | string | Yes | `Task` identifier |

## Success Response

Status: `200 OK`

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
| 401 | Missing or invalid JWT |
| 403 | Authenticated `User` is not a `Member` of the `Project` |
| 404 | `Project` or `Task` not found |
| 500 | Unexpected server error |

## Notes

- Only a `Member` can access `Task` detail within a `Project`.
- Response must follow the standard API response envelope defined in `docs/03-api/task.md`.