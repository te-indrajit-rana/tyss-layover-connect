package com.tyss.layover.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tyss.layover.entity.Hotel;
import com.tyss.layover.entity.HotelDetails;
import com.tyss.layover.entity.Image;
import com.tyss.layover.exception.HotelDetailsNotFoundException;
import com.tyss.layover.pojo.HotelDetailsPojo;
import com.tyss.layover.repository.HotelDetailsRepository;
import com.tyss.layover.repository.HotelRepository;
import com.tyss.layover.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotelDetailsServiceImpl implements HotelDetailsService {

	/**
	 * This Field Invoke The Hotel Details Repository Method
	 */
	private final HotelDetailsRepository hotelDetailsRepository;

	/**
	 * This Field Invoke The Method Of Hotel Persistence layer
	 */
	private final HotelRepository hotelRepository;

	/**
	 * This Field Invoke The Method Of Image Persistence layer
	 */
	private final ImageRepository imageRepository;
	/**
	 * This Method Used To Fetch The Hotel Details By Hotel Id.
	 */
	@Override
	public HotelDetailsPojo fetchByHotel(Integer hotelId) {
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new HotelDetailsNotFoundException("Invalid Hotel Id"));
		HotelDetails hoteldetails = hotelDetailsRepository.findByHotel(hotel);
		List<Image> images = imageRepository.findByHotel(hotel);
		HotelDetailsPojo hotelDetailsPojo = new HotelDetailsPojo();
		BeanUtils.copyProperties(hoteldetails, hotelDetailsPojo);
		hotelDetailsPojo.setTotalRooms(hotelDetailsPojo.getSingleRooms() + hotelDetailsPojo.getDoubleRooms()
				+ hotelDetailsPojo.getAccessibleRooms());
		hotelDetailsPojo.setHotelId(hoteldetails.getHotel().getHotelId());
		hotelDetailsPojo.setHotelDescription(hoteldetails.getHotel().getHotelDescription());
		hotelDetailsPojo.setHotelName(hoteldetails.getHotel().getHotelName());
		hotelDetailsPojo.setHotelStar(hoteldetails.getHotel().getHotelStars());
		hotelDetailsPojo.setHotelImages(images);
		return hotelDetailsPojo;
	}

	/**
	 * This Method Used To Update The Hotel Details.
	 */
	@Transactional
	@Override
	public HotelDetailsPojo updateHoteldetails(HotelDetailsPojo hotelDetailsPojo) {
		Hotel hotel = hotelRepository.findById(hotelDetailsPojo.getHotelId())
				.orElseThrow(() -> new HotelDetailsNotFoundException("Invalid Hotel ID"));
		HotelDetails hotelDetails = hotelDetailsRepository.findByHotel(hotel);
		HotelDetails details = HotelDetails.builder().hotelDetailsId(hotelDetails.getHotelDetailsId())
				.singleRooms(hotelDetailsPojo.getSingleRooms()).doubleRooms(hotelDetailsPojo.getDoubleRooms())
				.accessibleRooms(hotelDetailsPojo.getAccessibleRooms())
				.singleRoomPrice(hotelDetailsPojo.getSingleRoomPrice())
				.doubleRoomPrice(hotelDetailsPojo.getDoubleRoomPrice())
				.accessibleRoomPrice(hotelDetailsPojo.getAccessibleRoomPrice())
				.beveragePrice(hotelDetailsPojo.getBeveragePrice()).breakfastPrice(hotelDetailsPojo.getBreakfastPrice())
				.lunchPrice(hotelDetailsPojo.getLunchPrice()).dinnerPrice(hotelDetailsPojo.getDinnerPrice()).totalRooms(hotelDetailsPojo.getTotalRooms())
				.hotel(hotel).build();
		HotelDetails savehotelDetails = hotelDetailsRepository.save(details);
		BeanUtils.copyProperties(savehotelDetails, hotelDetailsPojo);

		return hotelDetailsPojo;
	}

}
