package com.companycob.domain.model.dto;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationErrorsCollectionTest {

	@Test
	public void getAddIndividualErrorsAndGetAtTheEnd() {
		var errors = new ValidationErrorsCollection();
		errors.addError("teste", "message");
		errors.addError("teste2", "message2");

		var errorsList = errors.getErrors();
		assertThat(errorsList.size()).isEqualTo(2);

		assertThat(errorsList.get(0).getProperty()).isEqualTo("teste");
		assertThat(errorsList.get(0).getError()).isEqualTo("message");

		assertThat(errorsList.get(1).getProperty()).isEqualTo("teste2");
		assertThat(errorsList.get(1).getError()).isEqualTo("message2");
	}

	@Test
	public void getAddAllErrorsAndGetAtTheEnd() {
		var errors = new ValidationErrorsCollection();

		var error1 = new ValidationErrorsCollection.ValidationErrorDTO("teste", "message");
		var error2 = new ValidationErrorsCollection.ValidationErrorDTO("teste2", "message2");

		errors.addAllErrors(List.of(error1, error2));

		var errorsList = errors.getErrors();
		assertThat(errorsList.size()).isEqualTo(2);

		assertThat(errorsList.get(0).getProperty()).isEqualTo("teste");
		assertThat(errorsList.get(0).getError()).isEqualTo("message");

		assertThat(errorsList.get(1).getProperty()).isEqualTo("teste2");
		assertThat(errorsList.get(1).getError()).isEqualTo("message2");
	}
}
