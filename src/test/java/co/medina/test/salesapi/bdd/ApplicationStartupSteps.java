package co.medina.test.salesapi.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ApplicationStartupSteps {

	private final ApplicationContext applicationContext;

	@Given("the application context is starting")
	public void theApplicationContextIsStarting() {
	}

	@When("the context finishes loading")
	public void theContextFinishesLoading() {
	}

	@Then("the context is available")
	public void theContextIsAvailable() {
		assertThat(applicationContext).isNotNull();
	}

}
