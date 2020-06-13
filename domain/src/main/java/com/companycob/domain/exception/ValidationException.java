package com.companycob.domain.exception;

import com.companycob.domain.model.dto.ValidationErrorsDTO;

public class ValidationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private ValidationErrorsDTO errors;

	public ValidationException(ValidationErrorsDTO errors) {
		super();
		this.errors = errors;
	}

	public ValidationErrorsDTO getErrors() {
		return errors;
	}
}
