# Auth API (v1)

## Scope

Authentication for `User` account access using JWT.

Base path: `/api/v1/auth`

## Endpoints

| Method | Path | Description | Auth Required |
|---|---|---|---|
| POST | `/register` | Register new `User` | No |
| POST | `/login` | Authenticate and issue tokens | No |
| POST | `/refresh` | Refresh access token | No |

## API Contracts

### POST /register

Request body:

| Field | Type | Required | Rules |
|---|---|---|---|
| fullName | string | Yes | 2-100 characters |
| email | string | Yes | valid email, unique |
| password | string | Yes | minimum 8 characters |

Success response data:

| Field | Type | Description |
|---|---|---|
| id | string | `User` identifier |
| email | string | Registered email |
| fullName | string | Display name |
| createdAt | string | ISO-8601 timestamp |

### POST /login

Request body:

| Field | Type | Required | Rules |
|---|---|---|---|
| email | string | Yes | valid email |
| password | string | Yes | non-empty |

Success response data:

| Field | Type | Description |
|---|---|---|
| accessToken | string | JWT for API access |
| refreshToken | string | JWT for token refresh |
| tokenType | string | Always `Bearer` |
| expiresIn | number | Access token lifetime (seconds) |

### POST /refresh

Request body:

| Field | Type | Required | Rules |
|---|---|---|---|
| refreshToken | string | Yes | valid, unexpired token |

Success response data:

| Field | Type | Description |
|---|---|---|
| accessToken | string | New access JWT |
| refreshToken | string | Rotated refresh JWT |
| tokenType | string | Always `Bearer` |
| expiresIn | number | Access token lifetime (seconds) |

## Standard Response Envelope

All responses follow:

| Field | Type | Description |
|---|---|---|
| success | boolean | Request outcome |
| message | string | Human-readable message |
| data | object/null | Payload when applicable |

## Standard Error Cases

| Status | When |
|---|---|
| 400 | Validation failed |
| 401 | Invalid credentials or token |
| 409 | Email already exists |
| 500 | Unexpected server error |
