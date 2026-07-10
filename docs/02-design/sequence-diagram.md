# Sequence Diagram (v1)

## Register Flow

1. Client sends register request to Auth Controller.
2. Auth Controller validates request DTO.
3. Auth Service checks email uniqueness.
4. Auth Service hashes password and persists User via Repository.
5. Auth Controller returns success response.

## Login Flow

1. Client sends login request to Auth Controller.
2. Auth Service verifies User credentials.
3. Auth Service issues JWT access token and refresh token.
4. Auth Controller returns token payload.

## Create Project Flow

1. Authenticated User sends create project request.
2. Project Controller validates request DTO.
3. Project Service creates Project with Owner = current User.
4. Project Service persists Project and initial Member record.
5. Project Controller returns created Project response.

## Create Task Flow

1. Authenticated Member sends create task request in a Project.
2. Task Service verifies project membership.
3. Task Service validates Assignee is a Member when provided.
4. Task Service persists Task.
5. Task Controller returns created Task response.
