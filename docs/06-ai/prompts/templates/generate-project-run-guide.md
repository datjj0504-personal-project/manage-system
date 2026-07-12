# Generate Project Run Guide

## Objective

Create the root README.md.

The README must explain how to run the complete system.

Read:

backend/

frontend/

docker-compose.yml

docs/

Do not assume commands.

Detect automatically.

---

Generate:

# Project Overview

Describe:

- backend
- frontend
- database
- docker

---

# Requirements

Java

Maven

Node

Docker

Docker Compose

Git

---

# Project Structure

Explain every folder.

---

# Start Database

Explain exactly:

docker compose up

or

docker compose up -d

Explain what containers are created.

---

# Start Backend

Commands.

How to verify backend is running.

Swagger URL.

Health endpoint.

---

# Start Frontend

Commands.

Frontend URL.

---

# Verify Everything

Explain:

Database

↓

Backend

↓

Swagger

↓

Frontend

---

# Shutdown

docker compose down

Stop backend

Stop frontend

---

# Common Problems

Database connection

Flyway

Port conflicts

JWT

CORS

Docker

---

The README must allow a new developer to run the entire system without asking questions.