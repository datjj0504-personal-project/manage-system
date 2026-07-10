# Testing Rules

## Test Stack

- JUnit 5
- Mockito
- MockMvc
- @DataJpaTest

## Layer Strategy

- Service layer: unit tests for business logic.
- Controller layer: API contract tests with MockMvc.
- Repository layer: data access tests with @DataJpaTest.

## Coverage Goal

- Target minimum 80% coverage on critical business services.

## Quality Rules

- Tests must be deterministic and independent.
- Use meaningful test names with scenario + expected result.
- Cover positive and negative cases, especially Validation and authorization failures.