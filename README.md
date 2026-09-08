# Sales API

Sales API is a Spring Boot 4 application that exposes a single REST endpoint for price-rate resolution. Given a brand id,
a product id, and an application date, `GET /api/v1/prices` returns the unique applicable price, using the highest-priority
rate when multiple price lists overlap.

The project is structured in hexagonal layers, keeps the domain independent from the framework, and seeds the example data
automatically through an H2 + Flyway migration on startup.

## Project goals

- Return exactly one applicable price for a brand/product/date combination
- Enforce the business rule of highest-priority match when ranges overlap
- Keep the domain model and application logic framework-agnostic
- Seed the example dataset automatically from Flyway on every startup
- Provide clean REST API contracts with validation and ProblemDetail responses
- Cover the business scenarios with automated tests

## Technology stack

- Java 26
- Spring Boot 4.1.1
- Spring Web MVC, Spring Data JPA, Bean Validation
- H2 embedded in-memory database
- Flyway migrations
- Lombok and MapStruct
- springdoc-openapi for Swagger/OpenAPI docs
- JaCoCo for coverage reports
- JUnit 5 + Cucumber for automated testing

## Architecture summary

The project follows a hexagonal architecture:

- `domain` contains the core business model and business rules
- `application` holds use cases and ports
- `infrastructure.persistence` adapts the repository to JPA and the database
- `infrastructure.web` exposes the REST API and maps domain results to response DTOs

See [docs/architecture.md](docs/architecture.md) for the full layer breakdown.

## Prerequisites

- Java 26 via the Gradle toolchain

No Docker or external database is required. The application runs against an embedded in-memory H2 database that is seeded
by Flyway every time the app starts.

## Running locally

```bash
./gradlew bootRun
```

Then open:

- API: `http://localhost:8080/api/v1/prices?brandId=1&productId=35455&applicationDate=2020-06-14T10:00:00`
- H2 console: `http://localhost:8080/h2-console`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

The H2 console uses:

- JDBC URL: `jdbc:h2:mem:sales-api`
- User: `sa`
- Password: empty

## API usage

Example request:

```bash
curl "http://localhost:8080/api/v1/prices?brandId=1&productId=35455&applicationDate=2020-06-14T10:00:00"
```

Example response:

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceListId": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.5,
  "currency": "EUR"
}
```

Refer to [docs/api.md](docs/api.md) for a complete contract and error handling details.

## Testing

Run the full suite:

```bash
./gradlew test
```

Run a single test class or method:

```bash
./gradlew test --tests "co.medina.test.salesapi.application.service.ObtainApplicablePriceServiceTest"
./gradlew test --tests "co.medina.test.salesapi.infrastructure.web.PricesControllerValidationTest"
```

The repository includes:

- unit tests for the application service
- controller validation tests for malformed or missing parameters
- BDD integration tests covering the official scenarios from the test statement

See [docs/testing.md](docs/testing.md) for details on the testing strategy and coverage reporting.

## Building and quality gate

```bash
./gradlew build
./gradlew jacocoTestReport
```

The build generates the HTML and XML JaCoCo reports under `build/reports/jacoco/test`.

## Documentation

- [docs/getting-started.md](docs/getting-started.md) — setup and local run guide
- [docs/architecture.md](docs/architecture.md) — architecture and package layout
- [docs/database.md](docs/database.md) — Flyway and H2 database model
- [docs/api.md](docs/api.md) — endpoint contract and examples
- [docs/testing.md](docs/testing.md) — testing strategy and coverage details

## Repository hygiene and version control

This repository follows a clean and review-friendly workflow:

- change scope stays focused on a single responsibility per task
- meaningful commit messages describe the business or technical outcome
- branch names and PRs should map clearly to the feature or fix they introduce
- documentation is kept near the code it describes to make onboarding easier

The goal is to keep the history understandable and the final delivery easy to review by a technical team.
