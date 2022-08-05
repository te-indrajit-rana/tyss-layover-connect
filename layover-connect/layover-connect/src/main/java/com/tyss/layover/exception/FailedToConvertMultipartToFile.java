package com.tyss.layover.exception;

public class FailedToConvertMultipartToFile extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public  FailedToConvertMultipartToFile(String message) {
		super(message);
	}
}
