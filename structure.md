# Project Structure

This document summarizes the current folder and file layout of the repository and explains the purpose of each major area.

## Root

```text
manage-system/
„¥„Ÿ .git/                              # Git metadata
„¥„Ÿ .vscode/                           # Workspace-level editor settings
„¥„Ÿ backend/                           # Spring Boot backend service
„¥„Ÿ docker/                            # Container orchestration files
„¥„Ÿ docs/                              # Project documentation by domain
„¥„Ÿ knowledge/                         # Operational notes and quick references
„¥„Ÿ .gitignore                         # Root ignore rules
„¥„Ÿ info.txt                           # Additional project notes
„¥„Ÿ README.md                          # Repository overview
„¤„Ÿ structure.md                       # This structure documentation
```

## Backend

```text
backend/
„¥„Ÿ .mvn/                              # Maven wrapper support files
„¥„Ÿ src/
„   „¤„Ÿ main/
„      „¥„Ÿ java/
„      „   „¤„Ÿ dev/datnt/taskmanagement/
„      „      „¥„Ÿ Application.java       # Spring Boot entry point
„      „      „¥„Ÿ common/
„      „      „   „¤„Ÿ ApiResponse.java    # Standard API response envelope
„      „      „¥„Ÿ config/
„      „      „   „¤„Ÿ SecurityConfig.java # Security filter chain configuration
„      „      „¥„Ÿ controller/
„      „      „   „¤„Ÿ HealthController.java # Health check endpoint
„      „      „¥„Ÿ exception/
„      „      „   „¤„Ÿ GlobalExceptionHandler.java # Centralized exception handling
„      „      „¥„Ÿ constant/              # Constants package scaffold
„      „      „¥„Ÿ dto/                   # DTO package scaffold
„      „      „¥„Ÿ entity/                # Entity package scaffold
„      „      „¥„Ÿ mapper/                # Mapper package scaffold
„      „      „¥„Ÿ repository/            # Repository package scaffold
„      „      „¥„Ÿ security/              # Security package scaffold
„      „      „¥„Ÿ service/               # Service package scaffold
„      „      „¥„Ÿ util/                  # Utility package scaffold
„      „      „¤„Ÿ validation/            # Validation package scaffold
„      „¤„Ÿ resources/
„         „¥„Ÿ application.yml           # Base Spring configuration
„         „¥„Ÿ application-local.yml     # Local profile configuration
„         „¥„Ÿ application-dev.yml       # Dev profile configuration
„         „¥„Ÿ application-prod.yml      # Production profile configuration
„         „¤„Ÿ db/
„            „¤„Ÿ migration/
„               „¤„Ÿ .gitkeep            # Placeholder for Flyway scripts
„¥„Ÿ .env.example                       # Environment variable template
„¥„Ÿ mvnw                               # Maven wrapper (Unix)
„¥„Ÿ mvnw.cmd                           # Maven wrapper (Windows)
„¥„Ÿ pom.xml                            # Maven project definition
„¤„Ÿ README.md                          # Backend setup and run guide
```

## Docker

```text
docker/
„¥„Ÿ compose.yaml                       # Container composition configuration
„¤„Ÿ README.md                          # Docker usage notes
```

## Documentation

```text
docs/
„¥„Ÿ 00-project-context/                # Domain context and engineering principles
„   „¥„Ÿ business-domain.md              # Business objects and rules
„   „¥„Ÿ coding-principles.md            # Coding and architecture principles
„   „¥„Ÿ decision-log.md                 # Architecture Decision Records
„   „¥„Ÿ glossary.md                     # Shared terminology
„   „¤„Ÿ tech-stack.md                   # Technology stack reference
„¥„Ÿ 01-requirement/                    # Functional requirements and plan
„   „¥„Ÿ feature-list.md                 # Product feature inventory
„   „¥„Ÿ overview.md                     # Project objective and scope overview
„   „¤„Ÿ roadmap.md                      # Sprint roadmap and milestones
„¥„Ÿ 02-design/                         # System design artifacts
„   „¥„Ÿ architecture.md                 # Layered architecture design
„   „¥„Ÿ class-diagram.md                # Domain class relationships
„   „¥„Ÿ package.md                      # Package responsibilities and boundaries
„   „¤„Ÿ sequence-diagram.md             # Key API flow sequences
„¥„Ÿ 03-api/                            # API contracts by module
„   „¥„Ÿ auth.md                         # Authentication APIs
„   „¥„Ÿ project.md                      # Project APIs
„   „¥„Ÿ task.md                         # Task APIs
„   „¤„Ÿ user.md                         # User profile APIs
„¥„Ÿ 04-database/                       # Database design and migration guidance
„   „¥„Ÿ erd.png                         # ERD image placeholder
„   „¥„Ÿ migration.md                    # Flyway migration conventions
„   „¤„Ÿ tables.md                       # v1 table definitions
„¥„Ÿ 05-rules/                          # Development standards and rules
„   „¥„Ÿ api-rule.md                     # API design and response rules
„   „¥„Ÿ commit-rule.md                  # Commit message conventions
„   „¥„Ÿ exception-rule.md               # Exception handling rules
„   „¥„Ÿ folder-rule.md                  # Folder and layer boundaries
„   „¥„Ÿ git-rule.md                     # Branch and PR workflow rules
„   „¥„Ÿ java-style.md                   # Java coding style guide
„   „¥„Ÿ logging-rule.md                 # Logging and sensitive data policy
„   „¥„Ÿ prompt-tasks.md                 # Standard task prompt template
„   „¥„Ÿ security-rule.md                # Security baseline rules
„   „¤„Ÿ testing-rule.md                 # Testing strategy and standards
„¥„Ÿ 06-prompts/                        # Role-specific prompt templates
„   „¥„Ÿ backend-agent.md                # Backend coding agent prompt
„   „¥„Ÿ frontend-agent.md               # Frontend coding agent prompt
„   „¥„Ÿ refactor-agent.md               # Refactor-focused agent prompt
„   „¤„Ÿ review-agent.md                 # Code review agent prompt
„¥„Ÿ 07-progress/                       # Sprint progress tracking
„   „¥„Ÿ sprint-1/
„   „   „¤„Ÿ report.txt                   # Sprint 1 report
„   „¥„Ÿ sprint-2/
„   „   „¤„Ÿ report.txt                   # Sprint 2 report
„   „¥„Ÿ sprint-3/
„   „   „¤„Ÿ report.txt                   # Sprint 3 report
„   „¤„Ÿ sprint-4/
„      „¤„Ÿ report.txt                   # Sprint 4 report
„¥„Ÿ 08-ai/                             # AI planning and status tracking docs
„   „¥„Ÿ current-task.md                 # Current implementation focus
„   „¥„Ÿ next-task.md                    # Prioritized upcoming tasks
„   „¥„Ÿ project-status.md               # Current project health snapshot
„   „¥„Ÿ session-summary.md              # Latest AI session summary
„   „¤„Ÿ system-prompt.md                # Base system prompt for AI workflow
„¤„Ÿ 09-sample-prompts/                 # Reusable prompt samples
	„¥„Ÿ prompt-udpate-any-folder-docs.txt # Generic docs update prompt
	„¤„Ÿ prompt-update-docs-08-ai.txt    # Prompt for updating docs/08-ai
```

## Knowledge

```text
knowledge/
„¤„Ÿ application_main_run.txt           # Notes for running the application
```

## Notes

- The backend currently contains foundational classes and package scaffolding.
- Feature modules (authentication, user, project, task business implementation) are represented in docs and package layout, but many implementation files are still pending.
