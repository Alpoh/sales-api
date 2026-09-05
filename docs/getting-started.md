# Getting Started

## Prerequisites

- Java 26 (via the Gradle toolchain — no need to have it pre-installed if Gradle can provision it)

No Docker or external database is required — persistence runs on an embedded, in-memory H2 database
(see [Database](database.md)).

## Running locally

```bash
./gradlew bootRun
```

The app starts on `http://localhost:8080`, backed by an in-memory H2 database that Flyway seeds with the
example dataset on every startup. The H2 console is available at `http://localhost:8080/h2-console`
(JDBC URL `jdbc:h2:mem:sales-api`, user `sa`, empty password).

## Running tests

```bash
./gradlew test
```

See [Testing](testing.md) for details — tests use the same embedded H2 database, no container needed.

## Building

```bash
./gradlew build
```

Compiles, runs tests, and packages the application.
