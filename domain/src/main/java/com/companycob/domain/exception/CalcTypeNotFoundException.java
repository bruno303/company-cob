package com.companycob.domain.exception;

public class CalcTypeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final int id;

	public CalcTypeNotFoundException(final int id) {
		this.id = id;
	}

	@Override
	public String getMessage() {
		return String.format("CalcType with id %d not found.", id);
	}
}
