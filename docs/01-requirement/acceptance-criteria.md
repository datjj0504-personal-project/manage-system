# Acceptance Criteria

## Authentication

- A new `User` can register with valid required fields.
- Registration fails when email already exists or request Validation fails.
- A valid login request returns JWT access and refresh tokens.
- Invalid credentials return an authentication error.
- A valid refresh request returns a new access token.

## User Profile

- An authenticated `User` can retrieve own profile.
- An authenticated `User` can update allowed profile fields.
- An authenticated `User` can change password with correct current password.
- Unauthorized requests to user profile APIs are rejected.

## Project

- An authenticated `User` can create a `Project`.
- A `Member` can view accessible `Project` detail.
- A `Member` can list accessible `Project` records with Pagination support.
- An `Owner` can update own `Project`.
- Only an `Owner` can delete a `Project`.
- A non-member cannot access a `Project`.

## Task

- A `Member` can create a `Task` in an accessible `Project`.
- A `Member` can update allowed `Task` fields.
- A `Member` can assign a `Task` only to a valid `Assignee` within the same `Project`.
- `Task` listing supports Pagination, Sorting, and Filtering.
- A completed `Task` cannot be modified unless reopened.
- A non-member cannot access `Task` records in a `Project`.

## Collaboration (Planned)

- A `Member` can add a `Comment` to a `Task`.
- A `Member` can upload an `Attachment` to a `Task`.

## Cross-Cutting

- All protected APIs require valid authentication.
- All API responses follow the standard response envelope.
- Validation failures return clear client-facing error responses.
- Key business features are covered by automated tests before completion is claimed.
