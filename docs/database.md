# Database

## Engine

PostgreSQL is the only supported database — there is no embedded/in-memory (H2) fallback.

## Local development: Docker Compose

[`compose.yaml`](../compose.yaml) at the repo root defines a single `postgres:17` service:

```yaml
services:
  postgres:
    image: 'postgres:17'
    environment:
      - 'POSTGRES_DB=sales-api'
      - 'POSTGRES_PASSWORD=secret'
      - 'POSTGRES_USER=sales-api'
    ports:
      - '5432'
    volumes:
      - 'postgres-data:/var/lib/postgresql/data'
```

Data is persisted in the named volume `postgres-data`, so it survives container restarts/recreation
(only removed with `docker compose down -v`).

The `spring-boot-docker-compose` dependency (`developmentOnly` in `build.gradle`) auto-starts this
service when running `./gradlew bootRun` and configures the datasource to point at it — no manual
`docker compose up` or connection properties required. This only applies to `bootRun`; it does **not**
apply to the `test` task (see [Testing](testing.md)).

There is no datasource/profile configuration in `application.properties` — connection details are
supplied automatically by Docker Compose support (locally) or Testcontainers (in tests).

## Migrations

`flyway-core` and `flyway-database-postgresql` are on the classpath for schema migrations. Migrations
are expected under `src/main/resources/db/migration` (this directory does not exist yet — the project
has no schema to migrate).
