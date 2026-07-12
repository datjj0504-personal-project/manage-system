# Design

This folder contains the technical design artifacts that translate project requirements into implementation structure for the Task Management System.

## Documents

- `architecture.md`: High-level layered architecture, responsibilities, and cross-cutting concerns.
- `package.md`: Package boundaries, responsibilities, and dependency direction.
- `class-diagram.md`: Core domain class relationships for v1 scope.
- `sequence-diagram.md`: Main request flows for auth, project, and task behavior.

## How To Use This Folder

- Read `architecture.md` first to understand the overall structure.
- Read `package.md` before creating or moving backend classes.
- Read `class-diagram.md` before defining entities and relationships.
- Read `sequence-diagram.md` before implementing service flow and controller interactions.

## Maintenance Rules

- Keep design documents aligned with `docs/01-requirement` and actual backend implementation.
- Update sequence and class design when behavior or relationships change materially.
- Do not allow design docs to describe architecture that the codebase no longer follows.
