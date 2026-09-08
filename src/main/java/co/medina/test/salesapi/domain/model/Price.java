package co.medina.test.salesapi.domain.model;

import java.time.LocalDateTime;

public record Price(
        Long brandId,
        Long productId,
        Long priceListId,
        Integer priority,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Money price
) {
}
