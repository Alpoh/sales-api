# Database

## Engine

H2, embedded and in-memory (`com.h2database:h2`, `runtimeOnly`) — no PostgreSQL, no external database server, no Docker.
The same in-memory instance backs both `bootRun` and `./gradlew test`.

This is a deliberate choice matching the evaluation criteria for this technical test ("Inicializar con los datos del
ejemplo al arrancar la aplicación - H2"): the app must be runnable and queryable with zero external setup, pre-seeded
with the example dataset on every startup.

## Configuration

Datasource connection is configured directly in `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:sales-api;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true

spring.jpa.hibernate.ddl-auto=validate
```

`DB_CLOSE_DELAY=-1` keeps the in-memory database alive for the life of the JVM (it would otherwise be dropped as soon as
the last connection closes). The H2 console is exposed at `/h2-console` for inspecting the seeded data during
development — connect with JDBC URL `jdbc:h2:mem:sales-api`, user `sa`, empty password (an IDE's own database tool needs
the same JDBC URL configured to see this data; a different in-memory database name is a separate, empty H2 instance).

`ddl-auto=validate` means Hibernate never creates or drops schema itself — Flyway is the only thing allowed to
create/alter tables. Leaving `ddl-auto` unset would let Spring Boot's implicit `create-drop`
default for an embedded database auto-create an empty, unindexed, unseeded schema straight from the JPA entity
annotations, which satisfies neither the seeding requirement nor gives control over indexes.

## Migrations

Flyway migrations live under `src/main/resources/db/migration` and run automatically on every application startup,
re-seeding the fresh in-memory database each time:

- `V1__create_prices_table.sql` — creates the `prices` table plus a composite index on
  `(brand_id, product_id, start_date, end_date, priority)`, so the applicable-price lookup is a single indexed query
  rather than a full scan.
- `V2__seed_prices_data.sql` — inserts the example dataset from the test statement (brand 1, product 35455, 4
  overlapping price-list rows), satisfying "Inicializar con los datos del ejemplo al arrancar la aplicación - H2".

**Spring Boot 4 note:** schema migrations require the `spring-boot-starter-flyway` starter, not the bare
`org.flywaydb:flyway-core` library — Boot 4 split Flyway's Spring integration into its own module
(`spring-boot-flyway`), which only the starter pulls in. The bare library alone puts Flyway on the classpath with no
Spring wiring at all: no `Flyway` bean, migrations silently never run, no error.
