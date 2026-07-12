# Refresh Token API

## Endpoint

- Method: `POST`
- Path: `/api/v1/auth/refresh`
- Authentication: Not required

## Purpose

Issue a new access token from a valid refresh token.

## Request Body

| Field | Type | Required | Rules |
|---|---|---|---|
| refreshToken | string | Yes | valid, unexpired token |

## Success Response

Status: `200 OK`

`data` fields:

| Field | Type | Description |
|---|---|---|
| accessToken | string | New access JWT |
| refreshToken | string | Rotated refresh JWT |
| tokenType | string | Always `Bearer` |
| expiresIn | number | Access token lifetime in seconds |

## Validation and Error Cases

| Status | When |
|---|---|
| 400 | Request Validation failed |
| 401 | Refresh token is invalid or expired |
| 500 | Unexpected server error |

## Notes

- Refresh token rotation is recommended after successful refresh.
- Response must follow the standard API response envelope defined in `docs/03-api/auth.md`.
