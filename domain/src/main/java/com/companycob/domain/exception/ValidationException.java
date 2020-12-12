package com.companycob.domain.exception;

import com.companycob.domain.model.dto.ValidationErrorsCollection;
import com.companycob.domain.model.dto.ValidationErrorsListCollection;

import java.util.List;

public class ValidationException extends Exception {
	
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
