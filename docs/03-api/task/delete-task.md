# Delete Task API

## Endpoint

- Method: `DELETE`
- Path: `/api/v1/projects/{projectId}/tasks/{taskId}`
- Authentication: Required

## Purpose

Delete an existing `Task` from a `Project`.

## Path Parameters

| Param | Type | Required | Description |
|---|---|---|---|
| projectId | string | Yes | Parent `Project` identifier |
| taskId | string | Yes | `Task` identifier |

## Success Response

Status: `204 No Content`

`data` is `null`.

## Validation and Error Cases

| Status | When |
|---|---|
| 401 | Missing or invalid JWT |
| 403 | Authenticated `User` is not permitted to delete the `Task` |
| 404 | `Project` or `Task` not found |
| 500 | Unexpected server error |

## Notes

- Delete behavior should remain consistent with `Project` membership authorization.
- Response must follow the standard API response envelope defined in `docs/03-api/task.md` when a body is returned by implementation choice.