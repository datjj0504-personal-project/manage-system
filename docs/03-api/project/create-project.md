# Create Project API

## Endpoint

- Method: `POST`
- Path: `/api/v1/projects`
- Authentication: Required

## Purpose

Create a new `Project` for the authenticated `User`.

## Request Body

| Field | Type | Required | Rules |
|---|---|---|---|
| name | string | Yes | 3-120 characters |
| description | string | No | maximum 2000 characters |

## Success Response

Status: `201 Created`

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
| 400 | Request Validation failed |
| 401 | Missing or invalid JWT |
| 500 | Unexpected server error |

## Notes

- The authenticated `User` becomes the `Owner` of the created `Project`.
- Response must follow the standard API response envelope defined in `docs/03-api/project.md`.