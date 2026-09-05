package co.medina.test.salesapi.application.service;

import co.medina.test.salesapi.application.port.in.ObtainApplicablePriceQuery;
import co.medina.test.salesapi.application.port.in.ObtainApplicablePriceUseCase;
import co.medina.test.salesapi.application.port.out.PriceRepository;
import co.medina.test.salesapi.domain.exception.ApplicablePriceNotFoundException;
import co.medina.test.salesapi.domain.model.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ObtainApplicablePriceService implements ObtainApplicablePriceUseCase {

	private final PriceRepository priceRepository;

	@Override
	public Price obtainApplicablePrice(ObtainApplicablePriceQuery query) {
		return priceRepository.findHighestPriorityApplicablePrice(query.brandId(), query.productId(), query.applicationDate())
				.orElseThrow(() -> new ApplicablePriceNotFoundException(query.brandId(), query.productId(), query.applicationDate()));
	}

}
