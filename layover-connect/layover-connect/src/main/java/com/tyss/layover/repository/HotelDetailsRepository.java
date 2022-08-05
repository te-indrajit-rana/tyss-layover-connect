package com.tyss.layover.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.Hotel;
import com.tyss.layover.entity.HotelDetails;

public interface HotelDetailsRepository extends JpaRepository<HotelDetails, Integer> {

	HotelDetails findByHotel(Hotel hotel);
}
