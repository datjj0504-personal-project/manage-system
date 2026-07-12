# Class Diagram (v1)

## Core Domain Classes

- User
- Project
- Member
- Task
- Comment (planned)
- Attachment (planned)

## Relationship Summary

- One User can own many Project.
- One Project has many Member.
- One Member links one User to one Project with role.
- One Project has many Task.
- One Task can have one Assignee (User).
- One Task can have many Comment (planned).
- One Task can have many Attachment (planned).

## Service-Level Classes (Target)

- AuthService
- UserService
- ProjectService
- TaskService

## Note

Detailed visual UML can be added after entities are implemented to avoid drift between design and code.
