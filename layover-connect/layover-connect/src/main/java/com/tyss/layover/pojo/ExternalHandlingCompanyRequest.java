package com.tyss.layover.pojo;

import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.CONTACT_NUMBER_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.CONTACT_NUMBER_CAN_NOT_BE_NULL;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERNAL_HANDLING_COMPANY_ADDRESS_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERNAL_HANDLING_COMPANY_COUNTRY_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERNAL_HANDLING_COMPANY_LOCATION_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERNAL_HANDLING_COMPANY_NAME_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERNAL_HANDLING_COMPANY_TITLE_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.HANDLING_AIRLINES_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.NAME_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.PLEASE_ENTER_CORRECT_EMAIL_FORMAT;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalHandlingCompanyRequest {

	private Integer externalHandlingCompanyId;
	@NotBlank(message = EXTERNAL_HANDLING_COMPANY_NAME_CAN_NOT_BE_BLANK)
	private String externalHandlingCompanyName;

	@NotBlank(message = EXTERNAL_HANDLING_COMPANY_TITLE_CAN_NOT_BE_BLANK)
	private String externalHandlingCompanyTitle;

	@NotBlank(message = EXTERNAL_HANDLING_COMPANY_ADDRESS_CAN_NOT_BE_BLANK)
	private String externalHandlingCompanyAddress;

	@NotBlank(message = NAME_CAN_NOT_BE_BLANK)
	private String name;

	@NotBlank(message = CONTACT_NUMBER_CAN_NOT_BE_BLANK)
	@NotNull(message = CONTACT_NUMBER_CAN_NOT_BE_NULL)
	private String contactNumber;

	@NotNull(message = HANDLING_AIRLINES_CAN_NOT_BE_BLANK)
	private List<String> handlingAirlines;

	@NotBlank(message = EXTERNAL_HANDLING_COMPANY_COUNTRY_CAN_NOT_BE_BLANK)
	private String country;

	@NotBlank(message = EXTERNAL_HANDLING_COMPANY_LOCATION_CAN_NOT_BE_BLANK)
	private String location;

	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = PLEASE_ENTER_CORRECT_EMAIL_FORMAT)
	private String email;

	private String status;
	
	private String adminNote;
}
