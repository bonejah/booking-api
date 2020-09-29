package com.lslb.app.bookingapi.controllers;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lslb.app.bookingapi.exceptions.BookingCreatedException;
import com.lslb.app.bookingapi.exceptions.BookingDayInAdvanceException;
import com.lslb.app.bookingapi.exceptions.BookingEmptyException;
import com.lslb.app.bookingapi.exceptions.BookingMaxDateException;
import com.lslb.app.bookingapi.exceptions.BookingMonthInAdvanceException;
import com.lslb.app.bookingapi.exceptions.BookingNotFoundException;
import com.lslb.app.bookingapi.models.Booking;
import com.lslb.app.bookingapi.repositories.BookingRepository;

@RestController
@RequestMapping(path = "api/v1")
public class BookingController {

	@Autowired
	private BookingRepository repository;
	
	@GetMapping(value = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> getAllBookingsCurrentMonth() {
		List<Booking> pageResult = repository.getAllBookingsCurrentMonth();

		if (pageResult.isEmpty()) {
			throw new BookingEmptyException("There are no booking(s)!");
		}

		return new ResponseEntity<List<Booking>>(pageResult, HttpStatus.OK);
	}

	@GetMapping(value = "/booking/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> getAllBookingsCurrentMonthPageable() {		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("start_date"));
		
		Page<Booking> pageResult = repository.getAllBookingsCurrentMonthPageable(pageRequest);

		if (pageResult.isEmpty()) {
			throw new BookingEmptyException("There are no booking(s)!");
		}

		return new ResponseEntity<List<Booking>>(pageResult.getContent(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/booking/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Booking> getBook(@PathVariable("id") Long id) {
		Booking booking = repository.findById(id)
				.orElseThrow(() -> new BookingNotFoundException("Booking ID: " + id + " not found!"));

		return new ResponseEntity<Booking>(booking, HttpStatus.OK);
	}

	@PostMapping(value = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Booking> saveBooking(@Valid @RequestBody Booking book) {
		checkDates(book);
		
		Booking bookingFounded = repository.findBookByNameEmailAndDate(book.getName(), book.getEmail(), 
				book.getStartDate(), book.getEndDate());
		
		if (bookingFounded != null) {
			throw new BookingCreatedException("Exists a booking with this information: " + book);
		}
		
		Booking bookCreated = repository.save(book);

		return new ResponseEntity<Booking>(bookCreated, HttpStatus.OK);
	}

	@PutMapping(value = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Booking> updateBook(@RequestBody Booking book) {
		checkDates(book);

		Booking bookUpdated = repository.save(book);

		return new ResponseEntity<Booking>(bookUpdated, HttpStatus.OK);
	}

	@DeleteMapping(value = "/booking/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Booking> deleteBook(@PathVariable Long id) {
		Booking booking = repository.findById(id)
				.orElseThrow(() -> new BookingNotFoundException("Booking ID: " + id + " not found!"));

		repository.delete(booking);

		return new ResponseEntity<Booking>(HttpStatus.OK);
	}

	private void checkDates(Booking book) {
		LocalDate localDateStart = book.getStartDate().toLocalDate();
		LocalDate localDateEnd = book.getEndDate().toLocalDate();
		Period pDay = Period.between(localDateStart, LocalDate.now());

		if (pDay.getDays() != 0 && pDay.getDays() != 1) {
			throw new BookingDayInAdvanceException("The campsite can be booked at least 1 day in advance");
		}

		Period pMonth = Period.between(LocalDate.now(), localDateStart);
		
		if (pMonth.getMonths() != 0 && pMonth.getMonths() != 1) {
			throw new BookingMonthInAdvanceException("The campsite can be booked at least 1 month in advance");
		}

		long days = ChronoUnit.DAYS.between(localDateStart, localDateEnd);

		if (days > 3) {
			throw new BookingMaxDateException("Booking cannot be more 3 days");
		}
	}

}
