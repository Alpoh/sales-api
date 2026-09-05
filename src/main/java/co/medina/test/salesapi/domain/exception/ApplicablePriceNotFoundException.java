package co.medina.test.salesapi.domain.exception;

import java.time.LocalDateTime;

public class ApplicablePriceNotFoundException extends RuntimeException {

	public ApplicablePriceNotFoundException(Long brandId, Long productId, LocalDateTime applicationDate) {
		super("No applicable price found for brandId=%d, productId=%d, applicationDate=%s"
				.formatted(brandId, productId, applicationDate));
	}

}
