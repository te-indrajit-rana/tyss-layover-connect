package com.tyss.layover.service;

import java.util.Map;

import com.tyss.layover.pojo.OtpValidationPojo;

public interface LayoverAuthenticationService  {
	
	String authenticate(OtpValidationPojo otpValidationPojo);
	Map<String, Object> validateOtp(OtpValidationPojo otpValidationPojo);
	


}
