package com.tyss.layover.pojo;

import static com.tyss.layover.constant.CareerConstants.PLEASE_ENTER_ADDITIONAL_INFORMATION;
import static com.tyss.layover.constant.CareerConstants.PLEASE_ENTER_CURRENT_COMPANY_NAME;
import static com.tyss.layover.constant.CareerConstants.PLEASE_ENTER_YOUR_EMAIL_ADDRESS;
import static com.tyss.layover.constant.CareerConstants.PLEASE_ENTER_YOUR_NAME;
import static com.tyss.layover.constant.CareerConstants.PLEASE_ENTER_YOUR_PHONE_NUMBER;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CareersRequest {


	@NotBlank(message = PLEASE_ENTER_YOUR_NAME)
	private String fullName;

	@Email
	@NotBlank(message = PLEASE_ENTER_YOUR_EMAIL_ADDRESS)
	private String emailId;

	@NotBlank(message = PLEASE_ENTER_YOUR_PHONE_NUMBER)
	private String phone;

	@NotBlank(message = PLEASE_ENTER_CURRENT_COMPANY_NAME)
	private String currentCompany;

	@NotBlank(message = PLEASE_ENTER_ADDITIONAL_INFORMATION)
	private String additionalInformation;

}
