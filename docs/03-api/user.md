# User API (v1)

## Scope

Current authenticated `User` profile management.

Base path: `/api/v1/users`

## Endpoints

| Method | Path | Description | Auth Required |
|---|---|---|---|
| GET | `/me` | Get current `User` profile | Yes |
| PATCH | `/me` | Update current `User` profile | Yes |
| PUT | `/me/password` | Change current `User` password | Yes |

## API Contracts

### GET /me

Success response data:

| Field | Type | Description |
|---|---|---|
| id | string | `User` identifier |
| email | string | Account email |
| fullName | string | Display name |
| avatarUrl | string/null | Avatar URL |
| createdAt | string | ISO-8601 timestamp |
| updatedAt | string | ISO-8601 timestamp |

### PATCH /me

Request body:

| Field | Type | Required | Rules |
|---|---|---|---|
| fullName | string | No | 2-100 characters |
| avatarUrl | string | No | valid URL |

Success response data:

Returns updated profile with the same shape as `GET /me`.

### PUT /me/password

Request body:

| Field | Type | Required | Rules |
|---|---|---|---|
| currentPassword | string | Yes | non-empty |
| newPassword | string | Yes | minimum 8 characters |

Success response data:

`data` is `null`.

## Authorization

- JWT `Bearer` token is required.
- `User` may only access and modify own profile.

## Standard Error Cases

| Status | When |
|---|---|
| 400 | Validation failed |
| 401 | Missing or invalid JWT |
| 403 | Forbidden operation |
| 404 | `User` not found |
| 500 | Unexpected server error |
