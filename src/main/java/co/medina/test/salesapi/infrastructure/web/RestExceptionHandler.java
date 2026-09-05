package co.medina.test.salesapi.infrastructure.web;

import co.medina.test.salesapi.domain.exception.ApplicablePriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ApplicablePriceNotFoundException.class)
	public ProblemDetail handleApplicablePriceNotFound(ApplicablePriceNotFoundException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
	}

}
