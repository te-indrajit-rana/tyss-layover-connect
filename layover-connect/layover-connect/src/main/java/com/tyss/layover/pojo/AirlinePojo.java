package com.tyss.layover.pojo;

import static com.tyss.layover.constant.AirlineConstant.AIRLINE_ADDRESS_CANNOT_BE_NULL;
import static com.tyss.layover.constant.AirlineConstant.AIRLINE_ADRESS_CANNOT_BE_BLANK_OR_EMPTY;
import static com.tyss.layover.constant.AirlineConstant.AIRLINE_NAME_CANNOT_BE_BLANK_OR_EMPTY;
import static com.tyss.layover.constant.AirlineConstant.AIRLINE_NAME_CANNOT_BE_NULL;
import static com.tyss.layover.constant.AirlineConstant.AIRLINE_PERSON_NAME_CANNOT_BE_BLANK_OR_EMPTY;
import static com.tyss.layover.constant.AirlineConstant.AIRLINE_PERSON_NAME_CANNOT_BE_NULL;
import static com.tyss.layover.constant.AirlineConstant.AIRLINE_TITLE_CANNOT_BE_BLANK_OR_EMPTY;
import static com.tyss.layover.constant.AirlineConstant.AIRLINE_TITLE_CANNOT_BE_NULL;
import static com.tyss.layover.constant.AirlineConstant.COUNTRY_AME_CANNOT_BE_NULL;
import static com.tyss.layover.constant.AirlineConstant.COUNTRY_NAME_CANNOT_BE_BLANK_OR_EMPTY;
import static com.tyss.layover.constant.AirlineConstant.INVALID_EMAIL_ADDRESS;
import static com.tyss.layover.constant.AirlineConstant.LOCATION_AME_CANNOT_BE_NULL;
import static com.tyss.layover.constant.AirlineConstant.LOCATION_NAME_CANNOT_BE_BLANK_OR_EMPTY;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonInclude(value = Include.NON_DEFAULT)
public class AirlinePojo {
	
	private int airlineId;
	@NotNull(message = AIRLINE_NAME_CANNOT_BE_NULL)
	@NotBlank(message = AIRLINE_NAME_CANNOT_BE_BLANK_OR_EMPTY)
	private String airlineName;
	@NotNull(message = AIRLINE_TITLE_CANNOT_BE_NULL)
	@NotBlank(message = AIRLINE_TITLE_CANNOT_BE_BLANK_OR_EMPTY)
	private String airlineTitle;
	@NotNull(message = AIRLINE_ADDRESS_CANNOT_BE_NULL)
	@NotBlank(message = AIRLINE_ADRESS_CANNOT_BE_BLANK_OR_EMPTY)
	private String airlineAddress;
	@NotNull(message = AIRLINE_PERSON_NAME_CANNOT_BE_NULL)
	@NotBlank(message = AIRLINE_PERSON_NAME_CANNOT_BE_BLANK_OR_EMPTY)
	private String name;
//	@Range(min = 6, max = 20, message = AIRLINE_CONTACT_NUMBER_SHOULD_BE_IN_THE_RANGE_OF_6_TO_20)
//	@Pattern(regexp = "^[0-9]{20}",message =  AIRLINE_CONTACT_NUMBER_SHOULD_BE_IN_THE_RANGE_OF_6_TO_20)
//	@Pattern(regexp = "^\\+[0-9]{1,3}\\ [0-9]{4,14}(?:x.+)?$",message = PLEASE_ENTER_THE_VALID_PHONE_NUMBER)
	private String contactNumber;
	@NotNull(message = COUNTRY_AME_CANNOT_BE_NULL)
	@NotBlank(message = COUNTRY_NAME_CANNOT_BE_BLANK_OR_EMPTY)
	private String country;
	@NotNull(message = LOCATION_AME_CANNOT_BE_NULL)
	@NotBlank(message = LOCATION_NAME_CANNOT_BE_BLANK_OR_EMPTY)
	private String location;
	private String logoPath;
	private String generalLocPath;
	@Email(message = INVALID_EMAIL_ADDRESS)
//	@Pattern(regexp = "\"^\\\\w+([.-]?\\\\w+)@\\\\w+([.-]?\\\\w+)(\\\\.\\\\w{2,3})+$\"",message = INVALID_EMAIL_ADDRESS)
	private String email;
	private String userType;
	private Boolean isDeleted;
	private Boolean isBlocked;
	private String status;
	private String adminNote;
}
