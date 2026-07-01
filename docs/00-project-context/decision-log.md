# Architecture Decision Log

This document records architectural decisions.

Each decision is immutable.

If a decision changes, add a new record instead of modifying history.

---

## ADR-0001

Title

Use Monorepo

Status

Accepted

Reason

Backend, frontend, documentation, Docker, and CI remain synchronized.

---

## ADR-0002

Title

Use Layered Architecture

Status

Accepted

Reason

Simple, widely adopted, easy for learning and maintenance.

---

## ADR-0003

Title

Use Spring Boot

Status

Accepted

Reason

Industry standard Java backend framework.

---

## ADR-0004

Title

Use JWT Authentication

Status

Accepted

Reason

Stateless authentication suitable for REST APIs.

---

## ADR-0005

Title

Use MapStruct

Status

Accepted

Reason

Compile-time mapping with better performance than reflection-based mappers.

---

## ADR-0006

Title

Use Flyway

Status

Accepted

Reason

Version-controlled database migrations.

---

## ADR-0007

Title

Use Constructor Injection

Status

Accepted

Reason

Improves immutability and testability.

---

## ADR-0008

Title

Use DTO Pattern

Status

Accepted

Reason

Prevent exposing persistence models to external clients.

---

## ADR-0009

Title

Use Soft Delete

Status

Proposed

Reason

May be enabled for business entities requiring auditability.

---

## ADR-0010

Title

Use UUID or BIGINT

Status

Pending

Reason

Decision postponed until scalability requirements are defined.