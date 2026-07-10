# Delete Project API

## Endpoint

- Method: `DELETE`
- Path: `/api/v1/projects/{projectId}`
- Authentication: Required

## Purpose

Delete an existing `Project`.

## Path Parameters

| Param | Type | Required | Description |
|---|---|---|---|
| projectId | string | Yes | `Project` identifier |

## Success Response

Status: `204 No Content`

`data` is `null`.

## Validation and Error Cases

| Status | When |
|---|---|
| 401 | Missing or invalid JWT |
| 403 | Authenticated `User` is not the `Owner` |
| 404 | `Project` not found |
| 500 | Unexpected server error |

## Notes

- Only an `Owner` can delete a `Project`.
- Response must follow the standard API response envelope defined in `docs/03-api/project.md` when a body is returned by implementation choice.