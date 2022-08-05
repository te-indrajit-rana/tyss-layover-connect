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

import com.tyss.layover.pojo.TransportationRequest;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.TransportationService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*" )
@RestController
@RequiredArgsConstructor
//@SecurityRequirement(name = "layover-api")
@RequestMapping("api/v1/layover/transportation")
public class TransportationController {

	private final TransportationService transportationService;

	@PostMapping("/registration")
	public ResponseEntity<LayoverResponseBody> registerTransportationRegistration(
			@Valid @RequestBody TransportationRequest transportationRequest) {
		String registerTransportationRegistration = transportationService
				.registerTransportationRegistration(transportationRequest);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(registerTransportationRegistration).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

//	@PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_TRANSPORTATION')")
	@PutMapping("/update-details-by-admin")
	public ResponseEntity<LayoverResponseBody> updateTransportationDetailsByAdmin(
			@Valid @RequestBody TransportationRequest transportationRequest) {
		String updateTransportationDetailsByAdmin = transportationService
				.updateTransportationDetailsByAdmin(transportationRequest);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(updateTransportationDetailsByAdmin).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

//	@PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_TRANSPORTATION')")
	@DeleteMapping("/delete-details-by-admin")
	public ResponseEntity<LayoverResponseBody> deleteTransportationDetailsByAdmin(
			@RequestBody UserDeletePojo userDeletePojo) {
		String deleteTransportationDetailsByAdmin = transportationService
				.deleteTransportationDetailsByAdmin(userDeletePojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(deleteTransportationDetailsByAdmin).build();
		return ResponseEntity.ok().body(layoverResponseBody);

	}
}
