package co.medina.test.salesapi.infrastructure.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ApplicablePriceResponse(
		Long productId,
		Long brandId,
		Long priceListId,
		LocalDateTime startDate,
		LocalDateTime endDate,
		BigDecimal price,
		String currency
) {
}
