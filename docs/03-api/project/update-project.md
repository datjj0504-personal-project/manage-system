# Update Project API

## Endpoint

- Method: `PATCH`
- Path: `/api/v1/projects/{projectId}`
- Authentication: Required

## Purpose

Update allowed fields of an existing `Project`.

## Path Parameters

| Param | Type | Required | Description |
|---|---|---|---|
| projectId | string | Yes | `Project` identifier |

## Request Body

| Field | Type | Required | Rules |
|---|---|---|---|
| name | string | No | 3-120 characters |
| description | string | No | maximum 2000 characters |

## Success Response

Status: `200 OK`

`data` fields:

Returns updated `Project` detail.

## Validation and Error Cases

| Status | When |
|---|---|
| 400 | Request Validation failed |
| 401 | Missing or invalid JWT |
| 403 | Insufficient permission |
| 404 | `Project` not found |
| 500 | Unexpected server error |

## Notes

- Update rules should remain consistent with `Project` authorization design.
- Response must follow the standard API response envelope defined in `docs/03-api/project.md`.