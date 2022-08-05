package com.tyss.layover.pojo;

import static com.tyss.layover.constant.TransportationConstants.CONTACT_NUMBER_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.CONTACT_NUMBER_CAN_NOT_BE_NULL;
import static com.tyss.layover.constant.TransportationConstants.NAME_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.PLEASE_ENTER_CORRECT_EMAIL_FORMAT;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_ADDRESS_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_COUNTRY_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_LOCATION_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_NAME_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_TITLE_CAN_NOT_BE_BLANK;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportationRequest {
	private Integer transportationId;
	@NotBlank(message = TRANSPORTATION_NAME_CAN_NOT_BE_BLANK)
	private String transportationName;

	@NotBlank(message = TRANSPORTATION_TITLE_CAN_NOT_BE_BLANK)
	private String transportationTitle;

	@NotBlank(message = TRANSPORTATION_ADDRESS_CAN_NOT_BE_BLANK)
	private String transportationAddress;

	@NotBlank(message = NAME_CAN_NOT_BE_BLANK)
	private String name;

	@NotBlank(message = CONTACT_NUMBER_CAN_NOT_BE_BLANK)
	@NotNull(message = CONTACT_NUMBER_CAN_NOT_BE_NULL)
	private String contactNumber;

	@NotBlank(message = TRANSPORTATION_COUNTRY_CAN_NOT_BE_BLANK)
	private String country;

	@NotBlank(message = TRANSPORTATION_LOCATION_CAN_NOT_BE_BLANK)
	private String location;

	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = PLEASE_ENTER_CORRECT_EMAIL_FORMAT)
	private String email;

	private String status;

	private String adminNote;

}
