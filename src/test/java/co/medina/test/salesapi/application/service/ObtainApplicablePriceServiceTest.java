package co.medina.test.salesapi.application.service;

import co.medina.test.salesapi.application.port.in.ObtainApplicablePriceQuery;
import co.medina.test.salesapi.application.port.out.PriceRepository;
import co.medina.test.salesapi.domain.exception.ApplicablePriceNotFoundException;
import co.medina.test.salesapi.domain.model.Money;
import co.medina.test.salesapi.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObtainApplicablePriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    private ObtainApplicablePriceService service;

    @BeforeEach
    void setUp() {
        service = new ObtainApplicablePriceService(priceRepository);
    }

    @Test
    void obtainsThePriceReturnedByTheRepository() {
        var query = new ObtainApplicablePriceQuery(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0));
        var expectedPrice = new Price(1L, 35455L, 1L, 0,
                LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                new Money(BigDecimal.valueOf(35.50), Currency.getInstance("EUR")));
        when(priceRepository.findHighestPriorityApplicablePrice(query.brandId(), query.productId(), query.applicationDate()))
                .thenReturn(Optional.of(expectedPrice));

        var result = service.obtainApplicablePrice(query);

        assertThat(result).isEqualTo(expectedPrice);
    }

    @Test
    void throwsWhenNoPriceIsApplicable() {
        var query = new ObtainApplicablePriceQuery(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0));
        when(priceRepository.findHighestPriorityApplicablePrice(query.brandId(), query.productId(), query.applicationDate()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtainApplicablePrice(query))
                .isInstanceOf(ApplicablePriceNotFoundException.class);
    }

}
