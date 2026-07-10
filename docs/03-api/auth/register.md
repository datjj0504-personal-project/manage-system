# Register API

## Endpoint

- Method: `POST`
- Path: `/api/v1/auth/register`
- Authentication: Not required

## Purpose

Create a new `User` account.

## Request Body

| Field | Type | Required | Rules |
|---|---|---|---|
| fullName | string | Yes | 2-100 characters |
| email | string | Yes | valid email, unique |
| password | string | Yes | minimum 8 characters |

## Success Response

Status: `201 Created`

`data` fields:

| Field | Type | Description |
|---|---|---|
| id | string | `User` identifier |
| email | string | Registered email |
| fullName | string | Display name |
| createdAt | string | ISO-8601 timestamp |

## Validation and Error Cases

| Status | When |
|---|---|
| 400 | Request Validation failed |
| 409 | Email already exists |
| 500 | Unexpected server error |

## Notes

- Password must be stored using secure hashing.
- Response must follow the standard API response envelope defined in `docs/03-api/auth.md`.
