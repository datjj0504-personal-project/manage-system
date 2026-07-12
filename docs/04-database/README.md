# Database Documentation

This folder contains the database design reference for the Task Management System.

## Structure

- `erd/`: Visual ERD source and exported image.
- `tables/`: Table-level schema and migration documentation.

## Contents

- `erd/erd.drawio`: Editable ERD source.
- `erd/erd.png`: Exported ERD image.
- `tables/user.md`: `User` table design.
- `tables/project.md`: `Project` membership and ownership table design.
- `tables/task.md`: `Task` table design.
- `tables/migration.md`: Migration policy and naming rules.

## How To Use This Folder

- Read the relevant table document before changing entity or migration design.
- Update table docs together with Flyway migration changes.
- Keep ERD and table documents aligned.

## Maintenance Rules

- Keep terminology consistent with `docs/00-project-context/glossary.md`.
- Document new tables before or alongside implementation.
- Reflect authorization and business rules in schema relationships where applicable.
