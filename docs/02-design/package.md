# Package Design

Root package: `dev.datnt.taskmanagement`

## Package Responsibilities

- config: Spring framework configuration classes.
- common: Shared response objects and common helpers.
- controller: HTTP endpoint layer.
- dto: Request and response Data Transfer Objects.
- entity: Persistence models.
- exception: Custom exceptions and global handler.
- mapper: DTO and Entity transformation.
- repository: Data access interfaces and query definitions.
- security: JWT and authentication/authorization support.
- service: Business logic and use-case orchestration.
- util: Reusable utilities with no business side effects.
- validation: Custom Validation components and validators.
- constant: Shared constants and stable value definitions.

## Dependency Direction

- controller -> service -> repository
- service -> mapper
- controller -> dto
- repository -> entity
- exception and common can be used across layers as needed

Any reverse dependency is considered an architecture violation.