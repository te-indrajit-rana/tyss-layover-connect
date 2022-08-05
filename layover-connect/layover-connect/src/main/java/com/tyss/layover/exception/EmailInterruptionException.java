package com.tyss.layover.exception;

public class EmailInterruptionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailInterruptionException(String message) {
		super(message);
	}
}
