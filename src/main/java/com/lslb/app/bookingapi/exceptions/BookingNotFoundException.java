package com.lslb.app.bookingapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookingNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 7013843299166299838L;

	public BookingNotFoundException(String exception) {
		super(exception);
	}
	
}
