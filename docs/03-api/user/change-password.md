# Change Password API

## Endpoint

- Method: `PUT`
- Path: `/api/v1/users/me/password`
- Authentication: Required

## Purpose

Change the password of the current authenticated `User`.

## Request Body

| Field | Type | Required | Rules |
|---|---|---|---|
| currentPassword | string | Yes | non-empty |
| newPassword | string | Yes | minimum 8 characters |

## Success Response

Status: `200 OK`

`data` is `null`.

## Validation and Error Cases

| Status | When |
|---|---|
| 400 | Request Validation failed |
| 401 | Missing or invalid JWT |
| 403 | Current password is incorrect or operation is forbidden |
| 404 | `User` not found |
| 500 | Unexpected server error |

## Notes

- New password must be stored using secure hashing.
- Response must follow the standard API response envelope defined in `docs/03-api/user.md`.