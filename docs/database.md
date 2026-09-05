# Database

## Engine

H2, embedded and in-memory (`com.h2database:h2`, `runtimeOnly`) — no PostgreSQL, no external database
server, no Docker. The same in-memory instance backs both `bootRun` and `./gradlew test`.

This is a deliberate choice matching the evaluation criteria for this technical test ("Inicializar con
los datos del ejemplo al arrancar la aplicación - H2"): the app must be runnable and queryable with
zero external setup, pre-seeded with the example dataset on every startup.

## Configuration

Datasource connection is configured directly in `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:sales-api;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
```

`DB_CLOSE_DELAY=-1` keeps the in-memory database alive for the life of the JVM (it would otherwise be
dropped as soon as the last connection closes). The H2 console is exposed at `/h2-console` for inspecting
the seeded data during development.

## Migrations

`flyway-core` is on the classpath for schema migrations — no vendor-specific Flyway module is needed for
H2, unlike PostgreSQL. Migrations are expected under `src/main/resources/db/migration` (this directory
does not exist yet — the project has no schema to migrate), and run automatically on every application
startup, re-seeding the fresh in-memory database each time.
