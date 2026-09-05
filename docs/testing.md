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

## Database in tests

Persistence is an embedded, in-memory H2 database (see [Database](database.md)) — the same one used by
`bootRun`. No container, no Testcontainers, and no `@ServiceConnection` wiring are needed: a plain
`@SpringBootTest` gets a working, Flyway-seeded datasource for free, straight from
`application.properties`.

```java
@SpringBootTest
class SalesApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
```

Docker is not required to run the test suite.
