# Logging Rules

## Framework

- Use SLF4J API with Logback backend.

## Log Levels

- ERROR: failed operation requiring attention.
- WARN: unexpected but recoverable behavior.
- INFO: important business or lifecycle events.
- DEBUG: detailed troubleshooting logs (non-production by default).

## Security and Privacy

- Never use `System.out.println()`.
- Never log password.
- Never log JWT or access token.
- Never log sensitive personal data unless masked.

## Exception Logging

- Always log exception stacktrace at handling boundary.
- Include correlation context when available (request id, user id).