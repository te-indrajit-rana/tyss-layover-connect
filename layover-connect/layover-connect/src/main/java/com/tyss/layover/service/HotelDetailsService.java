package com.tyss.layover.service;

import com.tyss.layover.pojo.HotelDetailsPojo;

public interface HotelDetailsService {
	
	
	
	HotelDetailsPojo fetchByHotel(Integer hotelId);
	
	HotelDetailsPojo updateHoteldetails(HotelDetailsPojo hotelDetailsPojo);

}
