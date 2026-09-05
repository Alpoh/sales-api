# API

## `GET /api/v1/prices`

Returns the single highest-priority price rate applicable for a brand, product, and application date.
Never returns a list — when several rate rows overlap the same window, the highest-priority one wins.

### Query parameters

| Name              | Type              | Required | Description                                    | Example               |
|-------------------|-------------------|----------|------------------------------------------------|------------------------|
| `brandId`         | `Long`            | yes      | Chain/brand identifier                          | `1`                    |
| `productId`       | `Long`            | yes      | Product identifier                              | `35455`                |
| `applicationDate` | ISO local date-time | yes    | Date and time the price applies at              | `2020-06-14T10:00:00`  |

### Example

```bash
curl "http://localhost:8080/api/v1/prices?brandId=1&productId=35455&applicationDate=2020-06-14T10:00:00"
```

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceListId": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

### Responses

| Status | When                                                                          | Body                     |
|--------|-------------------------------------------------------------------------------|--------------------------|
| `200`  | A price is applicable for the given combination                               | `ApplicablePriceResponse` (above) |
| `400`  | A required parameter is missing or malformed                                  | `ProblemDetail`          |
| `404`  | No price is applicable for the given combination                              | `ProblemDetail`          |

## Generated documentation

Full API documentation is generated automatically by `springdoc-openapi-starter-webmvc-ui` from the
controller's OpenAPI annotations — there is no separately hand-maintained API reference.

Once the app is running (`./gradlew bootRun`), it exposes:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Raw OpenAPI spec: `http://localhost:8080/v3/api-docs`

Both are enabled by default. To disable either in a given environment, set:

```properties
springdoc.swagger-ui.enabled=false
springdoc.api-docs.enabled=false
```
