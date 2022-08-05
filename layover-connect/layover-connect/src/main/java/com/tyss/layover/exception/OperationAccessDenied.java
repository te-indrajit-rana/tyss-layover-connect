package com.tyss.layover.exception;

public class OperationAccessDenied extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OperationAccessDenied(String message) {
		super(message);
	}
}
