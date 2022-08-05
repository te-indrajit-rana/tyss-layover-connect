package com.tyss.layover.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.Airline;
import com.tyss.layover.entity.HotelDetails;
import com.tyss.layover.entity.LayoverRequest;

public interface LayoverRequestRepository extends JpaRepository<LayoverRequest, Integer> {

	List<LayoverRequest> findTop10ByOrderByTimestampDesc();

	Optional<List<LayoverRequest>> findByHotelDetails(HotelDetails hotelDetails);

	Optional<List<LayoverRequest>> findByAirline(Airline airline);

	Optional<List<LayoverRequest>> findByHotelDetailsAndTimestampBetween(HotelDetails hotelDetails,
			LocalDateTime startdate, LocalDateTime endDate);

	Optional<List<LayoverRequest>> findByAirlineAndTimestampBetween(Airline airline, LocalDateTime startdate,
			LocalDateTime endDate);

	List<LayoverRequest> findByHotelDetailsHotelHotelId(int hotelId);

//	List<LayoverRequest> findByAirlineAirlineId(Integer airlineId);
	Optional<List<LayoverRequest>> findByAirlineAirlineId(Integer airlineId);

	Optional<List<LayoverRequest>> findByAirlineAirlineIdAndTimestampBetween(Integer airlineId, LocalDateTime startdate,
			LocalDateTime endDate);

	Optional<List<LayoverRequest>> findByHotelDetailsHotelDetailsId(Integer hotelDetailsId);

	Optional<List<LayoverRequest>> findByHotelDetailsHotelDetailsIdAndTimestampBetween(Integer hotelDetailsId,
			LocalDateTime startDateTime, LocalDateTime endDateTime);

//	List<LayoverRequest> findByAirlineAirlineId(Integer airlineId);
}
