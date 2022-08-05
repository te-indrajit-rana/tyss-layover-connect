package com.tyss.layover.pojo;

import static com.tyss.layover.constant.ContactUsConstants.PLEASE_ENTER_PROJECT_DESCRIPTION;
import static com.tyss.layover.constant.ContactUsConstants.PLEASE_ENTER_THE_EMAIL_ADRESS;
import static com.tyss.layover.constant.ContactUsConstants.PLEASE_ENTER_THE_NAME;
import static com.tyss.layover.constant.ContactUsConstants.PLEASE_ENTER_YOUR_PHONE_NUMBER;

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
public class ContactUsRequest {


	@NotBlank(message = PLEASE_ENTER_THE_NAME)
	private String name;

	@Email
	@NotBlank(message = PLEASE_ENTER_THE_EMAIL_ADRESS)
	private String email;

	@NotBlank(message = PLEASE_ENTER_YOUR_PHONE_NUMBER)
	private String phone;

	@NotBlank(message = PLEASE_ENTER_PROJECT_DESCRIPTION)
	private String projectDescription;

}
