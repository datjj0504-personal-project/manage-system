# Generate Manual Test Scenarios

## Objective

Generate a complete manual testing document.

Target file:

docs/testing/manual-test.md

---

Read:

docs/03-api/

docs/06-ai/project-status.md

Current backend implementation

Current frontend implementation

---

Generate user testing scenarios.

For every feature provide:

Feature Name

Objective

Precondition

Test Steps

Expected Result

Postcondition

---

Cover every existing feature.

Examples:

Authentication

User Management

Project Management

Task Management

JWT

Authorization

Validation

---

Include:

Positive Cases

Negative Cases

Boundary Cases

Permission Cases

Validation Cases

Session Expiration

Unauthorized Access

Empty Data

Large Data

Refresh Browser

Invalid Token

Expired Token

---

Example

Scenario

Login Successfully

Precondition

User exists

Steps

Open Login Page

Enter username

Enter password

Click Login

Expected

Redirect to Dashboard

JWT stored

User information displayed

---

The document should be usable by a QA Engineer with no knowledge of the project.