# Project API (v1)

## Scope

CRUD for `Project` and member-scoped access.

Base path: `/api/v1/projects`

## Endpoints

| Method | Path | Description | Auth Required |
|---|---|---|---|
| POST | `/` | Create `Project` | Yes |
| GET | `/` | List accessible `Project` | Yes |
| GET | `/{projectId}` | Get `Project` detail | Yes |
| PATCH | `/{projectId}` | Update `Project` | Yes |
| DELETE | `/{projectId}` | Delete `Project` | Yes |

## API Contracts

### POST /

Request body:

| Field | Type | Required | Rules |
|---|---|---|---|
| name | string | Yes | 3-120 characters |
| description | string | No | maximum 2000 characters |

Success response data:

| Field | Type | Description |
|---|---|---|
| id | string | `Project` identifier |
| name | string | Project name |
| description | string/null | Project description |
| ownerId | string | `Owner` (`User`) identifier |
| createdAt | string | ISO-8601 timestamp |
| updatedAt | string | ISO-8601 timestamp |

### GET /

Query parameters:

| Param | Type | Required | Description |
|---|---|---|---|
| page | number | No | Default `0` |
| size | number | No | Default `20`, max `100` |
| sort | string | No | Example: `createdAt,desc` |
| q | string | No | Name keyword filtering |

Success response data:

| Field | Type | Description |
|---|---|---|
| items | array | `Project` summaries |
| page | number | Current page |
| size | number | Page size |
| totalElements | number | Total records |
| totalPages | number | Total pages |

### GET /{projectId}

Success response data:

Returns full `Project` detail.

### PATCH /{projectId}

Request body:

| Field | Type | Required | Rules |
|---|---|---|---|
| name | string | No | 3-120 characters |
| description | string | No | maximum 2000 characters |

Success response data:

Returns updated `Project` detail.

### DELETE /{projectId}

Success response data:

`data` is `null`.

## Authorization

- Only `Member` can access a `Project`.
- Only `Owner` can delete a `Project`.

## Standard Error Cases

| Status | When |
|---|---|
| 400 | Validation failed |
| 401 | Missing or invalid JWT |
| 403 | Insufficient permission |
| 404 | `Project` not found |
| 500 | Unexpected server error |
