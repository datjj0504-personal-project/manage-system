# Generate Frontend Run Guide

## Objective

Create or update the frontend README.

Target file:

frontend/README.md

---

Read the frontend project first.

Detect automatically:

- package manager
- framework
- node version
- environment variables
- available scripts

Do not assume.

---

Generate a complete step-by-step guide.

Include:

# Prerequisites

- Node.js version
- npm / pnpm / yarn
- Git

# Installation

Clone repository

Install dependencies

Example:

npm install

or

pnpm install

---

# Environment Variables

Explain every variable.

Provide an example .env file.

---

# Start Development Server

Explain exactly how to run.

Example:

npm run dev

Explain:

- default port
- URL
- hot reload

---

# Production Build

Explain:

npm run build

npm run preview

---

# Troubleshooting

Common issues

Missing package

Wrong Node version

Backend unavailable

CORS

Port already in use

---

The README should be understandable by someone who has never used this project.