package co.medina.test.salesapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Sales API",
        version = "v1",
        description = "Looks up the single applicable price rate for a brand, product, and application date."
))
public class SalesApiApplication {

    static void main(String[] args) {
        SpringApplication.run(SalesApiApplication.class, args);
    }

}
