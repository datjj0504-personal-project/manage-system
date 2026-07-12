# Login API

## Endpoint

- Method: `POST`
- Path: `/api/v1/auth/login`
- Authentication: Not required

## Purpose

Authenticate a `User` and issue JWT credentials.

## Request Body

| Field | Type | Required | Rules |
|---|---|---|---|
| email | string | Yes | valid email |
| password | string | Yes | non-empty |

## Success Response

Status: `200 OK`

`data` fields:

| Field | Type | Description |
|---|---|---|
| accessToken | string | JWT for API access |
| refreshToken | string | JWT for token refresh |
| tokenType | string | Always `Bearer` |
| expiresIn | number | Access token lifetime in seconds |

## Validation and Error Cases

| Status | When |
|---|---|
| 400 | Request Validation failed |
| 401 | Invalid credentials |
| 500 | Unexpected server error |

## Notes

- Access to protected APIs must use `Authorization: Bearer <accessToken>`.
- Response must follow the standard API response envelope defined in `docs/03-api/auth.md`.
