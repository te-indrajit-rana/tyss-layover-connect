package com.tyss.layover.controller;

import static com.tyss.layover.constant.AirlineConstant.AIRLINE_LOGO_UPDATED_SUCCESSFULLY;
import static com.tyss.layover.constant.AirlineConstant.AIRLINE_REGISTRATION_SUCCESSFULLY;
import static com.tyss.layover.constant.AirlineConstant.AIRLINE_UPDATED_SUCCESSFULLY;
import static com.tyss.layover.constant.AirlineConstant.FETCH_AIRLINE_SUCCESSFULLY;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.pojo.AirlinePojo;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.response.AirlineDashbordCountResponse;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.AirlineService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Airline Page . Here you find GET, POST, PUT,
 * DELETE controllers for the Airline Page. Here you can find the URL path for
 * all the Airline Page services.
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
@RequestMapping("api/v1/layover/airline")
//@SecurityRequirement(name = "layover-api")
public class AirlineController {

	/**
	 * @This field is used to invoke business layer methods
	 */
	private final AirlineService airlineService;

	/**
	 * @param airlinePojo
	 * @return ResponseEntity
	 */
	@Operation(summary = "This Api Is Used To Save Airline Information")
	@PostMapping("/registration")
	public ResponseEntity<LayoverResponseBody> saveAirline(@Valid @RequestBody AirlinePojo airlinePojo) {
		AirlinePojo saveAirline = airlineService.saveAirline(airlinePojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(AIRLINE_REGISTRATION_SUCCESSFULLY).data(saveAirline).build();
		log.info(AIRLINE_REGISTRATION_SUCCESSFULLY);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The save Details

	/**
	 * @param id
	 * @return ResponseEntity
	 */
	@Operation(summary = "This API Is Used To Fetch Airline Information Based On Airline ID.")
//	@PreAuthorize(value = HAS_ANY_AUTHORITY_ROLE_ADMIN_ROLE_AIRLINE)
	@GetMapping("/fetch-airline/{id}")
	public ResponseEntity<LayoverResponseBody> fetchById(@PathVariable("id") Integer id) {
		AirlinePojo saveAirline = airlineService.fetchById(id);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(FETCH_AIRLINE_SUCCESSFULLY).data(saveAirline).build();
		log.info(FETCH_AIRLINE_SUCCESSFULLY);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The FetchById Method

	/**
	 * @param airlinePojo
	 * @return ResponseEntity
	 */
	@Operation(summary = "This API Is Used To Update Airline Information")
//	@PreAuthorize(value = HAS_ANY_AUTHORITY_ROLE_ADMIN_ROLE_AIRLINE)
	@PutMapping("/update-airline")
	public ResponseEntity<LayoverResponseBody> updateAirlineDetails(@RequestBody AirlinePojo airlinePojo) {
		AirlinePojo airlineDetails = airlineService.updateAirlineDetails(airlinePojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(AIRLINE_UPDATED_SUCCESSFULLY).data(airlineDetails).build();
		log.info(AIRLINE_UPDATED_SUCCESSFULLY);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The Update Airline

	/**
	 * @param id
	 * @return ResponseEntity
	 */
//	@PreAuthorize(value = HAS_ANY_AUTHORITY_ROLE_ADMIN_ROLE_AIRLINE)
	@Operation(summary = "This API Is Used To Delete Airline Information Based On Airline ID")
	@DeleteMapping("/delete-airline")
	public ResponseEntity<LayoverResponseBody> deleteById(@RequestBody UserDeletePojo userDeletePojo) {
		String deleteAirline = airlineService.deleteAirline(userDeletePojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false).message(deleteAirline)
				.build();
		log.info(deleteAirline);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The Delete Airline

	/**
	 * @param id
	 * @param file
	 * @return ResponseEntity
	 */
	@Operation(summary = "Based on the airline ID, this API is Used To Save The Airline Logo and General Loc.")
//	@PreAuthorize(value = HAS_ANY_AUTHORITY_ROLE_ADMIN_ROLE_AIRLINE)
	@PutMapping(value = "/update-airline-logo/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<LayoverResponseBody> updateAirlineLogo(@PathVariable("id") Integer id,
			@RequestPart(required = false) MultipartFile logofile,
			@RequestPart(required = false) MultipartFile generalLoc) {
		AirlinePojo airlineDetails = airlineService.updateAirlineLogo(logofile, generalLoc, id);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(AIRLINE_LOGO_UPDATED_SUCCESSFULLY).data(airlineDetails).build();
		log.info(AIRLINE_LOGO_UPDATED_SUCCESSFULLY);
		return ResponseEntity.ok().body(layoverResponseBody);
	}// End Of The Update Airline Logo and generalLoc

	@GetMapping("/count-airline-dashboard/{airlineId}")
	public ResponseEntity<LayoverResponseBody> airlineCount(@PathVariable Integer airlineId) {
		AirlineDashbordCountResponse response = airlineService.airlineCount(airlineId);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message("Get Count Successfully").data(response).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

}
