package com.tyss.layover.controller;

import static com.tyss.layover.constant.LayoverConstant.BILLING_USER_FETCH_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverConstant.BILLING_USER_SAVE_SUCCESSFULLY;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.layover.pojo.BillingUserPojo;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.BillingUserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for BillingUser Page . Here you find GET, POST,
 * PUT, DELETE controllers for the BillingUser Page. Here you can find the URL
 * path for all the BillingUser Page services.
 * 
 * 
 * @author Akram
 * @author Shrinivas Anant Tikare
 * @author Indrajit rana
 *
 */
@Slf4j
@CrossOrigin(origins = "*" )
@RestController
@RequestMapping("api/v1/billing-user")
@RequiredArgsConstructor
//@SecurityRequirement(name = "layover-api")
public class BillingUserController {

	/**
	 * This Field Is Invoke The Business Layer Methods
	 */
	private final BillingUserService billingUserService;

	/**
	 * @param billingUserPojo
	 * @return ResponseEntity
	 */
	@Operation(summary = "This Api Is Used To Save The Billing User Based On The Airline Id")
	@PostMapping("/save")
//	@PreAuthorize("hasRole('ROLE_AIRLINE')")
	public ResponseEntity<LayoverResponseBody> saveBillingUser(@RequestBody BillingUserPojo billingUserPojo) {
		BillingUserPojo saveBillingUser = billingUserService.saveBillingUser(billingUserPojo);
		LayoverResponseBody layoverResponse = LayoverResponseBody.builder().isError(false)
				.message(BILLING_USER_SAVE_SUCCESSFULLY).data(saveBillingUser).build();
		log.info(BILLING_USER_SAVE_SUCCESSFULLY);
		return ResponseEntity.ok().body(layoverResponse);
	}

	/**
	 * @param id
	 * @return ResponseEntity
	 */
	@Operation(summary = "This Api Is Used To Fetch Billing User Details By Airline Id")
	@GetMapping("/fetch-billing-user/{airline_id}")
//	@PreAuthorize("hasRole('ROLE_AIRLINE')")
	public ResponseEntity<LayoverResponseBody> fetchBillingUser(@PathVariable("airline_id") Integer id) {
		BillingUserPojo saveBillingUser = billingUserService.fetchBillingUserByAirlineId(id);
		LayoverResponseBody layoverResponse = LayoverResponseBody.builder().isError(false)
				.message(BILLING_USER_FETCH_SUCCESSFULLY).data(saveBillingUser).build();
		log.info(BILLING_USER_FETCH_SUCCESSFULLY);
		return ResponseEntity.ok().body(layoverResponse);
	}

	/**
	 * @param billingUserPojo
	 * @return ResponseEntity
	 */
	@Operation(summary = "This Api Is Used To Update The Billing User")
	@PutMapping("/update")
//	@PreAuthorize("hasRole('ROLE_AIRLINE')")
	public ResponseEntity<LayoverResponseBody> updateBillingUser(@RequestBody BillingUserPojo billingUserPojo) {
		String billingUser = billingUserService.updateBillingUser(billingUserPojo);
		LayoverResponseBody layoverResponse = LayoverResponseBody.builder().isError(false).message(billingUser).build();
		log.info(billingUser);
		return ResponseEntity.ok().body(layoverResponse);
	}

	/**
	 * @param id
	 * @return ResponseEntity
	 */
	@Operation(summary = "This Api Is Used To Delete The Billing User Information")
	@DeleteMapping("/delete/{id}")
//	@PreAuthorize("hasRole('ROLE_AIRLINE')")
	public ResponseEntity<LayoverResponseBody> deleteBillingUser(@PathVariable("id") Integer id) {
		String billingUser = billingUserService.deleteBillingUser(id);
		LayoverResponseBody layoverResponse = LayoverResponseBody.builder().isError(false).message(billingUser).build();
		log.info(billingUser);
		return ResponseEntity.ok().body(layoverResponse);
	}

}
