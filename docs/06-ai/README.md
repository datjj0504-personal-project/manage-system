# 06-ai — AI Collaboration Guide

This folder contains all documents that govern how AI agents collaborate on this project.

It provides context, system prompts, agent role definitions, task state, and reusable prompt templates so that every AI session starts from an accurate and consistent baseline.

---

## Purpose

- Give AI agents the correct project context before generating any code or documentation.
- Track what has been done, what is in progress, and what comes next.
- Standardize how tasks are handed off between human developers and AI agents.
- Maintain a persistent record of each session so work is never duplicated or lost.

---

## Folder Structure

```text
06-ai/
├── README.md                  ← This file
├── system-prompt.md           ← Base instruction set loaded at the start of every session
├── current-task.md            ← The single task currently in execution
├── next-task.md               ← Ordered backlog of upcoming tasks
├── project-status.md          ← Live snapshot of implementation reality vs plan
├── session-summary.md         ← Written at the end of each session; read at the start of the next
└── prompts/
    ├── backend-agent.md       ← Role prompt for the backend engineer agent
    ├── frontend-agent.md      ← Role prompt for the frontend engineer agent
    ├── review-agent.md        ← Role prompt for the code reviewer agent
    ├── refactor-agent.md      ← Role prompt for the refactor agent
    └── templates/
        ├── generate-module.md         ← Template for implementing a new feature module
        ├── update-docs.md             ← Template for documentation-only update tasks
        ├── update-folder-under-docs.md ← Template for updating a specific docs folder
        ├── update-status.md           ← Template for updating project-status.md
        └── review-code.md             ← Template for targeted code review tasks
```

---

## File Reference

### `system-prompt.md`

Loaded at the start of every AI session.

Instructs the agent to read core project documentation before producing any output and to follow all engineering rules without deviation.

### `current-task.md`

Describes the single task currently being executed.

Contains objective, in-scope items, non-goals, and acceptance checklist.

**Update this file** when starting a new task. Only one task should be active at a time.

### `next-task.md`

Ordered list of upcoming tasks grouped by phase.

Used to plan what comes after the current task is complete.

**Update this file** when a task moves from backlog to `current-task.md`, or when new tasks are discovered.

### `project-status.md`

Tracks the gap between what the roadmap expects and what the source code actually contains.

Includes implementation snapshot, requirement coverage, sprint progress reality, risks, and immediate priorities.

**Update this file** at the end of each session or after each milestone is completed.

### `session-summary.md`

Written at the end of each session and read at the start of the next.

Records what was reviewed, what was implemented, what was found, and what the recommended next action is.

**Always update this file** before ending a session so the next session can resume without re-reading the entire codebase.

---

## Agent Prompts

Located in `prompts/`.

Each file defines the role, rules, and output expectations for a specific agent type.

| File | Role |
|---|---|
| `backend-agent.md` | Senior Java Backend Engineer |
| `frontend-agent.md` | Senior Frontend Engineer |
| `review-agent.md` | Code reviewer focused on architecture, security, and correctness |
| `refactor-agent.md` | Refactor-only engineer focused on readability without behavior change |

---

## Prompt Templates

Located in `prompts/templates/`.

Reusable task skeletons. Fill in the placeholders and send the result to the appropriate agent.

| Template | Use when |
|---|---|
| `generate-module.md` | Implementing a new feature module end-to-end |
| `update-docs.md` | Updating documentation without touching source code |
| `update-folder-under-docs.md` | Updating a specific folder under `docs/` |
| `update-status.md` | Updating `project-status.md` after a milestone |
| `review-code.md` | Running a targeted code review on a specific area |

---

## Session Workflow

Follow this order at the start and end of every AI session.

### Starting a session

1. Load `system-prompt.md` as the base instruction.
2. Read `session-summary.md` from the previous session.
3. Read `project-status.md` to understand the current state.
4. Read `current-task.md` to know what to work on.
5. Load the relevant agent prompt from `prompts/`.

### Ending a session

1. Update `project-status.md` with any changes to implementation state.
2. Write a new `session-summary.md` entry describing what was done.
3. Update `current-task.md` if the task is complete or its scope changed.
4. Move completed tasks and promote the next item in `next-task.md` if applicable.

---

## Rules

- Never skip reading `session-summary.md` before starting work.
- Never start implementing code before reading `current-task.md`.
- Never modify business logic and documentation in the same task.
- Keep `project-status.md` honest. Do not mark items done unless source code confirms it.
- Follow all engineering rules defined in `docs/05-engineering/`.
