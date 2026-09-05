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

There is no embedded/in-memory database, so any `@SpringBootTest` that touches the datasource needs
its own Postgres instance via Testcontainers. The `spring-boot-docker-compose` support used by
`bootRun` does **not** apply here — `developmentOnly` dependencies are not on the `test` task's
classpath.

The pattern used in `SalesApiApplicationTests`:

```java
@Testcontainers
@SpringBootTest
class SalesApiApplicationTests {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @Test
    void contextLoads() {
    }

}
```

- `@ServiceConnection` auto-configures the Spring datasource from the running container — no manual
  connection properties needed.
- `@Container` (from `org.testcontainers.junit.jupiter`) manages the container's lifecycle.
- **Testcontainers 2.x gotcha**: `org.testcontainers.postgresql.PostgreSQLContainer` is no longer
  generic — declare it as `PostgreSQLContainer`, not `PostgreSQLContainer<?>`.

Docker must be running locally for tests to pass.
