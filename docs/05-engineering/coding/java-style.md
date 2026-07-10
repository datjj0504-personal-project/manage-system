# Java Style Rules

## Language Baseline

- Java version: 21

## Naming

- Class: PascalCase
- Method: camelCase
- Variable: camelCase
- Constant: UPPER_SNAKE_CASE
- Package: lowercase

## Code Size Guidance

- Preferred max class length: 300 lines
- Preferred max method length: 30 lines

## Layer Discipline

- Controller: no business logic
- Service: business logic orchestration
- Repository: database access only

## Dependency Injection

- Use constructor injection only
- Do not use field injection

## DTO and Nullability

- Use record for DTO where practical
- Use Optional carefully; do not overuse in entities
- Avoid null return when clearer alternatives exist
- Use `final` where possible for immutability and clarity