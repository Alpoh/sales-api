# Getting Started

## Prerequisites

- Java 26 (via the Gradle toolchain — no need to have it pre-installed if Gradle can provision it)
- Docker, running locally — required by both `bootRun` (Docker Compose support) and `./gradlew test`
  (Testcontainers)

## Running locally

```bash
./gradlew bootRun
```

This auto-starts the Postgres container defined in [`compose.yaml`](../compose.yaml) and wires the
datasource to it automatically — no manual `docker compose up` or connection configuration needed.
The app starts on `http://localhost:8080`.

## Running tests

```bash
./gradlew test
```

See [Testing](testing.md) for details on how tests get their own Postgres instance.

## Building

```bash
./gradlew build
```

Compiles, runs tests, and packages the application.
