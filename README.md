# sales-api

Sales API — a Spring Boot 4 application built with Gradle. Given a chain (brand) id, a product id, and an application
date, `GET /api/v1/prices` returns the single applicable price rate for that combination (highest-priority match, when
several rate rows overlap the same window). Built with hexagonal architecture;
see [docs/architecture.md](docs/architecture.md) for the layer breakdown.

## Stack

- Java 26
- Spring Boot 4.1.1
- Spring Web (MVC), Spring Data JPA, Bean Validation
- H2 (embedded, in-memory), Flyway migrations
- Lombok, MapStruct

## Requirements

- Java 26 (via the Gradle toolchain — no need to have it pre-installed if Gradle can provision it)

No Docker, database server, or other external service is required: H2 runs embedded, in-memory, inside the application
process itself, and is seeded on every startup by the Flyway migrations under
`src/main/resources/db/migration`.

## Running locally

```bash
./gradlew bootRun
```

The app starts on `http://localhost:8080`, seeded with the example dataset. The H2 console is available at
`http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:sales-api`, user `sa`, empty password) for inspecting the
seeded data.

```bash
curl "http://localhost:8080/api/v1/prices?brandId=1&productId=35455&applicationDate=2020-06-14T10:00:00"
```

Swagger UI is at `http://localhost:8080/swagger-ui.html` — see [docs/api.md](docs/api.md) for the full
parameter/response reference.

## Running tests

```bash
./gradlew test
```

Run a single test class or method:

```bash
./gradlew test --tests "co.medina.test.salesapi.SalesApiApplicationTests"
./gradlew test --tests "co.medina.test.salesapi.SalesApiApplicationTests.contextLoads"
```

## Building

```bash
./gradlew build
```

## Documentation

See [docs/](docs/getting-started.md) for architecture, database, testing, and API details.
