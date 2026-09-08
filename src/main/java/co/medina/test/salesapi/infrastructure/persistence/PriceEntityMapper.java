package co.medina.test.salesapi.infrastructure.persistence;

import co.medina.test.salesapi.domain.model.Money;
import co.medina.test.salesapi.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Currency;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

    @Mapping(target = "price", expression = "java(toMoney(entity.getPrice(), entity.getCurrency()))")
    Price toDomain(PriceEntity entity);

    default Money toMoney(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }

}
