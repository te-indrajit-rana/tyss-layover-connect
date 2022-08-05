package com.tyss.layover.exception;

public class IncorrectOtpException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IncorrectOtpException(String message){
		super(message);
	}
}
