package com.tyss.layover.service;

import java.util.List;
import java.util.SortedMap;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.pojo.HotelMonthwiseFilterRequest;
import com.tyss.layover.pojo.HotelPojo;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.response.HotelDashbordCountResponse;

public interface HotelService {

	HotelPojo saveHotel(HotelPojo hotelPojo);

	HotelPojo fetchById(Integer id);

	String deleteById(UserDeletePojo userDeletePojo);

	HotelPojo updateHotelDetails(HotelPojo hotelPojo);

	HotelPojo updateHotelImages(List<MultipartFile> images, MultipartFile logo, Integer data);

	HotelDashbordCountResponse hotelCount(Integer hotelId);

	SortedMap<String, Long> totalLayoverCountBasedOnMonth(HotelMonthwiseFilterRequest hotelMonthwiseFilterRequest);

	SortedMap<String, Double> totalLayoverPaymentGeneratedBasedOnMonth(
			HotelMonthwiseFilterRequest hotelMonthwiseFilterRequest);
}
