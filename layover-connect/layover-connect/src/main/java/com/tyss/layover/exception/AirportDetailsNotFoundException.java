package com.tyss.layover.exception;

public class AirportDetailsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AirportDetailsNotFoundException(String message) {
		super(message);
	}

}
