package co.medina.test.salesapi.bdd;

import co.medina.test.salesapi.infrastructure.web.ApplicablePriceResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ApplicablePriceLookupSteps {

    private final TestRestTemplate testRestTemplate;

    private Long brandId;
    private Long productId;
    private String applicationDate;
    private ResponseEntity<ApplicablePriceResponse> response;

    @Given("a request for brand {long}, product {long} at {word}")
    public void aRequestForBrandProductAt(Long brandId, Long productId, String applicationDate) {
        this.brandId = brandId;
        this.productId = productId;
        this.applicationDate = applicationDate;
    }

    @When("the applicable price is requested")
    public void theApplicablePriceIsRequested() {
        var url = "/api/v1/prices?brandId=%d&productId=%d&applicationDate=%s".formatted(brandId, productId, applicationDate);
        response = testRestTemplate.getForEntity(url, ApplicablePriceResponse.class);
    }

    @Then("the response contains price list {long} with price {bigdecimal}")
    public void theResponseContainsPriceListWithPrice(Long priceListId, BigDecimal price) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().priceListId()).isEqualTo(priceListId);
        assertThat(response.getBody().price()).isEqualByComparingTo(price);
    }

    @Then("the response has status {int}")
    public void theResponseHasStatus(int status) {
        assertThat(response.getStatusCode().value()).isEqualTo(status);
    }

}
