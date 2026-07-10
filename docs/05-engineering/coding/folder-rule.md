# Folder Rules

## Layer Responsibilities

- controller: Receive request, trigger Validation, return response only.
- service: Business logic and use-case orchestration.
- repository: Database access and query logic only.
- mapper: Entity <-> DTO transformation.
- dto: Request and response payload classes only.
- entity: Persistence models only.
- config: Spring and framework configuration.
- exception: Exceptions and global exception handling.
- security: Authentication and authorization components.
- common: Shared objects and utilities used across modules.

## Boundary Rules

- Controller must not access Repository directly.
- Repository must not contain business rules.
- Entity must not be returned directly by API.
- Service should be the main place for transaction boundaries.