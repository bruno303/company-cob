package com.companycob.domain.exception;

import java.util.List;

import com.companycob.domain.model.dto.ValidationErrorsCollection;
import com.companycob.domain.model.dto.ValidationErrorsListCollection;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ValidationErrorsListCollection errors;

	public ValidationException() {
		this.errors = new ValidationErrorsListCollection();
	}

	public ValidationException(ValidationErrorsCollection errors) {
		this();
		this.errors.add(errors);
	}

	public ValidationException(List<ValidationErrorsCollection> errors) {
		this();
		this.errors.addAll(errors);
	}

	public List<ValidationErrorsCollection> getErrors() {
		return errors;
	}
}
