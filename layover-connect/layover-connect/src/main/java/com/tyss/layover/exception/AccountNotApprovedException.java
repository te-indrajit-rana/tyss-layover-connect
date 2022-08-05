package com.tyss.layover.exception;

public class AccountNotApprovedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountNotApprovedException(String message) {
		super(message);
	}
}
