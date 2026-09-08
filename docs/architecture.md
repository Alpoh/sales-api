# Architecture

## Current state

Sales API implements a single REST endpoint: given a chain (brand) id, a product id, and an application date, it returns
the single highest-priority price rate applicable at that date, seeded from an embedded H2 database on every startup.
The codebase follows hexagonal architecture (ports & adapters).

## Stack

- **Language/runtime**: Java 26 (Gradle toolchain), Spring Boot 4.1.1
- **Web**: Spring Web (MVC)
- **Persistence**: Spring Data JPA on an embedded, in-memory H2 database, with Flyway for migrations
  (see [Database](database.md))
- **Validation**: Bean Validation (`spring-boot-starter-validation`)
- **Boilerplate reduction**: Lombok, MapStruct (for entity ↔ domain and domain ↔ DTO mapping)
- **API docs**: springdoc-openapi (see [API](api.md))
- **Observability**: Spring Boot Actuator
- **Local dev**: Spring Boot DevTools — no Docker required
- **Testing**: unit tests (JUnit 5 + Mockito) and BDD integration tests (Cucumber) against the same embedded H2
  database, no containers needed (see [Testing](testing.md))

## Package layout

Root package: `co.medina.test.salesapi`, organized in hexagonal layers:

- `domain` — framework-agnostic core, no Spring/JPA annotations:
    - `model`: `Money` (amount + currency value object), `Price` (the rich domain model for a rate)
    - `exception`: `ApplicablePriceNotFoundException`
- `application` — use cases and ports, depends only on `domain`:
    - `port.in`: `ObtainApplicablePriceQuery` (input DTO), `ObtainApplicablePriceUseCase` (input port)
    - `port.out`: `PriceRepository` (output port — `findHighestPriorityApplicablePrice` expresses the
      "highest priority wins on overlap" business contract through its name, not a comment)
    - `service`: `ObtainApplicablePriceService` (implements the use case via the output port)
- `infrastructure.persistence` — outbound adapter, depends on `application`/`domain`:
    - `PriceEntity` (JPA entity, never exposed outside this package)
    - `PriceJpaRepository` (Spring Data JPA, one derived-query method pushing the "in range, highest priority" filtering
      down into a single indexed SQL query — no in-memory filtering of a loaded list)
    - `PriceEntityMapper` (MapStruct, `PriceEntity` → `Price`)
    - `PriceRepositoryAdapter` (implements the `PriceRepository` port)
- `infrastructure.web` — inbound adapter, depends on `application`/`domain`:
    - `ApplicablePriceResponse` (response DTO, never the domain model or the JPA entity)
    - `PriceWebMapper` (MapStruct, `Price` → `ApplicablePriceResponse`)
    - `PricesController` (`GET /api/v1/prices`, validated `@RequestParam`s)
    - `RestExceptionHandler` (`@RestControllerAdvice`, maps `ApplicablePriceNotFoundException` → `404`)

Adapters depend on the domain, never the reverse; controllers and JPA entities are never reused as each other or as the
domain model — every boundary crossing goes through an explicit MapStruct mapper.
