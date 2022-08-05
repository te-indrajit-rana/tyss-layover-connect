package com.tyss.layover.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.Airport;

public interface AirportRepository extends JpaRepository<Airport, Integer> {

	Optional<Airport> findByAirportId(Integer airportId);

	List<Airport> findTop10ByOrderByRegistrationDateDesc();

}
