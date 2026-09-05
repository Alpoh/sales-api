package co.medina.test.salesapi.infrastructure.web;

import co.medina.test.salesapi.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceWebMapper {

	@Mapping(target = "price", expression = "java(domainPrice.price().amount())")
	@Mapping(target = "currency", expression = "java(domainPrice.price().currency().getCurrencyCode())")
	ApplicablePriceResponse toResponse(Price domainPrice);

}
