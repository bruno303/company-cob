package com.companycob.domain.model.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationErrorsDTO {
	
	private List<ValidationErrorDTO> errors;

	public List<ValidationErrorDTO> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	public boolean hasErrors() {
		if (errors == null) {
			errors = new ArrayList<ValidationErrorDTO>();
		}
		
		return !errors.isEmpty();
	}
	
	public void addError(String property, String errorMessage) {
		if (errors == null) {
			errors = new ArrayList<ValidationErrorDTO>();
		}
		
		errors.add(new ValidationErrorDTO(property, errorMessage));
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		getErrors().forEach(error -> builder.append(error.toString()).append(" | "));
		return builder.toString();
	}
	
	public static class ValidationErrorDTO {
		private String property;
		private String error;
		
		public ValidationErrorDTO(String property, String error) {
			super();
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
