# Git Rules

## Branch Model

- main: production-ready history
- develop: integration branch for completed features
- feature/*: feature development
- bugfix/*: bug fixes on develop scope
- release/*: release preparation
- hotfix/*: urgent production fix from main

## Pull Request Rules

- Rebase or merge from develop before opening PR.
- Keep PR scope focused and reviewable.
- Include testing notes and impact summary.
- Do not merge if required checks fail.