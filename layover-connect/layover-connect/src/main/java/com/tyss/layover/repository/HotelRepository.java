package com.tyss.layover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer>{
	
	List<Hotel> findTop10ByOrderByRegistrationDateDesc();
	
}
