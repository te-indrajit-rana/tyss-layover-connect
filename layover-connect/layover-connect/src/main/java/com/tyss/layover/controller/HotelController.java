package com.tyss.layover.controller;

import static com.tyss.layover.constant.AdminConstants.GET_ALL_THE_LAYOVER_COUNT_BASED_MONTH;
import static com.tyss.layover.constant.HotelConstant.HOTEL_DETAILS_SUCCESSFULLY_UPDATED;
import static com.tyss.layover.constant.HotelConstant.HOTEL_IMAGES_UPDATED_SUCCESSFULLY;
import static com.tyss.layover.constant.HotelConstant.HOTEL_REGISTRATION_DETAILS_FETCH_SUCCESSFULLY;
import static com.tyss.layover.constant.HotelConstant.HOTEL_REGISTRATION_SUCCESSFULL;
import static com.tyss.layover.constant.HotelConstant.TOTAL_PAYMENT_FETCHED_SUCCESSFULLY;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.pojo.HotelMonthwiseFilterRequest;
import com.tyss.layover.pojo.HotelPojo;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.response.HotelDashbordCountResponse;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.HotelService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Hotel Page . Here you find GET, POST, PUT,
 * DELETE controllers for the Hotel Page. Here you can find the URL path for all
 * the Hotel Page services.
 * 
 * 
 * @author Akram
 * @author Shrinivas Anant Tikare
 * @author Indrajit rana
 *
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/layover/hotel")
//@SecurityRequirement(name = "layover-api")
public class HotelController {

	/**
	 * @This field is used to invoke business layer methods
	 */
	private final HotelService hotelService;

	/**
	 * @param hotelPojo
	 * @return ResponseEntity
	 */
	@Operation(summary = "This Api Used To Save Hotel Information")
	@PostMapping("/registration")
	public ResponseEntity<LayoverResponseBody> saveHotel(@RequestBody HotelPojo hotelPojo) {
		HotelPojo saveHotel = hotelService.saveHotel(hotelPojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(HOTEL_REGISTRATION_SUCCESSFULL).data(saveHotel).build();
		log.info(HOTEL_REGISTRATION_SUCCESSFULL);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The Hotel Registration

	/**
	 * @param id
	 * @return ResponseEntity
	 */
	@Operation(summary = "This Api Is Used To Fetch The Hotel Information Based On Hotel Id")
//	@PreAuthorize(value = HAS_ANY_AUTHORITY_ROLE_ADMIN_ROLE_HOTEL)
	@GetMapping("/fetch-hotel/{id}")
	public ResponseEntity<LayoverResponseBody> fetchById(@PathVariable("id") Integer id) {
		HotelPojo hotelPojo = hotelService.fetchById(id);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(HOTEL_REGISTRATION_DETAILS_FETCH_SUCCESSFULLY).data(hotelPojo).build();
		log.info(HOTEL_REGISTRATION_DETAILS_FETCH_SUCCESSFULLY);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The Fetch Hotel

	/**
	 * @param hotelPojo
	 * @return ResponseEntity
	 */
//	@PreAuthorize(value = HAS_ANY_AUTHORITY_ROLE_ADMIN_ROLE_HOTEL)
	@Operation(summary = "This Api Is Used To Update The Hotel Information")
	@PutMapping("/update-hotel")
	public ResponseEntity<LayoverResponseBody> updateHotel(@RequestBody HotelPojo hotelPojo) {
		HotelPojo saveHotel = hotelService.updateHotelDetails(hotelPojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(HOTEL_DETAILS_SUCCESSFULLY_UPDATED).data(saveHotel).build();
		log.info(HOTEL_DETAILS_SUCCESSFULLY_UPDATED);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The Update Hotel

	/**
	 * @param id
	 * @return ResponseEntity
	 */
//	@PreAuthorize(value = HAS_ANY_AUTHORITY_ROLE_ADMIN_ROLE_HOTEL)
	@Operation(summary = "This Api Is Used To Delete The Hotel Information Based On Hotel Id")
	@DeleteMapping("/delete-hotel")
	public ResponseEntity<LayoverResponseBody> deleteById(@RequestBody UserDeletePojo userDeletePojo) {
		String deleteById = hotelService.deleteById(userDeletePojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false).message(deleteById)
				.build();
		log.info(deleteById);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The Delete Hotel

	/**
	 * @param id
	 * @param multipartFile
	 * @return ResponseEntity
	 */
//	@PreAuthorize(value = HAS_ANY_AUTHORITY_ROLE_ADMIN_ROLE_HOTEL)
	@Operation(summary = "This Api Is Used To Save The Hotel Images And Hotel Logo Based On Hotel Id")
	@PutMapping(value = "/update-hotel-images/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<LayoverResponseBody> updateHotelImages(@PathVariable("id") Integer id,
			@RequestParam List<MultipartFile> multipartFile, @RequestParam MultipartFile file) {
		HotelPojo saveHotel = hotelService.updateHotelImages(multipartFile, file, id);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(HOTEL_IMAGES_UPDATED_SUCCESSFULLY).data(saveHotel).build();
		log.info(HOTEL_IMAGES_UPDATED_SUCCESSFULLY);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The Update Hotel Images

	@GetMapping("/count-hotel-dashbord/{hotelId}")
	public ResponseEntity<LayoverResponseBody> getCountOfHotelDashBord(@PathVariable("hotelId") Integer hotelId) {
		HotelDashbordCountResponse hotelCount = hotelService.hotelCount(hotelId);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message("Get Count Successfully").data(hotelCount).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

	@GetMapping("/get-hotel-dashboard-count-monthwise")
	public ResponseEntity<LayoverResponseBody> getAllTheTotalLayoverCountBasedOnMonth(
			HotelMonthwiseFilterRequest hotelMonthwiseFilterRequest) {
		Map<String, Long> totalLayoverCountBasedOnMonth = hotelService
				.totalLayoverCountBasedOnMonth(hotelMonthwiseFilterRequest);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(GET_ALL_THE_LAYOVER_COUNT_BASED_MONTH).data(totalLayoverCountBasedOnMonth).build();
		return ResponseEntity.ok().body(layoverResponseBody);

	}

	@GetMapping("/get-hotel-dashboard-payment-monthwise")
	public ResponseEntity<LayoverResponseBody> getAllTheTotalPaymetGenerateBasedOnMonth(
			HotelMonthwiseFilterRequest hotelMonthwiseFilterRequest) {
		SortedMap<String, Double> totalAmountGeneratedBasedOnMonth = hotelService
				.totalLayoverPaymentGeneratedBasedOnMonth(hotelMonthwiseFilterRequest);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(TOTAL_PAYMENT_FETCHED_SUCCESSFULLY).data(totalAmountGeneratedBasedOnMonth).build();
		return ResponseEntity.ok().body(layoverResponseBody);

	}
}
