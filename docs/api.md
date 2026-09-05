# API

API documentation is generated automatically by `springdoc-openapi-starter-webmvc-ui` from controllers
— there is no hand-maintained API reference, and there are no controllers yet.

Once the app is running (`./gradlew bootRun`), it exposes:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Raw OpenAPI spec: `http://localhost:8080/v3/api-docs`

Both are enabled by default. To disable either in a given environment, set:

```properties
springdoc.swagger-ui.enabled=false
springdoc.api-docs.enabled=false
```
