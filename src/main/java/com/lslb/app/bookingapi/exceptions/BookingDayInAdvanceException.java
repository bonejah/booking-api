package com.lslb.app.bookingapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingDayInAdvanceException extends RuntimeException {

	private static final long serialVersionUID = -8337252101630467557L;

	public BookingDayInAdvanceException(String exception) {
		super(exception);
	}
	
}
