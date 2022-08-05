package com.tyss.layover.controller;

import static com.tyss.layover.constant.LayoverRequestResponseConstant.HOTEL_DETAILS_FETCH_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.LAYOVER_REQUEST_FETCH_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THERE_ARE_NO_ACCEPTED_LAYOVER_REQUESTS_FOR_THIS_AIRLINE;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THERE_ARE_NO_LAYOVER_REQUESTS_FOR_THIS_AIRLINE;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THERE_ARE_NO_OPEN_LAYOVER_REQUESTS_FOR_THIS_AIRLINE;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THERE_ARE_NO_ROOMS_AVAILABLE_FOR_THE_REQUESTED_CAPACITY;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THIS_API_IS_USED_TO_FETCH_ALL_THE_ACCEPTED_LAYOVER_REQUEST_BASED_ON_FROM_DATE_TO_END_DATE_AND_AIRLINE_ID_BY_AIRLINE;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THIS_API_IS_USED_TO_FETCH_ALL_THE_LAYOVER_REQUEST_BASED_ON_FROM_DATE_AND_TO_DATE_BY_HOTEL;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THIS_API_IS_USED_TO_FETCH_ALL_THE_LAYOVER_REQUEST_BASED_ON_FROM_DATE_TO_END_DATE_AND_AIRLINE_ID_BY_AIRLINE;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THIS_API_IS_USED_TO_FETCH_ALL_THE_OPEN_LAYOVER_REQUEST_BASED_ON_FROM_DATE_TO_END_DATE_AND_AIRLINE_ID_BY_AIRLINE;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THIS_API_IS_USED_TO_RETRIEVE_ALL_HOTEL_DETAILS_INFORMATION_FROM_THE_AIRLINE_SIDE_BASED_ON_CAPACITY_AND_LOCATION;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THIS_API_IS_USED_TO_SAVE_AIRLINE_LAYOVER_REQUESTS;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THIS_API_IS_USED_TO_SAVE_LAYOVER_RESPONSE_BY_HOTEL;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THIS_API_IS_USED_TO_SAVE_PASSENGER_LIST_AND_LOC_BASED_ON_LAYOVER_REQUEST_ID_BY_AIRLINE;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THIS_API_IS_USED_TO_SAVE_THE_MEDIA_FILES;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.pojo.CommonPojo;
import com.tyss.layover.pojo.HotelDetailsPojo;
import com.tyss.layover.pojo.LayoverPojo;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.LayoverRequestResponseService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for LayoverRequestResponse Page . Here you find
 * GET, POST, PUT, DELETE controllers for the LayoverRequestResponse Page. Here
 * you can find the URL path for all the LayoverRequestResponse Page services.
 * 
 * 
 * @author Akram
 * @author Shrinivas Anant Tikare
 * @author Indrajit rana
 *
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/v1/layover/request-response")
//@SecurityRequirement(name = "layover-api")
public class LayoverRequestResponseController {

	/**
	 * This Field Invoke The Business Layer Methods
	 */
	private final LayoverRequestResponseService layoverRequestService;

