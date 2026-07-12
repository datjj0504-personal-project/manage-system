# API Rules

## Response Envelope

Success response:

```json
{
    "success": true,
    "message": "Operation completed",
    "data": {}
}
```

Error response:

```json
{
    "success": false,
    "message": "Validation failed",
    "data": null
}
```

## Endpoint Conventions

- Use RESTful naming with plural resource names.
- Use `/api/v1` prefix for v1 endpoints.
- Use nouns for resources, not verbs in path.
- Use query parameters for Pagination, Sorting, and Filtering.

## Status Codes

- 200 OK: successful read/update
- 201 Created: resource created
- 204 No Content: successful delete or empty payload response
- 400 Bad Request: Validation error
- 401 Unauthorized: missing or invalid authentication
- 403 Forbidden: insufficient permission
- 404 Not Found: resource not found
- 409 Conflict: business rule conflict
- 500 Internal Server Error: unexpected server error

## DTO Rules

- Never expose Entity directly.
- Request and response must use DTO.
- Keep field names consistent across APIs unless versioned change is needed.