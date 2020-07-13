package com.lslb.app.bookingapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class BookingCreatedException extends RuntimeException {

	private static final long serialVersionUID = 9029535391344771939L;

	public BookingCreatedException(String exception) {
		super(exception);
	}

}
