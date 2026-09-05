package co.medina.test.salesapi.application.port.in;

import co.medina.test.salesapi.domain.model.Price;

public interface ObtainApplicablePriceUseCase {

	Price obtainApplicablePrice(ObtainApplicablePriceQuery query);

}
