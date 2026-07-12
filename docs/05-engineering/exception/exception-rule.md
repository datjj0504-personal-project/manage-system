# Exception Rules

## Core Rules

- Use GlobalExceptionHandler for centralized exception mapping.
- Do not expose internal stacktrace details in API responses.
- Never swallow exceptions silently.
- Catch broad `Exception` only at global boundary, not in business logic.

## Exception Types

- ValidationException: invalid request input.
- BusinessException: domain rule violation.
- ResourceNotFoundException: requested resource does not exist.
- AuthenticationException: invalid or missing credentials.
- AuthorizationException: authenticated but no permission.

## Mapping Guidance

- ValidationException -> 400
- AuthenticationException -> 401
- AuthorizationException -> 403
- ResourceNotFoundException -> 404
- BusinessException -> 409
- Unexpected exception -> 500