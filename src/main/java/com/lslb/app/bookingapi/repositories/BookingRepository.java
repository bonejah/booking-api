package com.lslb.app.bookingapi.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lslb.app.bookingapi.models.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query(value = "SELECT * FROM booking_api.bookings WHERE MONTH(start_date) = MONTH(CURRENT_DATE())", nativeQuery = true)
	List<Booking> getAllBookingsCurrentMonth();

	@Query(value = "SELECT * FROM booking_api.bookings WHERE MONTH(start_date) = MONTH(CURRENT_DATE())", nativeQuery = true)
	Page<Booking> getAllBookingsCurrentMonthPageable(Pageable pageable);

	@Query(value = "SELECT * FROM booking_api.bookings WHERE name = ? and email =? and start_date = ? and end_date = ?", nativeQuery = true)
	Booking findBookByNameEmailAndDate(String name, String email, LocalDateTime startDate, LocalDateTime endDate);

}
