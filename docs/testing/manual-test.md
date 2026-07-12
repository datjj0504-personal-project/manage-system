# Manual Test Scenarios

## Scope

This document is a manual QA guide for the currently implemented system.

Implemented and testable now:

- Authentication
- JWT access and refresh flow
- User profile view and update
- Password change
- Project CRUD
- Frontend protected routes and page refresh behavior

Not yet implemented in code and therefore not testable in this release:

- Task management

## Test Data Assumptions

- The backend is running on `http://localhost:8080`.
- The frontend is running on `http://localhost:5173`.
- PostgreSQL is available through Docker Compose.
- A test user can be created through the registration screen.
- A user can be authenticated with valid email and password credentials.

## Scenario 1: Register New User

| Field | Value |
|---|---|
| Feature Name | Authentication: Register |
| Objective | Verify a new user can create an account successfully. |
| Precondition | Backend, database, and frontend are running. The email does not already exist. |
| Test Steps | Open `/register`; enter a valid full name, email, and password; click Create account. |
| Expected Result | Registration succeeds, the user is redirected to the login page, and the account is stored in the backend. |
| Postcondition | A new user exists and can log in. |

## Scenario 2: Register Validation Errors

| Field | Value |
|---|---|
| Feature Name | Authentication: Register validation |
| Objective | Verify form validation blocks invalid registration data. |
| Precondition | Frontend is running. |
| Test Steps | Open `/register`; submit an empty form; enter invalid email; use a short password; use a full name shorter than 2 characters. |
| Expected Result | Validation messages are shown for each invalid field and no API request is sent until the form is valid. |
| Postcondition | No new account is created. |

## Scenario 3: Register Duplicate Email

| Field | Value |
|---|---|
| Feature Name | Authentication: Register duplicate email |
| Objective | Verify the backend rejects duplicate account creation. |
| Precondition | A user already exists with the chosen email. |
| Test Steps | Open `/register`; enter the existing email with any valid name and password; submit the form. |
| Expected Result | Registration fails with a clear error message from the backend. |
| Postcondition | Existing account remains unchanged. |

## Scenario 4: Login Successfully

| Field | Value |
|---|---|
| Feature Name | Authentication: Login |
| Objective | Verify a valid user can log in and reach the dashboard. |
| Precondition | A registered user exists. |
| Test Steps | Open `/login`; enter valid credentials; click Sign in. |
| Expected Result | JWT tokens are stored, the user is redirected to the projects page, and the dashboard shell is visible. |
| Postcondition | User session is active in the browser. |

## Scenario 5: Login Failure With Invalid Credentials

| Field | Value |
|---|---|
| Feature Name | Authentication: Invalid login |
| Objective | Verify invalid credentials are rejected. |
| Precondition | A registered user exists. |
| Test Steps | Open `/login`; enter an incorrect password or unknown email; submit the form. |
| Expected Result | The request fails with a 401 response and the page shows an error message. |
| Postcondition | No tokens are stored and the user remains on the login page. |

## Scenario 6: Unauthorized Protected Route Access

| Field | Value |
|---|---|
| Feature Name | Authorization: Protected routes |
| Objective | Verify protected pages redirect unauthenticated users. |
| Precondition | No access token is stored in the browser. |
| Test Steps | Open `/projects` directly; open `/profile` directly. |
| Expected Result | The user is redirected to `/login`. |
| Postcondition | Protected pages are not accessible without authentication. |

## Scenario 7: Browser Refresh After Login

| Field | Value |
|---|---|
| Feature Name | JWT and session persistence |
| Objective | Verify authentication survives a browser refresh. |
| Precondition | User is logged in. |
| Test Steps | Open `/projects`; refresh the browser tab. |
| Expected Result | The user stays authenticated, the dashboard reloads, and protected data is fetched again if needed. |
| Postcondition | Session remains active after refresh. |

## Scenario 8: Refresh Token Recovery After Expired Access Token

| Field | Value |
|---|---|
| Feature Name | JWT: refresh token flow |
| Objective | Verify the app can recover when the access token expires. |
| Precondition | User is logged in and a valid refresh token exists. |
| Test Steps | Wait for or simulate an expired access token; open a protected page or trigger a protected API call. |
| Expected Result | The client uses the refresh token to obtain a new access token and the request succeeds without forcing a manual login. |
| Postcondition | User stays signed in with renewed tokens. |

## Scenario 9: Refresh Token Failure

