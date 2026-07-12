# Implement Feature Workflow

## Objective

Implement the feature described in `docs/08-ai/current-task.md`.

The implementation must follow the project architecture, engineering standards, and documentation.

---

# Step 1 - Read Project Context

Before making any changes, read and understand the following directories in order.

Required:

- docs/00-project-context/
- docs/01-requirements/
- docs/02-design/
- docs/03-api/
- docs/04-database/
- docs/05-engineering/
- docs/08-ai/

Focus especially on:

- current-task.md
- project-status.md
- decision-log.md

Do not skip this step.

---

# Step 2 - Understand the Current Task

Read:

docs/08-ai/current-task.md

Summarize the task.

Answer the following:

- What business problem is being solved?
- What functionality will be added?
- Which existing modules are affected?
- Which APIs are affected?
- Which database tables are affected?
- Which existing components can be reused?

If the task is unclear,
stop and ask for clarification.

Do not assume requirements.

---

# Step 3 - Review Existing Code

Before implementing anything:

Locate all related source code.

Review:

- Entity
- Repository
- Service
- Controller
- DTO
- Mapper
- Configuration
- Exception
- Validation
- Security

Reuse existing implementations whenever possible.

Avoid duplicate code.

---

# Step 4 - Produce an Implementation Plan

Before writing code, provide a technical implementation plan.

Include:

## Scope

- Files to create
- Files to modify

## Backend Layers

- Entity
- Repository
- Service
- Controller
- DTO
- Mapper

## Database

- Schema changes
- Flyway migration
- Indexes
- Constraints

## API

- Endpoints
- Request
- Response
- Status Codes

## Validation

- Required validations
- Business validations

## Security

- Authentication impact
- Authorization impact

## Testing

- Unit Tests
- Integration Tests

## Risks

List possible risks or breaking changes.

Wait for reviewer approval.

Do not write code.

---

# Step 5 - Implement

After reviewer approval:

Implement ONLY the approved scope.

Requirements:

- Follow SOLID principles.
- Follow Clean Architecture.
- Constructor Injection only.
- Never use field injection.
- Never expose Entity directly.
- Use DTOs.
- Use MapStruct.
- Use Bean Validation.
- Follow package responsibilities.
- Follow naming conventions.
- Keep methods small and focused.
- Reuse existing code whenever possible.

Do not implement unrelated features.

---

# Step 6 - Explain Implementation

For every new class explain:

- Why this class exists.
- Its responsibility.
- Dependencies.
- How it interacts with other classes.

Explain every important Spring annotation used.

Explain any design decisions.

---

# Step 7 - Self Review

Review your own implementation.

Check:

- Clean Architecture
- SOLID
- REST API design
- Validation
- Exception handling
- Logging
- Security
- Readability
- Maintainability
- Performance

List any known limitations.

---

# Step 8 - Update Documentation

If implementation changes documentation:

Update only the necessary files.

Possible updates include:

- docs/03-api/
- docs/04-database/
- docs/02-design/
- docs/00-project-context/decision-log.md
- docs/08-ai/project-status.md

Do not modify unrelated documents.

---

# Step 9 - Update AI Workspace

After implementation:

Update:

- current-task.md
- next-task.md
- project-status.md
- session-summary.md

Reflect only completed work.

Do not invent progress.

---

# Step 10 - Prepare Commit Summary

Generate:

## Summary

- What was implemented

## Modified Files

- List all modified files

## Technical Decisions

- Important architectural decisions

## Remaining Work

- Remaining tasks

## Suggested Commit Message

Follow Conventional Commits.

Example:

feat(auth): implement JWT authentication

---

# Global Rules

Always:

- Think before coding.
- Minimize changes.
- Preserve architecture.
- Prefer readability over cleverness.
- Explain non-obvious decisions.
- Never remove existing functionality without justification.
- Never modify unrelated files.
- Stop if requirements are ambiguous.
- Wait for reviewer approval before major architectural changes.