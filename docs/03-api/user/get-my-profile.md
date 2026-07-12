# Get My Profile API

## Endpoint

- Method: `GET`
- Path: `/api/v1/users/me`
- Authentication: Required

## Purpose

Return the current authenticated `User` profile.

## Success Response

Status: `200 OK`

`data` fields:

| Field | Type | Description |
|---|---|---|
| id | string | `User` identifier |
| email | string | Account email |
| fullName | string | Display name |
| avatarUrl | string/null | Avatar URL |
| createdAt | string | ISO-8601 timestamp |
| updatedAt | string | ISO-8601 timestamp |

## Validation and Error Cases

| Status | When |
|---|---|
| 401 | Missing or invalid JWT |
| 404 | `User` not found |
| 500 | Unexpected server error |

## Notes

- A `User` may only access own profile.
- Response must follow the standard API response envelope defined in `docs/03-api/user.md`.