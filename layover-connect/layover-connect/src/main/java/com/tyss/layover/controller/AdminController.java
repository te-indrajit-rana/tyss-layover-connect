package com.tyss.layover.controller;

import static com.tyss.layover.constant.AdminConstants.GET_ALL_THE_LATEST_LAYOVER;
import static com.tyss.layover.constant.AdminConstants.GET_ALL_THE_LAYOVER_COUNT_BASED_MONTH;
import static com.tyss.layover.constant.AdminConstants.GET_ALL_THE_REGISTER_USER;
import static com.tyss.layover.constant.AdminConstants.LATEST_REGISTERATIONS_ARE_FETCHED;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.layover.response.AdminActionResponse;
import com.tyss.layover.response.AdminCountResponse;
import com.tyss.layover.response.AdminLatestLayoversResponse;
import com.tyss.layover.response.AdminLatestRegistrationResponse;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/v1/layover/admin")
//@SecurityRequirement(name = "layover-api")
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/count")
	public ResponseEntity<LayoverResponseBody> adminCountResponse() {
		AdminCountResponse adminCountResponse = adminService.adminCountResponse();
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.data(adminCountResponse).build();
		return ResponseEntity.ok().body(layoverResponseBody);

	}

	@GetMapping("/latest-registration")
	public ResponseEntity<LayoverResponseBody> latestRegister(String userType) {
		List<AdminLatestRegistrationResponse> adminLatestRegistrationResponses = adminService
				.adminLatestRegistrationResponses(userType);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(LATEST_REGISTERATIONS_ARE_FETCHED).data(adminLatestRegistrationResponses).build();
		return ResponseEntity.ok().body(layoverResponseBody);

	}

	@GetMapping("/get-all-the-register-user")
	public ResponseEntity<LayoverResponseBody> getAllTheRegisterUser(String userType) {
		List<AdminActionResponse> allTheRegisteredUserDetails = adminService.getAllTheRegisteredUserDetails(userType);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(GET_ALL_THE_REGISTER_USER).data(allTheRegisteredUserDetails).build();
		return ResponseEntity.ok().body(layoverResponseBody);

	}

	@GetMapping("/get-all-the-latest-layovers")
	public ResponseEntity<LayoverResponseBody> getAllThelatestLayovers() {
		List<AdminLatestLayoversResponse> adminLatestLayoversResponses = adminService.adminLatestLayoversResponses();
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(GET_ALL_THE_LATEST_LAYOVER).data(adminLatestLayoversResponses).build();
		return ResponseEntity.ok().body(layoverResponseBody);

	}

	@GetMapping("/get-layover-count-monthwise/{status}/{year}")
	public ResponseEntity<LayoverResponseBody> getAllTheTotalLayoverCountBasedOnMonth(
			@PathVariable("status") String status, @PathVariable("year") Integer year) {
		Map<String, Long> totalLayoverCountBasedOnMonth = adminService.totalLayoverCountBasedOnMonth(status, year);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(GET_ALL_THE_LAYOVER_COUNT_BASED_MONTH).data(totalLayoverCountBasedOnMonth).build();
		return ResponseEntity.ok().body(layoverResponseBody);

	}

	@GetMapping("/get-layover-payment-monthwise/{status}/{year}")
	public ResponseEntity<LayoverResponseBody> getAllTheTotalPaymetGenerateBasedOnMonth(
			@PathVariable("status") String status, @PathVariable("year") Integer year) {
		SortedMap<String, Double> totalAmountGeneratedBasedOnMonth = adminService
				.totalAmountGeneratedBasedOnMonth(status, year);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message("Total Payment Fetched Successfully").data(totalAmountGeneratedBasedOnMonth).build();
		return ResponseEntity.ok().body(layoverResponseBody);

	}

}
