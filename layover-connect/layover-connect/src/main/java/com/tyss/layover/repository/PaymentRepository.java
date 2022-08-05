package com.tyss.layover.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.LayoverResponse;
import com.tyss.layover.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	Payment findByLayoverResponse(LayoverResponse layoverResponse);

	Payment findByLayoverResponseAndTimestampBetween(LayoverResponse layoverResponse, LocalDateTime startdate,
			LocalDateTime endDate);

	List<Payment> findByLayoverResponseLayoverRequestHotelDetailsHotelHotelId(Integer hotelId);

	List<Payment> findByLayoverResponseLayoverRequestAirlineAirlineId(Integer airlineId);

}
