Feature: Applicable price lookup

  Scenario Outline: Resolving the applicable price for a product at a given date
    Given a request for brand <brandId>, product <productId> at <applicationDate>
    When the applicable price is requested
    Then the response contains price list <priceListId> with price <price>

    Examples:
      | brandId | productId | applicationDate     | priceListId | price |
      | 1       | 35455     | 2020-06-14T10:00:00 | 1           | 35.50 |
      | 1       | 35455     | 2020-06-14T16:00:00 | 2           | 25.45 |
      | 1       | 35455     | 2020-06-14T21:00:00 | 1           | 35.50 |
      | 1       | 35455     | 2020-06-15T10:00:00 | 3           | 30.50 |
      | 1       | 35455     | 2020-06-16T21:00:00 | 4           | 38.95 |
