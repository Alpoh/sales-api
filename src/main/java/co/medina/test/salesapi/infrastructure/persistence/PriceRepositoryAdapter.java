package co.medina.test.salesapi.infrastructure.persistence;

import co.medina.test.salesapi.application.port.out.PriceRepository;
import co.medina.test.salesapi.domain.model.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {

	private final PriceJpaRepository priceJpaRepository;
	private final PriceEntityMapper priceEntityMapper;

	@Override
	public Optional<Price> findHighestPriorityApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate) {
		return priceJpaRepository
				.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
						brandId, productId, applicationDate, applicationDate)
				.map(priceEntityMapper::toDomain);
	}

}
