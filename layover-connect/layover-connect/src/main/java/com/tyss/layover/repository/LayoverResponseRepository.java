package com.tyss.layover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.LayoverRequest;
import com.tyss.layover.entity.LayoverResponse;

public interface LayoverResponseRepository extends JpaRepository<LayoverResponse, Integer> {

	LayoverResponse findByLayoverRequest(LayoverRequest layoverRequest);

	LayoverResponse findByStatus(String status);

	Long countByStatus(String status);

	List<LayoverResponse> findAllByTotalAmountNotNull();

	List<LayoverResponse> findByLayoverRequestHotelDetailsHotelHotelId(Integer hotelId);

	List<LayoverResponse> findByLayoverRequestAirlineAirlineId(Integer airlineId);

}
