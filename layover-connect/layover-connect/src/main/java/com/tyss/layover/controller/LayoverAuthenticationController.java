package com.tyss.layover.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.layover.constant.LayoverConstant;
import com.tyss.layover.pojo.OtpValidationPojo;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.LayoverAuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

/**
 * This is a controller class for Authentication Page . Here you find POST
 * controllers for the Authentication Page. Here you can find the URL path for
 * all the Authentication Page services.
 * 
 * 
 * @author Akram
 * @author Shrinivas Anant Tikare
 * @author Indrajit rana
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/layover/auth")
//@SecurityRequirement(name = "layover-api")
public class LayoverAuthenticationController {

	/**
	 * This Field Invoke The Business Layer Methods
	 */
	private final LayoverAuthenticationService authenticationServiceImpl;

	/**
	 * @param otpValidationPojo.
	 * @return ResponseEntity.
	 */
	@Operation(summary = "The OTP Is Sent To The Registered Email Address Using This Api.")
	@PostMapping("/get-otp")
	public ResponseEntity<LayoverResponseBody> authenticate(@RequestBody OtpValidationPojo otpValidationPojo) {
		String authenticate = authenticationServiceImpl.authenticate(otpValidationPojo);
		return ResponseEntity.ok().body(LayoverResponseBody.builder().isError(false).message(authenticate).build());
	}

	/**
	 * @param otpValidationPojo
	 * @return ResponseEntity
	 */
	@Operation(summary = "The OTP Is Validated Using This Api. ")
	@PostMapping("/validate-otp")
	public ResponseEntity<LayoverResponseBody> validateOtp(@RequestBody OtpValidationPojo otpValidationPojo) {
		Map<String, Object> validateOtp = authenticationServiceImpl.validateOtp(otpValidationPojo);
		return ResponseEntity.ok().body(LayoverResponseBody.builder().isError(false)
				.message(LayoverConstant.ACCESS_TOKEN_GENERATED_SUCCESSFULLY).data(validateOtp).build());

	}

}
