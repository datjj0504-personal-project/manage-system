# Architecture

## High-Level Flow

Frontend

↓

REST API

↓

Controller

↓

Service

↓

Repository

↓

Database

## Layer Responsibilities

- Controller: HTTP request and response mapping, Validation trigger, no business logic.
- Service: Business rules, authorization checks, transaction boundaries.
- Repository: Data access only, query optimization, no business rules.
- Database: Persistent storage with migration-managed schema.

## Cross-Cutting Concerns

- Security: JWT-based authentication and role-based authorization.
- Error handling: Centralized by GlobalExceptionHandler.
- Mapping: DTO and Entity conversion by mapper layer.
- Documentation: OpenAPI and Markdown docs stay aligned.

## Principles

- Layered Architecture
- SOLID
- Clean Code
- RESTful API
- Separation of Concerns