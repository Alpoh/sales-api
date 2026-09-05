# Architecture

## Current state

Sales API is a Spring Boot 4 application. As of now it contains no domain code — only the
`SalesApiApplication` entry point (`co.medina.test.salesapi`) and a context-load test. The dependency
set below signals the intended shape of the app (a REST API backed by PostgreSQL with DB migrations),
but none of it is wired up yet: there are no entities, repositories, controllers, migrations, or
DTOs/mappers.

## Stack

- **Language/runtime**: Java 26 (Gradle toolchain), Spring Boot 4.1.1
- **Web**: Spring Web (MVC)
- **Persistence**: Spring Data JPA on PostgreSQL, with Flyway for migrations
- **Validation**: Bean Validation (`spring-boot-starter-validation`)
- **Boilerplate reduction**: Lombok, MapStruct (for DTO ↔ entity mapping)
- **API docs**: springdoc-openapi (see [API](api.md))
- **Observability**: Spring Boot Actuator
- **Local dev**: Spring Boot DevTools, Docker Compose support (see [Database](database.md))
- **Testing**: Testcontainers (PostgreSQL module) for integration tests

## Package layout

Root package: `co.medina.test.salesapi`. There is a single class today
(`SalesApiApplication`); no sub-packages exist yet. When adding the first real feature, you are
establishing the architectural conventions (package-by-layer vs. package-by-feature, where
controllers/services/repositories/entities/DTOs live), not following existing ones.

## Expected future layout

Based on the dependencies already declared:

- `src/main/resources/db/migration` — Flyway SQL migrations
- Entities, repositories (Spring Data JPA), controllers (Spring MVC), and MapStruct mappers between
  entities and DTOs
