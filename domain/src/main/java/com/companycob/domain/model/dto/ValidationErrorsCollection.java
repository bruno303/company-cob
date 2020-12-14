package com.companycob.domain.model.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ValidationErrorsCollection implements Serializable {

	private static final long serialVersionUID = 4470419054738645352L;

	private ValidationErrorList errors;

	public List<ValidationErrorDTO> getErrors() {
		verifyErrorsList();
		return Collections.unmodifiableList(errors);
	}

	public boolean hasErrors() {
		verifyErrorsList();
		return !errors.isEmpty();
	}

	private void verifyErrorsList() {
		if (errors == null) {
			errors = new ValidationErrorList();
		}
	}

	public void addError(final String property, final String errorMessage) {
		if (errors == null) {
			errors = new ValidationErrorList();
		}

		errors.add(new ValidationErrorDTO(property, errorMessage));
	}

	public void addAllErrors(final List<ValidationErrorDTO> errors) {
		if (this.errors == null) {
			this.errors = new ValidationErrorList();
		}

		this.errors.addAll(errors);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		getErrors().forEach(error -> builder.append(error.toString()).append(" | "));
		return builder.toString();
	}

	public static class ValidationErrorDTO {
		private final String property;
		private final String error;

		public ValidationErrorDTO(final String property, final String error) {
			this.property = property;
			this.error = error;
		}

		public String getProperty() {
			return property;
		}

		public String getError() {
			return error;
		}

		@Override
		public String toString() {
			return String.format("%s -> %s", property, error);
		}
	}
}
