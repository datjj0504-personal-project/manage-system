# User Table

## Purpose

Store the registered `User` account and profile information.

## Proposed Columns

| Column | Type | Required | Notes |
|---|---|---|---|
| id | UUID or BIGINT | Yes | Primary key, final decision pending ADR |
| email | varchar | Yes | Unique login identifier |
| password_hash | varchar | Yes | Securely hashed password |
| full_name | varchar | Yes | Display name |
| avatar_url | varchar | No | Profile image URL |
| created_at | timestamp | Yes | Audit creation time |
| updated_at | timestamp | Yes | Audit update time |
| deleted_at | timestamp | No | Optional Soft Delete support |

## Constraints

- Primary key on `id`
- Unique constraint on `email`
- `email` must not be null
- `password_hash` must not be null

## Index Guidance

- Unique index on `email`

## Notes

- `User` is referenced by `Project` ownership and `Task` assignment.
- Plaintext password must never be stored.
