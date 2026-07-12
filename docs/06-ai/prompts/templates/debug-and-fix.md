# Debug and Fix Workflow

## Objective

The latest implementation causes the application to fail.

Your task is to identify the root cause, propose a fix, verify the fix, and summarize the result.

Do not blindly modify code.

---

# Step 1 - Read Context

Read:

- docs/08-ai/current-task.md
- docs/08-ai/project-status.md
- docs/00-project-context/decision-log.md

Understand what feature was just implemented.

---

# Step 2 - Reproduce the Problem

Attempt to reproduce the issue.

Check:

- Project builds successfully.
- Spring Boot starts successfully.
- Database connection.
- Flyway migration.
- Bean initialization.
- Dependency Injection.
- Security configuration.
- REST endpoints.

If execution is not possible, inspect the source code and startup logs.

---

# Step 3 - Analyze the Failure

Identify the root cause.

For each issue provide:

- Error message
- Stack trace location
- Root cause
- Why it happened
- Impact

Do not guess.

If multiple causes exist, rank them by likelihood.

---

# Step 4 - Create a Fix Plan

Before changing code explain:

- Which files will change
- Why they need to change
- Any architectural impact
- Any risk of regression

Wait for reviewer approval if the fix changes architecture.

---

# Step 5 - Apply the Fix

Implement the minimal change required.

Requirements:

- Do not modify unrelated code.
- Preserve architecture.
- Reuse existing components.
- Avoid introducing new technical debt.
- Explain every non-obvious change.

---

# Step 6 - Verify

After applying the fix verify:

- Project compiles successfully.
- Spring Boot starts successfully.
- No Bean creation errors.
- No dependency injection errors.
- No Flyway errors.
- No Hibernate errors.
- No Security initialization errors.
- Swagger is accessible.
- Existing APIs continue to work.

If any verification fails, continue debugging.

---

# Step 7 - Regression Check

Ensure the fix does not break:

- Authentication
- JWT
- User module
- Existing APIs
- Database schema

List any remaining risks.

---

# Step 8 - Final Report

Provide:

## Root Cause

## Files Modified

## Changes Made

## Verification Results

## Remaining Risks

## Suggested Follow-up Improvements

Do not update documentation automatically unless requested.