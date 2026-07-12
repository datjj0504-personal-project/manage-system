# Generate Automation Test Runner

## Objective

Create a complete automation test execution environment for the backend project.

The automation framework is located under:

backend/src/test/java/dev/datnt/taskmanagement/

Your goal is to allow any developer to execute the complete automation suite with a single command.

---

# Step 1 - Read Project

Inspect the backend project.

Detect automatically:

- Maven version
- Java version
- Spring Boot version
- JUnit version
- Test framework
- Package structure
- Existing test classes
- Existing test suites
- Existing profiles

Do not assume.

---

# Step 2 - Discover Automation Tests

Search under:

backend/src/test/java/dev/datnt/taskmanagement/

Identify:

- Unit Tests
- Integration Tests
- API Tests
- Automation Tests
- Test Suites

Produce a summary.

---

# Step 3 - Generate Automation Runner

Create a new directory:

backend/automation/

Inside it generate:

automation/

├── run-all.bat
├── run-unit.bat
├── run-integration.bat
├── run-api.bat
├── clean-and-run.bat
├── generate-report.bat
└── README.md

If some categories do not exist, skip them gracefully.

---

# Step 4 - Batch Scripts

Each script must:

Display a clear title.

Example:

=====================================
Task Management Automation Runner
=====================================

Print:

Current Java Version

Current Maven Version

Current Spring Profile

Working Directory

Then execute the corresponding Maven command.

Examples:

mvn clean test

mvn test

mvn verify

mvn failsafe:integration-test

Automatically stop when an error occurs.

Return a proper exit code.

Display:

SUCCESS

or

FAILED

at the end.

---

# Step 5 - Test Report

Configure the runner to generate reports if available.

Prefer:

Surefire Report

Failsafe Report

JUnit XML

HTML Report

If reporting plugins are missing, explain what should be added.

Do not modify pom.xml unless necessary.

---

# Step 6 - README

Generate:

backend/automation/README.md

Include:

# Purpose

# Folder Structure

# Prerequisites

Java Version

Maven Version

Backend Running Requirements

Database Requirements

Environment Variables

# Available Scripts

Explain every script.

Example:

run-all.bat

Runs the complete automation suite.

run-unit.bat

Runs only unit tests.

run-integration.bat

Runs only integration tests.

clean-and-run.bat

Cleans target directory and executes all tests.

generate-report.bat

Generates HTML reports.

---

# How To Run

Example:

cd backend

automation\run-all.bat

or

automation\run-unit.bat

---

# Expected Result

Explain:

PASS

FAIL

Skipped Tests

Generated Reports

Exit Codes

---

# Troubleshooting

Common issues.

Missing JAVA_HOME

Missing Maven

Database unavailable

Spring profile mismatch

Port already in use

Test failures

Permission denied

---

# Step 7 - Verification

Verify:

Every generated script works.

Scripts execute from:

backend/

without requiring manual modification.

---

# Step 8 - Deliverables

Provide:

Generated files

Purpose of each file

Detected test categories

Suggested future improvements

Do not modify application source code.