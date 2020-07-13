package com.lslb.app.bookingapi.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lslb.app.bookingapi.models.Booking;


@SpringBootTest
public class BookingControlerTest {
	
	@Autowired
	private BookingController controller;
	
	
	
	@Test()
	public void shouldExecuteConcurrentRequests() {
		Booking booking = new Booking("teste", "teste@gmail.com", LocalDateTime.now(), LocalDateTime.now());
		
		int countSaved = 0;
		
		for (int i = 0; i < 10000; i++) {
			controller.saveBooking(booking);
			booking = new Booking("teste", "teste@gmail.com", LocalDateTime.now(), LocalDateTime.now());
			countSaved++;
		}
		
		assertEquals(10000, countSaved);
	}

}
