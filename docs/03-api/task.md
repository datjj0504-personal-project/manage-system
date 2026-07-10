# Task API (v1)

## Scope

CRUD for `Task` within a `Project`.

Base path: `/api/v1/projects/{projectId}/tasks`

## Endpoints

| Method | Path | Description | Auth Required |
|---|---|---|---|
| POST | `/` | Create `Task` | Yes |
| GET | `/` | List `Task` in `Project` | Yes |
| GET | `/{taskId}` | Get `Task` detail | Yes |
| PATCH | `/{taskId}` | Update `Task` | Yes |
| DELETE | `/{taskId}` | Delete `Task` | Yes |

## API Contracts

### POST /

Request body:

| Field | Type | Required | Rules |
|---|---|---|---|
| title | string | Yes | 3-200 characters |
| description | string | No | maximum 5000 characters |
| status | string | No | `TODO`, `IN_PROGRESS`, `DONE` |
| priority | string | No | `LOW`, `MEDIUM`, `HIGH` |
| dueDate | string | No | ISO-8601 date-time |
| assigneeId | string | No | Must be `Member` of `Project` |

Success response data:

| Field | Type | Description |
|---|---|---|
| id | string | `Task` identifier |
| projectId | string | Parent `Project` identifier |
| title | string | Task title |
| description | string/null | Task description |
| status | string | Current `Status` |
| priority | string | Current `Priority` |
| dueDate | string/null | Due date-time |
| assigneeId | string/null | `Assignee` (`User`) identifier |
| createdAt | string | ISO-8601 timestamp |
| updatedAt | string | ISO-8601 timestamp |

### GET /

Query parameters:

| Param | Type | Required | Description |
|---|---|---|---|
| page | number | No | Default `0` |
| size | number | No | Default `20`, max `100` |
| sort | string | No | Example: `dueDate,asc` |
| status | string | No | Filter by `Status` |
| priority | string | No | Filter by `Priority` |
| assigneeId | string | No | Filter by `Assignee` |
| q | string | No | Title keyword filtering |

Success response data:

| Field | Type | Description |
|---|---|---|
| items | array | `Task` summaries |
| page | number | Current page |
| size | number | Page size |
| totalElements | number | Total records |
| totalPages | number | Total pages |

### GET /{taskId}

Success response data:

Returns full `Task` detail.

### PATCH /{taskId}

Request body:

Supports partial update for:

- `title`
- `description`
- `status`
- `priority`
- `dueDate`
- `assigneeId`

Success response data:

Returns updated `Task` detail.

### DELETE /{taskId}

Success response data:

`data` is `null`.

## Authorization and Business Rules

- Only `Member` can access `Task` within a `Project`.
- Completed `Task` (`status = DONE`) cannot be modified unless reopened.

## Standard Error Cases

| Status | When |
|---|---|
| 400 | Validation failed |
| 401 | Missing or invalid JWT |
| 403 | Insufficient permission |
| 404 | `Project` or `Task` not found |
| 409 | Business rule conflict |
| 500 | Unexpected server error |
