package com.companycob.tests.exception;

public class SetupDatabaseException extends RuntimeException {

	private static final long serialVersionUID = 7606955495093227622L;

	public SetupDatabaseException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
