package com.tyss.layover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Integer> {
	
	List<Airline> findTop10ByOrderByRegistrationDateDesc();

}
