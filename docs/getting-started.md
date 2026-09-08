# Getting Started

## Prerequisites

- Java 26 (via the Gradle toolchain; Gradle can provision it automatically if necessary)

No Docker or external database is required. The application uses an embedded in-memory H2 database and Flyway
migrations to seed the example data on startup. See [database.md](database.md) for the persistence setup.

## Local run

```bash
./gradlew bootRun
```

The application starts on `http://localhost:8080` and exposes:

- Endpoint: `http://localhost:8080/api/v1/prices`
- H2 console: `http://localhost:8080/h2-console`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

Example request:

```bash
curl "http://localhost:8080/api/v1/prices?brandId=1&productId=35455&applicationDate=2020-06-14T10:00:00"
```

The embedded H2 database uses these credentials:

- JDBC URL: `jdbc:h2:mem:sales-api`
- User: `sa`
- Password: empty

See [api.md](api.md) for the full request/response contract.

## Running the tests

```bash
./gradlew test
```

This executes the JUnit 5 suite together with the Cucumber integration scenarios against the same embedded H2 instance.
See [testing.md](testing.md) for a complete overview of the test strategy.

## Building the project

```bash
./gradlew build
./gradlew jacocoTestReport
```

The build compiles the application, runs the tests, and generates the JaCoCo reports under `build/reports/jacoco/test`.