| Field | Value |
|---|---|
| Feature Name | JWT: invalid refresh token |
| Objective | Verify the app handles refresh failures safely. |
| Precondition | User is logged in, but the stored refresh token is invalid or revoked. |
| Test Steps | Trigger a protected request after the access token expires. |
| Expected Result | The request fails, authentication is cleared, and the user is returned to the login page. |
| Postcondition | No stale tokens remain in browser storage. |

## Scenario 10: View My Profile

| Field | Value |
|---|---|
| Feature Name | User Profile: Read |
| Objective | Verify the current user profile is displayed correctly. |
| Precondition | User is logged in. |
| Test Steps | Open `/profile`. |
| Expected Result | The profile data loads from `/api/v1/users/me` and shows email, full name, and avatar URL if present. |
| Postcondition | User can review current profile information. |

## Scenario 11: Update My Profile

| Field | Value |
|---|---|
| Feature Name | User Profile: Update |
| Objective | Verify profile edits are saved. |
| Precondition | User is logged in and the profile page is open. |
| Test Steps | Change the full name; optionally change avatar URL; click Save profile. |
| Expected Result | The backend returns success, the updated values remain visible after reload, and a success message appears. |
| Postcondition | Profile information is updated in the database. |

## Scenario 12: Profile Validation and Invalid Avatar URL

| Field | Value |
|---|---|
| Feature Name | User Profile: Validation |
| Objective | Verify invalid profile input is rejected. |
| Precondition | User is logged in. |
| Test Steps | Enter a full name shorter than 2 characters; enter a malformed avatar URL; submit the profile form. |
| Expected Result | Validation errors are shown and the request is blocked until the data is valid. |
| Postcondition | No invalid profile data is saved. |

## Scenario 13: Change Password Successfully

| Field | Value |
|---|---|
| Feature Name | User Profile: Change password |
| Objective | Verify the user can change their password. |
| Precondition | User is logged in and knows the current password. |
| Test Steps | Open `/profile`; enter the current password and a new valid password; click Update password. |
| Expected Result | The backend returns success and the password is changed. |
| Postcondition | The old password no longer works and the new password is active. |

## Scenario 14: Change Password With Wrong Current Password

| Field | Value |
|---|---|
| Feature Name | User Profile: Password error handling |
| Objective | Verify the backend rejects a wrong current password. |
| Precondition | User is logged in. |
| Test Steps | Enter an incorrect current password and a valid new password; submit the password form. |
| Expected Result | The request fails with an error message and the password does not change. |
| Postcondition | Existing password remains valid. |

## Scenario 15: Create Project Successfully

| Field | Value |
|---|---|
| Feature Name | Project Management: Create |
| Objective | Verify a new project can be created. |
| Precondition | User is logged in. |
| Test Steps | Open `/projects`; fill in a valid project name and optional description; click Create project. |
| Expected Result | The project is created, appears in the project list, and opens in the detail panel. |
| Postcondition | A new project record exists for the authenticated user. |

## Scenario 16: Create Project Validation

| Field | Value |
|---|---|
| Feature Name | Project Management: Create validation |
| Objective | Verify invalid project input is rejected. |
| Precondition | User is logged in. |
| Test Steps | Submit an empty project form; submit a name shorter than 3 characters; submit an overly long description. |
| Expected Result | Validation messages are shown and the API request is blocked until the data is valid. |
| Postcondition | No invalid project is created. |

## Scenario 17: List Projects and Handle Empty State

| Field | Value |
|---|---|
| Feature Name | Project Management: List and empty state |
| Objective | Verify the project list works with zero and multiple records. |
| Precondition | User is logged in. |
| Test Steps | Open `/projects`; inspect the list; if no projects exist, observe the empty state; if projects exist, review the paginated list. |
| Expected Result | The page displays either a friendly empty state or a list of accessible projects with correct pagination metadata. |
| Postcondition | The list view remains usable regardless of record count. |

## Scenario 18: Search, Paginate, and Refresh Projects

| Field | Value |
|---|---|
| Feature Name | Project Management: Search and pagination |
| Objective | Verify search, pagination, and browser refresh behavior. |
| Precondition | User is logged in and there are enough projects to paginate or search. |
| Test Steps | Search by keyword; move to the next page; refresh the browser; use Back and Forward navigation. |
| Expected Result | The visible project list matches the search and pagination state, and the page remains stable after refresh/navigation. |
| Postcondition | User can continue browsing projects without losing functionality. |

## Scenario 19: View Project Detail

