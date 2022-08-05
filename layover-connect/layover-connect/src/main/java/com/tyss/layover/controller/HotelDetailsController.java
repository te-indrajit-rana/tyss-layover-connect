package com.tyss.layover.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.layover.pojo.HotelDetailsPojo;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.HotelDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

/**
 * This is a controller class for HotelDetails Page . Here you find GET, PUT,
 * DELETE controllers for the HotelDetails Page. Here you can find the URL path
 * for all the HotelDetails Page services.
 * 
 * 
 * @author Akram
 * @author Shrinivas Anant Tikare
 * @author Indrajit rana
 *
 */

@CrossOrigin(origins = "*" )
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/layover/hotel-details")
//@SecurityRequirement(name = "layover-api")
public class HotelDetailsController {

	/**
	 * This Field Is Invoke The Business layer Method
	 */
	private final HotelDetailsService hotelDetailsService;

	/**
	 * @param id
	 * @return ResponseEntity
	 */
//	@PreAuthorize(value = HAS_ANY_AUTHORITY_ROLE_ADMIN_ROLE_HOTEL)
	@Operation(summary = "This Api Is Used To Fetch the HotelDetails Information Based On The Hotel Id")
	@GetMapping("/fetch-hotel-detail/{id}")
	public ResponseEntity<LayoverResponseBody> fetchbyHotelId(@PathVariable("id") Integer id) {
		HotelDetailsPojo hotelDetailsPojo = hotelDetailsService.fetchByHotel(id);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message("HotelDetails Fetch Successfully").data(hotelDetailsPojo).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

//	@PreAuthorize(value = HAS_ANY_AUTHORITY_ROLE_ADMIN_ROLE_HOTEL)
	/**
	 * @param hotelDetailsPojo
	 * @return ResponseEntity
	 */
	@Operation(summary = "This Api Is Used To Update The Hoteldetails Information")
	@PutMapping("update-hotel-details")
	public ResponseEntity<LayoverResponseBody> updateHotelDetails(@RequestBody HotelDetailsPojo hotelDetailsPojo) {
		HotelDetailsPojo hoteldetails = hotelDetailsService.updateHoteldetails(hotelDetailsPojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false).message("Hotel Details Updated Successfully").data(hoteldetails)
				.build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

}
