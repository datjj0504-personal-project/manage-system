# Get Project Detail API

## Endpoint

- Method: `GET`
- Path: `/api/v1/projects/{projectId}`
- Authentication: Required

## Purpose

Return detail for one accessible `Project`.

## Path Parameters

| Param | Type | Required | Description |
|---|---|---|---|
| projectId | string | Yes | `Project` identifier |

## Success Response

Status: `200 OK`

`data` fields:

| Field | Type | Description |
|---|---|---|
| id | string | `Project` identifier |
| name | string | Project name |
| description | string/null | Project description |
| ownerId | string | `Owner` (`User`) identifier |
| createdAt | string | ISO-8601 timestamp |
| updatedAt | string | ISO-8601 timestamp |

## Validation and Error Cases

| Status | When |
|---|---|
| 401 | Missing or invalid JWT |
| 403 | Authenticated `User` is not a `Member` of the `Project` |
| 404 | `Project` not found |
| 500 | Unexpected server error |

## Notes

- Only a `Member` can access `Project` detail.
- Response must follow the standard API response envelope defined in `docs/03-api/project.md`.