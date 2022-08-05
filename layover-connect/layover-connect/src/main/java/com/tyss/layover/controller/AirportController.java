package com.tyss.layover.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.layover.pojo.AirportRequest;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.AirportService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*" )
@RestController
@RequiredArgsConstructor
//@SecurityRequirement(name = "layover-api")
@RequestMapping("api/v1/layover/airpot")
public class AirportController {

	private final AirportService airportService;

	@PostMapping("/registration")
	public ResponseEntity<LayoverResponseBody> airportRegistration(@Valid @RequestBody AirportRequest airportRequest) {
		String registerAirport = airportService.registerAirport(airportRequest);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(registerAirport).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

//	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_AIRPORT')")
	@PutMapping("/update-details-by-admin")
	public ResponseEntity<LayoverResponseBody> updateAirportDetailsByAdmin(
			@Valid @RequestBody AirportRequest airportRequest) {
		String updateAirportDetails = airportService.updateAirportDetailsByAdmin(airportRequest);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(updateAirportDetails).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

//	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_AIRPORT')")
	@DeleteMapping("/delete-details-by-admin")
	public ResponseEntity<LayoverResponseBody> deleteAirportDetailsByAdmin(@RequestBody UserDeletePojo userDeletePojo) {
		String deleteAirportDetailsByAdmin = airportService.deleteAirportDetailsByAdmin(userDeletePojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(deleteAirportDetailsByAdmin).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

}
