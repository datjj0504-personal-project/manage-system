# Business Domain

## Project Overview

Task Management System is a collaborative work management platform.

Users can create projects, organize tasks, assign responsibilities, monitor progress, and collaborate through comments and attachments.

This project is designed for learning enterprise Java backend development while following production-level engineering practices.

---

# Core Business Objects

## User

Represents a registered account.

Responsibilities

- Authenticate
- Manage owned projects
- Join projects
- Create tasks
- Update assigned tasks
- Comment on tasks

---

## Project

Represents a workspace.

Contains

- Members
- Tasks
- Project settings

A project always belongs to one owner.

---

## Member

Represents the relationship between User and Project.

Roles

- Owner
- Admin
- Member

---

## Task

Represents a unit of work.

Attributes

- Title
- Description
- Status
- Priority
- Due Date
- Assignee

Each task belongs to exactly one project.

---

## Comment

Represents communication on a task.

Only project members may create comments.

---

## Attachment

Represents uploaded files associated with a task.

Supported examples

- Images
- Documents
- PDFs

---

# Business Rules

Only authenticated users may access resources.

Users may only access projects they belong to.

Only project owners may delete projects.

Completed tasks cannot be modified unless reopened.

Deleted resources are soft deleted whenever possible.

Every operation must be auditable.

---

# Future Extensions

Notification

Email

Activity Timeline

Kanban Board

Calendar

Labels

Subtasks

Workspace

Organization

OAuth Login