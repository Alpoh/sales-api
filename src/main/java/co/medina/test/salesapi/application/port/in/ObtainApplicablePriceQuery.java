package co.medina.test.salesapi.application.port.in;

import java.time.LocalDateTime;

public record ObtainApplicablePriceQuery(
		Long brandId,
		Long productId,
		LocalDateTime applicationDate
) {
}
