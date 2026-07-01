# Coding Principles

## Primary Principles

The project follows the following principles.

- SOLID
- DRY
- KISS
- YAGNI
- Separation of Concerns
- Single Responsibility
- Clean Code

---

## Architecture

Use Layered Architecture.

Controller

↓

Service

↓

Repository

↓

Database

Controllers must never contain business logic.

Repositories must never contain business rules.

Services coordinate business behavior.

---

## Code Style

Prefer readability over cleverness.

Small methods.

Small classes.

Meaningful naming.

Immutable DTO whenever possible.

Avoid static state.

Avoid magic numbers.

Prefer composition over inheritance.

Prefer constructor injection.

Avoid field injection.

---

## Error Handling

Use custom exceptions.

Use GlobalExceptionHandler.

Never expose internal exception details.

Always return consistent API responses.

---

## Database

Normalize data when reasonable.

Avoid N+1 queries.

Prefer lazy loading.

Index foreign keys.

Never use SELECT *.

Use migration scripts.

---

## Security

Never trust client input.

Validate every request.

Encrypt passwords.

Never expose entities directly.

Sanitize inputs.

Use principle of least privilege.

---

## Performance

Measure before optimizing.

Use pagination.

Avoid unnecessary object creation.

Batch operations when appropriate.

Cache only when justified.

---

## Documentation

Document architecture decisions.

Keep README updated.

Document APIs.

Document breaking changes.

---

## Testing

Test business logic.

Avoid testing framework internals.

Prefer deterministic tests.

Tests should be independent.