	/**
	 * @param capacity
	 * @param location
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_RETRIEVE_ALL_HOTEL_DETAILS_INFORMATION_FROM_THE_AIRLINE_SIDE_BASED_ON_CAPACITY_AND_LOCATION)
	@GetMapping("/fetch-all-hotel-details/{capacity}/{location}")
	public ResponseEntity<LayoverResponseBody> fetchAllHotelDetails(@PathVariable("capacity") Integer capacity,
			@PathVariable("location") String location) {
		List<HotelDetailsPojo> hotelDetails = layoverRequestService.fetchAllHotelDetails(capacity, location);
		LayoverResponseBody layoverResponseBody = null;
		if (hotelDetails.isEmpty()) {
			layoverResponseBody = LayoverResponseBody.builder().isError(false)
					.message(THERE_ARE_NO_ROOMS_AVAILABLE_FOR_THE_REQUESTED_CAPACITY).build();
			log.info(THERE_ARE_NO_ROOMS_AVAILABLE_FOR_THE_REQUESTED_CAPACITY);
			return ResponseEntity.ok().body(layoverResponseBody);
		} else {
			layoverResponseBody = LayoverResponseBody.builder().isError(false).message(HOTEL_DETAILS_FETCH_SUCCESSFULLY)
					.data(hotelDetails).build();
			log.info(HOTEL_DETAILS_FETCH_SUCCESSFULLY);
		}
		return ResponseEntity.ok().body(layoverResponseBody);

	}// End Of The Fetch All HotelDetails

	/**
	 * @param locPath
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_SAVE_THE_MEDIA_FILES)
	@PostMapping(value = "/save-media-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<LayoverResponseBody> saveMediaFile(@RequestPart List<MultipartFile> locPath) {
		List<String> saveMultipartFile = layoverRequestService.saveMultipartFile(locPath);
		if (saveMultipartFile.isEmpty()) {
			LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(true)
					.message("SomeThing Went Wrong").build();
			return ResponseEntity.ok().body(layoverResponseBody);
		}
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message("Media File save Successfully").data(saveMultipartFile).build();
		return ResponseEntity.ok().body(layoverResponseBody);

	}

	/**
	 * @param multipartFiles
	 * @param data
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_SAVE_AIRLINE_LAYOVER_REQUESTS)
	@PostMapping("/save-request")
	public ResponseEntity<LayoverResponseBody> saveRequest(@RequestBody List<LayoverPojo> layoverPojos) {
		String saveRequest = layoverRequestService.saveRequest(layoverPojos);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false).message(saveRequest)
				.build();
		log.info(saveRequest);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The SaveRequest

	/**
	 * @param hoteldetailId
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_FETCH_ALL_THE_LAYOVER_REQUEST_BASED_ON_FROM_DATE_AND_TO_DATE_BY_HOTEL)
	@PostMapping("/fetch-all-request-by-hotelDetail")
	public ResponseEntity<LayoverResponseBody> fetchAllRequestByHotelDetail(
			@RequestBody CommonPojo layoverRequestPojo) {
		List<LayoverPojo> layoverRequestPojos = layoverRequestService.fetchAllLayoverRequestByHotel(layoverRequestPojo);
		LayoverResponseBody layoverResponseBody = null;
		if (layoverRequestPojos.isEmpty()) {
			layoverResponseBody = LayoverResponseBody.builder().isError(false)
					.message("There Are No Layover Requests For This Hotel").build();
		} else {
			layoverResponseBody = LayoverResponseBody.builder().isError(false)
					.message(LAYOVER_REQUEST_FETCH_SUCCESSFULLY).data(layoverRequestPojos).build();
			log.info(LAYOVER_REQUEST_FETCH_SUCCESSFULLY);
		}

		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The GetAllRequestByHotelDetailId Method

	/**
	 * @param layoverRequestPojo
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_SAVE_LAYOVER_RESPONSE_BY_HOTEL)
	@PostMapping("/save-response")
	public ResponseEntity<LayoverResponseBody> saveResponse(@RequestBody LayoverPojo layoverRequestPojo) {
		String saveRequest = layoverRequestService.saveLayoverResponse(layoverRequestPojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false).message(saveRequest)
				.build();
		log.info(saveRequest);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The saveResponse Method

	/**
	 * @param airlineId
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_FETCH_ALL_THE_ACCEPTED_LAYOVER_REQUEST_BASED_ON_FROM_DATE_TO_END_DATE_AND_AIRLINE_ID_BY_AIRLINE)
	@PostMapping("/fetch-all-accepted-request-by-airline")
	public ResponseEntity<LayoverResponseBody> fetchAllAcceptedRequestByAirline(
			@RequestBody CommonPojo layoverRequestPojo) {
		List<LayoverPojo> layoverRequestPojos = layoverRequestService
				.fetchAllAcceptedRequestByAirline(layoverRequestPojo);
		if (layoverRequestPojos.isEmpty()) {
			LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(true)
					.message(THERE_ARE_NO_ACCEPTED_LAYOVER_REQUESTS_FOR_THIS_AIRLINE).build();
			log.info(THERE_ARE_NO_ACCEPTED_LAYOVER_REQUESTS_FOR_THIS_AIRLINE);
			return ResponseEntity.ok().body(layoverResponseBody);
		}
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(LAYOVER_REQUEST_FETCH_SUCCESSFULLY).data(layoverRequestPojos).build();
		log.info(LAYOVER_REQUEST_FETCH_SUCCESSFULLY);
		return ResponseEntity.ok().body(layoverResponseBody);

	}// End Of The FetchAllAcceptedRequestBybAirlineId Method

	/**
	 * @param airlineId
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_FETCH_ALL_THE_OPEN_LAYOVER_REQUEST_BASED_ON_FROM_DATE_TO_END_DATE_AND_AIRLINE_ID_BY_AIRLINE)
	@PostMapping("/fetch-all-open-request-by-airline")
	public ResponseEntity<LayoverResponseBody> fetchAllOpenRequestByAirline(
			@RequestBody CommonPojo layoverRequestPojo) {
		List<LayoverPojo> layoverRequestPojos = layoverRequestService.fetchOpenRequestByAirline(layoverRequestPojo);
		if (layoverRequestPojos.isEmpty()) {
			LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(true)
					.message(THERE_ARE_NO_OPEN_LAYOVER_REQUESTS_FOR_THIS_AIRLINE).build();
			log.info(THERE_ARE_NO_OPEN_LAYOVER_REQUESTS_FOR_THIS_AIRLINE);
			return ResponseEntity.ok().body(layoverResponseBody);
		}
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(LAYOVER_REQUEST_FETCH_SUCCESSFULLY).data(layoverRequestPojos).build();
		log.info(LAYOVER_REQUEST_FETCH_SUCCESSFULLY);
		return ResponseEntity.ok().body(layoverResponseBody);

	}// End Of The FetchAllAcceptedRequestBybAirlineId Method

	/**
	 * @param passengerList
	 * @param loc
	 * @param layoverRequestId
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_SAVE_PASSENGER_LIST_AND_LOC_BASED_ON_LAYOVER_REQUEST_ID_BY_AIRLINE)
	@PutMapping(value = "/update-passengerList-and-loc/{layoverRequestId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<LayoverResponseBody> updatePassengerListAndLoc(@RequestPart MultipartFile passengerList,
			@RequestPart MultipartFile loc, @PathVariable Integer layoverRequestId) {
		String uploadPassengerListAndLoc = layoverRequestService.uploadPassengerListAndLoc(passengerList, loc,
				layoverRequestId);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(uploadPassengerListAndLoc).build();
		log.info(uploadPassengerListAndLoc);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The UpdatePassengerListAndLoc Method

	/**
	 * @param airlineId
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_FETCH_ALL_THE_LAYOVER_REQUEST_BASED_ON_FROM_DATE_TO_END_DATE_AND_AIRLINE_ID_BY_AIRLINE)
	@PostMapping("/fetch-all-request-by-airline")
	public ResponseEntity<LayoverResponseBody> fetchAllRequestBybAirlineId(@RequestBody CommonPojo layoverRequestPojo) {
		List<LayoverPojo> layoverRequestPojos = layoverRequestService.fetchAllRequestByAirline(layoverRequestPojo);
		LayoverResponseBody layoverResponseBody = null;
		if (layoverRequestPojos.isEmpty()) {
			layoverResponseBody = LayoverResponseBody.builder().isError(true)
					.message(THERE_ARE_NO_LAYOVER_REQUESTS_FOR_THIS_AIRLINE).build();
			log.info(THERE_ARE_NO_LAYOVER_REQUESTS_FOR_THIS_AIRLINE);
		} else {
			layoverResponseBody = LayoverResponseBody.builder().isError(false)
					.message(LAYOVER_REQUEST_FETCH_SUCCESSFULLY).data(layoverRequestPojos).build();
			log.info(LAYOVER_REQUEST_FETCH_SUCCESSFULLY);
		}

		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The FetchAllRequestBybAirlineId Method

}
