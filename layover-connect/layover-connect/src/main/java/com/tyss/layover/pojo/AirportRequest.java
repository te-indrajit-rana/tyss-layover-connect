package com.tyss.layover.pojo;

import static com.tyss.layover.constant.AirportConstants.AIRPORT_ADDRESS_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_COUNTRY_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_LOCATION_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_NAME_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_TITLE_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.CONTACT_NUMBER_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.CONTACT_NUMBER_CAN_NOT_BE_NULL;
import static com.tyss.layover.constant.AirportConstants.HANDLING_AIRLINES_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.PLEASE_ENTER_CORRECT_EMAIL_FORMAT;

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
public class AirportRequest {

	private Integer airportId;
	@NotBlank(message = AIRPORT_NAME_CAN_NOT_BE_BLANK)
	private String airportName;

	@NotBlank(message = AIRPORT_TITLE_CAN_NOT_BE_BLANK)
	private String airportTitle;

	@NotBlank(message = AIRPORT_ADDRESS_CAN_NOT_BE_BLANK)
	private String airportAddress;

	@NotBlank(message = "Name can not be blank")
	private String name;

	@NotBlank(message = CONTACT_NUMBER_CAN_NOT_BE_BLANK)
	@NotNull(message = CONTACT_NUMBER_CAN_NOT_BE_NULL)
	private String contactNumber;

	@NotNull(message = HANDLING_AIRLINES_CAN_NOT_BE_BLANK)
	private List<String> handlingAirlines;

	@NotBlank(message = AIRPORT_COUNTRY_CAN_NOT_BE_BLANK)
	private String country;

	@NotBlank(message = AIRPORT_LOCATION_CAN_NOT_BE_BLANK)
	private String location;

	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = PLEASE_ENTER_CORRECT_EMAIL_FORMAT)
	private String email;

	private String status;
	
	private String adminNote;

}
