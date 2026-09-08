# Testing

## Running the suite

```bash
./gradlew test
```

To run a single class or method:

```bash
./gradlew test --tests "co.medina.test.salesapi.application.service.ObtainApplicablePriceServiceTest"
./gradlew test --tests "co.medina.test.salesapi.infrastructure.web.PricesControllerValidationTest"
```

The project uses JUnit 5 with the JUnit Platform and executes Cucumber scenarios through the platform engine.

## Unit tests

`ObtainApplicablePriceServiceTest` verifies the application service behavior in isolation by mocking the repository output
port. This keeps the tests fast and focused on the use case logic without starting a Spring context.

A second validation layer is exercised by `PricesControllerValidationTest`, which checks malformed input and missing
required parameters return HTTP 400 responses.

## BDD integration tests

The request/response scenarios defined in the business statement are implemented as Gherkin features under
`src/test/resources/features`, including the success cases and the 404 pathway when no price applies.

The step definitions live in `src/test/java/co/medina/test/salesapi/bdd` and call the real HTTP endpoint through a
`TestRestTemplate` configured with `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)`.

This is wired by the shared Cucumber bootstrap configuration and executed automatically by the `CucumberTestSuite`
through the JUnit Platform engine.

## Embedded database in tests

The tests run on the same embedded in-memory H2 database used by the application in local development. Flyway seeds the
example data on startup, so no Docker or external service is required for either `bootRun` or the test suite.

## Coverage

The project generates JaCoCo reports automatically after `test`:

- HTML: `build/reports/jacoco/test/html/index.html`
- XML: `build/reports/jacoco/test/jacocoTestReport.xml`

The coverage report is useful as a baseline for ongoing code-quality and regression checks.
