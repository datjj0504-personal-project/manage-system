# Security Rules

## Authentication and Authorization

- Use BCrypt password encoder for password hashing.
- Use JWT for stateless authentication.
- Enforce role-based authorization.

## Transport Security

- Production must use HTTPS only.

## Input and Output Safety

- Validate every request payload.
- Never trust frontend validation alone.
- Sanitize inputs where required.
- Never expose Entity directly in API responses.

## Sensitive Data Handling

- Do not store plaintext passwords.
- Do not log credentials or tokens.
- Restrict error messages to safe details for clients.