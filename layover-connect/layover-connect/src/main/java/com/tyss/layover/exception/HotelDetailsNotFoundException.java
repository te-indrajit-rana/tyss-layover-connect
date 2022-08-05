package com.tyss.layover.exception;

@SuppressWarnings("serial")
public class HotelDetailsNotFoundException extends RuntimeException {

	public HotelDetailsNotFoundException(String message) {
		super(message);
	}
}
