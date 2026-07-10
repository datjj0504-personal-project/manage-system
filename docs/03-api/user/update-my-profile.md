# Update My Profile API

## Endpoint

- Method: `PATCH`
- Path: `/api/v1/users/me`
- Authentication: Required

## Purpose

Update allowed fields of the current authenticated `User` profile.

## Request Body

| Field | Type | Required | Rules |
|---|---|---|---|
| fullName | string | No | 2-100 characters |
| avatarUrl | string | No | valid URL |

## Success Response

Status: `200 OK`

`data` fields:

Returns updated profile with the same shape as `GET /me`.

## Validation and Error Cases

| Status | When |
|---|---|
| 400 | Request Validation failed |
| 401 | Missing or invalid JWT |
| 404 | `User` not found |
| 500 | Unexpected server error |

## Notes

- A `User` may only modify own profile.
- Response must follow the standard API response envelope defined in `docs/03-api/user.md`.