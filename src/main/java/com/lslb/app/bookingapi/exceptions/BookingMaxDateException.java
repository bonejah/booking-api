package com.lslb.app.bookingapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingMaxDateException extends RuntimeException {

	private static final long serialVersionUID = -8114704270869317846L;

	public BookingMaxDateException(String exception) {
		super(exception);
	}
	
}
