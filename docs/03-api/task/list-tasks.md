# List Tasks API

## Endpoint

- Method: `GET`
- Path: `/api/v1/projects/{projectId}/tasks`
- Authentication: Required

## Purpose

List `Task` records in an accessible `Project`.

## Path Parameters

| Param | Type | Required | Description |
|---|---|---|---|
| projectId | string | Yes | Parent `Project` identifier |

## Query Parameters

| Param | Type | Required | Description |
|---|---|---|---|
| page | number | No | Default `0` |
| size | number | No | Default `20`, max `100` |
| sort | string | No | Example: `dueDate,asc` |
| status | string | No | Filter by `Status` |
| priority | string | No | Filter by `Priority` |
| assigneeId | string | No | Filter by `Assignee` |
| q | string | No | Title keyword Filtering |

## Success Response

Status: `200 OK`

`data` fields:

| Field | Type | Description |
|---|---|---|
| items | array | `Task` summaries |
| page | number | Current page |
| size | number | Page size |
| totalElements | number | Total records |
| totalPages | number | Total pages |

## Validation and Error Cases

| Status | When |
|---|---|
| 400 | Invalid Pagination, Sorting, or Filtering request |
| 401 | Missing or invalid JWT |
| 403 | Authenticated `User` is not a `Member` of the `Project` |
| 404 | `Project` not found |
| 500 | Unexpected server error |

## Notes

- Only `Task` records inside the requested accessible `Project` should be returned.
- Response must follow the standard API response envelope defined in `docs/03-api/task.md`.