package co.medina.test.salesapi.application.port.out;

import co.medina.test.salesapi.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {

    Optional<Price> findHighestPriorityApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate);

}
