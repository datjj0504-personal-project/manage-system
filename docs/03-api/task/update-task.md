# Update Task API

## Endpoint

- Method: `PATCH`
- Path: `/api/v1/projects/{projectId}/tasks/{taskId}`
- Authentication: Required

## Purpose

Update allowed fields of an existing `Task`.

## Path Parameters

| Param | Type | Required | Description |
|---|---|---|---|
| projectId | string | Yes | Parent `Project` identifier |
| taskId | string | Yes | `Task` identifier |

## Request Body

Supports partial update for:

- `title`
- `description`
- `status`
- `priority`
- `dueDate`
- `assigneeId`

## Success Response

Status: `200 OK`

`data` fields:

Returns updated `Task` detail.

## Validation and Error Cases

| Status | When |
|---|---|
| 400 | Request Validation failed |
| 401 | Missing or invalid JWT |
| 403 | Authenticated `User` is not permitted to update the `Task` |
| 404 | `Project` or `Task` not found |
| 409 | Business rule conflict |
| 500 | Unexpected server error |

## Notes

- Completed `Task` (`status = DONE`) cannot be modified unless reopened.
- Response must follow the standard API response envelope defined in `docs/03-api/task.md`.