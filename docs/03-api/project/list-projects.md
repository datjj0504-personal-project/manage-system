# List Projects API

## Endpoint

- Method: `GET`
- Path: `/api/v1/projects`
- Authentication: Required

## Purpose

List accessible `Project` records for the authenticated `User`.

## Query Parameters

| Param | Type | Required | Description |
|---|---|---|---|
| page | number | No | Default `0` |
| size | number | No | Default `20`, max `100` |
| sort | string | No | Example: `createdAt,desc` |
| q | string | No | Name keyword Filtering |

## Success Response

Status: `200 OK`

`data` fields:

| Field | Type | Description |
|---|---|---|
| items | array | `Project` summaries |
| page | number | Current page |
| size | number | Page size |
| totalElements | number | Total records |
| totalPages | number | Total pages |

## Validation and Error Cases

| Status | When |
|---|---|
| 400 | Invalid Pagination, Sorting, or Filtering request |
| 401 | Missing or invalid JWT |
| 500 | Unexpected server error |

## Notes

- Only `Project` records accessible to the authenticated `User` should be returned.
- Response must follow the standard API response envelope defined in `docs/03-api/project.md`.