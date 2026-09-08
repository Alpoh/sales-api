package co.medina.test.salesapi.infrastructure.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class PricesControllerValidationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void rejectsMissingRequiredParameters() {
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                "/api/v1/prices?brandId=1&productId=35455",
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void rejectsMalformedApplicationDate() {
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                "/api/v1/prices?brandId=1&productId=35455&applicationDate=not-a-date",
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void rejectsNonPositiveEntityIdentifiers() {
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                "/api/v1/prices?brandId=0&productId=-1&applicationDate=2020-06-14T10:00:00",
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
