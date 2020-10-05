package com.companycob.domain.exception;

import java.util.ArrayList;
import java.util.List;

import com.companycob.domain.model.dto.ValidationErrorsCollection;

public class ValidationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private List<ValidationErrorsCollection> errors;
	
	public ValidationException() {
		this.errors = new ArrayList<>();
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
