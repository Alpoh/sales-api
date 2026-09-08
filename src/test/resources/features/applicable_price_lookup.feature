Feature: Applicable price lookup

  Scenario Outline: Resolving the applicable price for a product at a given date
    Given a request for brand <brandId>, product <productId> at <applicationDate>
    When the applicable price is requested
    Then the response contains price list <priceListId> with price <price>

    Examples:
      | brandId | productId | applicationDate     | priceListId | price |
      | 1       | 35455     | 2020-06-14T09:59:59 | 1           | 35.50 |
      | 1       | 35455     | 2020-06-14T10:00:00 | 1           | 35.50 |
      | 1       | 35455     | 2020-06-14T15:00:00 | 2           | 25.45 |
      | 1       | 35455     | 2020-06-14T16:00:00 | 2           | 25.45 |
      | 1       | 35455     | 2020-06-14T18:30:00 | 2           | 25.45 |
      | 1       | 35455     | 2020-06-14T21:00:00 | 1           | 35.50 |
      | 1       | 35455     | 2020-06-15T10:00:00 | 3           | 30.50 |
      | 1       | 35455     | 2020-06-15T11:00:00 | 3           | 30.50 |
      | 1       | 35455     | 2020-06-15T16:00:00 | 4           | 38.95 |
      | 1       | 35455     | 2020-06-16T21:00:00 | 4           | 38.95 |

  Scenario: No price is applicable for the given combination
    Given a request for brand 999, product 1 at 2020-06-14T10:00:00
    When the applicable price is requested
    Then the response has status 404

  Scenario: Rejects invalid identifiers
    Given a request for brand 0, product -1 at 2020-06-14T10:00:00
    When the applicable price is requested
    Then the response has status 400

  Scenario: Prefers the highest-priority price when more than five price rows overlap
    Given a request for brand 2, product 99999 at 2020-06-14T20:15:00
    When the applicable price is requested
    Then the response contains price list 107 with price 31.00
