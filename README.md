# sales-api

Sales API — a Spring Boot 4 application built with Gradle.

## Stack

- Java 26
- Spring Boot 4.1.1
- Spring Web (MVC), Spring Data JPA, Bean Validation
- PostgreSQL (runtime driver + Testcontainers for tests), Flyway migrations
- Lombok, MapStruct
- Spring Boot DevTools, Docker Compose support

## Requirements

- Docker (running locally) — `bootRun` and the test suite both need a Postgres instance:
  `bootRun` auto-starts the `compose.yaml` Postgres container, tests spin up their own via Testcontainers.

## Running locally

```bash
./gradlew bootRun
```

## Running tests

```bash
./gradlew test
```

## Building

```bash
./gradlew build
```

## Documentation

See [docs/](docs/README.md) for architecture, database, testing, and API details.
