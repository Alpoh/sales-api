package co.medina.test.salesapi.infrastructure.web;

import co.medina.test.salesapi.application.port.in.ObtainApplicablePriceQuery;
import co.medina.test.salesapi.application.port.in.ObtainApplicablePriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Tag(name = "Prices", description = "Applicable price rate lookup")
public class PricesController {

    private final ObtainApplicablePriceUseCase obtainApplicablePriceUseCase;
    private final PriceWebMapper priceWebMapper;

    @GetMapping
    @Operation(summary = "Find the single applicable price for a brand, product, and application date")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The applicable price was found"),
            @ApiResponse(responseCode = "404", description = "No price is applicable for the given combination")
    })
    public ApplicablePriceResponse obtainApplicablePrice(
            @RequestParam @NotNull @Positive
            @Parameter(description = "Chain/brand identifier", example = "1") Long brandId,
            @RequestParam @NotNull @Positive
            @Parameter(description = "Product identifier", example = "35455") Long productId,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @Parameter(description = "Date and time the price applies at", example = "2020-06-14T10:00:00") LocalDateTime applicationDate) {
        var price = obtainApplicablePriceUseCase.obtainApplicablePrice(
                new ObtainApplicablePriceQuery(brandId, productId, applicationDate));
        return priceWebMapper.toResponse(price);
    }

}
