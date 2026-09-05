package co.medina.test.salesapi.infrastructure.web;

import co.medina.test.salesapi.application.port.in.ObtainApplicablePriceQuery;
import co.medina.test.salesapi.application.port.in.ObtainApplicablePriceUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
@Validated
@RequiredArgsConstructor
public class PricesController {

	private final ObtainApplicablePriceUseCase obtainApplicablePriceUseCase;
	private final PriceWebMapper priceWebMapper;

	@GetMapping
	public ApplicablePriceResponse obtainApplicablePrice(
			@RequestParam @NotNull Long brandId,
			@RequestParam @NotNull Long productId,
			@RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {
		var price = obtainApplicablePriceUseCase.obtainApplicablePrice(
				new ObtainApplicablePriceQuery(brandId, productId, applicationDate));
		return priceWebMapper.toResponse(price);
	}

}