| Field | Value |
|---|---|
| Feature Name | Project Management: Read detail |
| Objective | Verify a project detail can be loaded from the list. |
| Precondition | At least one project exists and the user can access it. |
| Test Steps | Select a project from the list or open `/projects/{projectId}` directly. |
| Expected Result | The project detail loads and shows the current name and description. |
| Postcondition | User can inspect a specific project record. |

## Scenario 20: Update Project Successfully

| Field | Value |
|---|---|
| Feature Name | Project Management: Update |
| Objective | Verify project edits are saved. |
| Precondition | A project exists and the user has access to it. |
| Test Steps | Change the project name or description; click Save changes. |
| Expected Result | The backend returns success, the detail view updates, and the list reflects the new values after refresh. |
| Postcondition | The project record is updated in the database. |

## Scenario 21: Delete Project With Confirmation

| Field | Value |
|---|---|
| Feature Name | Project Management: Delete |
| Objective | Verify a project can be deleted after confirmation. |
| Precondition | A project exists and the user has access to it. |
| Test Steps | Open the project detail; click Delete; confirm the browser dialog. |
| Expected Result | The project is deleted, removed from the list, and the UI returns to the projects view. |
| Postcondition | Deleted project is no longer accessible. |

## Scenario 22: Project Unauthorized Access and Invalid ID Handling

| Field | Value |
|---|---|
| Feature Name | Project Management: Authorization and errors |
| Objective | Verify unauthorized or invalid project access is handled safely. |
| Precondition | User is logged in or logged out depending on the subcase. |
| Test Steps | Open a project route with an invalid or inaccessible project ID; repeat while logged out. |
| Expected Result | Logged-out access redirects to login, and invalid or inaccessible records show a clear error or not-found behavior. |
| Postcondition | The app does not expose unauthorized project data. |

## Scenario 23: Backend Health Check

| Field | Value |
|---|---|
| Feature Name | Health check |
| Objective | Verify the backend is alive before testing the UI. |
| Precondition | Backend is running. |
| Test Steps | Open `http://localhost:8080/api/v1/health`. |
| Expected Result | The endpoint returns a success response with the message `Application is running`. |
| Postcondition | QA can proceed with the remaining scenarios. |

## Scenario 24: Swagger UI Availability

| Field | Value |
|---|---|
| Feature Name | API documentation |
| Objective | Verify the API documentation is reachable. |
| Precondition | Backend is running. |
| Test Steps | Open `http://localhost:8080/swagger-ui.html`. |
| Expected Result | Swagger UI loads and shows the implemented endpoints. |
| Postcondition | QA can inspect API contracts during testing. |

## Scenario 25: Logout

| Field | Value |
|---|---|
| Feature Name | Session management |
| Objective | Verify the user can end the session from the frontend. |
| Precondition | User is logged in. |
| Test Steps | Click Logout in the dashboard shell. |
| Expected Result | Tokens are cleared from browser storage and the user is redirected to `/login`. |
| Postcondition | Protected pages require login again. |

## Scenario 26: Refresh Browser After Logout

| Field | Value |
|---|---|
| Feature Name | Session cleanup |
| Objective | Verify logout persists after a refresh. |
| Precondition | User has logged out. |
| Test Steps | Refresh the browser on a protected route or on the login page after logout. |
| Expected Result | The user remains logged out and cannot access protected data without signing in again. |
| Postcondition | Session is fully cleared. |

## Scenario 27: Large Input Boundaries

| Field | Value |
|---|---|
| Feature Name | Validation and boundary cases |
| Objective | Verify the UI and backend handle maximum-length inputs safely. |
| Precondition | User is logged in. |
| Test Steps | Enter very long but valid values close to the documented limits for full name, project name, and description; submit forms. |
| Expected Result | Valid boundary values are accepted, overly long values are rejected, and no UI breakage occurs. |
| Postcondition | No data corruption or layout issues are introduced. |

## Scenario 28: Expired or Invalid Token Recovery

| Field | Value |
|---|---|
| Feature Name | JWT error handling |
| Objective | Verify the app handles expired or invalid tokens gracefully. |
| Precondition | A token is expired, tampered with, or removed while the refresh token is invalid. |
| Test Steps | Trigger any protected request such as loading profile or projects. |
| Expected Result | The app rejects access, clears invalid session data if needed, and returns the user to login. |
| Postcondition | No unauthorized data remains accessible in the browser session. |

## Notes for QA

- The manual tests above are intended for both browser-based frontend verification and direct backend/API verification when needed.
- Project task scenarios are intentionally excluded because task CRUD is not yet implemented in source code.
- If backend or frontend behavior changes, update this document together with the API docs and implementation status.