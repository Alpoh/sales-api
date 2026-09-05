# Testing

## Running tests

```bash
./gradlew test
```

Run a single test class or method:

```bash
./gradlew test --tests "co.medina.test.salesapi.SalesApiApplicationTests"
./gradlew test --tests "co.medina.test.salesapi.SalesApiApplicationTests.contextLoads"
```

Tests run on JUnit 5 (JUnit Platform).

## Unit tests

`ObtainApplicablePriceServiceTest` (JUnit 5 + Mockito) exercises the application service in isolation,
mocking the `PriceRepository` output port — no Spring context, no database.

## BDD integration tests

The 5 scenarios from the test statement are expressed as Gherkin in
`src/test/resources/features/applicable_price_lookup.feature` (a single `Scenario Outline` with an
`Examples` table), with step definitions in
`src/test/java/co/medina/test/salesapi/bdd/ApplicablePriceLookupSteps.java`. Steps call the real running
endpoint through a `TestRestTemplate` (`@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)`,
configured via `CucumberSpringConfiguration`) and assert on the HTTP response.

They run automatically as part of `./gradlew test`, wired through the JUnit Platform's `CucumberTestSuite`
(`@Suite` + `@IncludeEngines("cucumber")`) — no separate Cucumber CLI/task needed.

**Spring Boot 4 note:** getting `TestRestTemplate` injectable took two extra pieces beyond
`RANDOM_PORT`, both specific to Boot 4's module split: the `@AutoConfigureTestRestTemplate` annotation on
`CucumberSpringConfiguration` (a bean is no longer auto-registered from `RANDOM_PORT` alone), and the
`spring-boot-starter-restclient` dependency (its autoconfiguration needs `RestTemplateBuilder`, which no
other starter in this project pulls in). `TestRestTemplate` itself also moved package, from
`org.springframework.boot.test.web.client` (Boot 3) to `org.springframework.boot.resttestclient` (Boot 4).

## Database in tests

Persistence is an embedded, in-memory H2 database (see [Database](database.md)) — the same one used by
`bootRun`. No container, no Testcontainers, and no `@ServiceConnection` wiring are needed: a plain
`@SpringBootTest` gets a working, Flyway-seeded datasource for free, straight from
`application.properties`.

Docker is not required to run the test suite.
