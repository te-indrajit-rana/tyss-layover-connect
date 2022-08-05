package com.tyss.layover.entity;

import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.CONTACT_NUMBER_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.CONTACT_NUMBER_CAN_NOT_BE_NULL;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERNAL_HANDLING_COMPANY_ADDRESS_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERNAL_HANDLING_COMPANY_COUNTRY_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERNAL_HANDLING_COMPANY_LOCATION_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERNAL_HANDLING_COMPANY_NAME_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERNAL_HANDLING_COMPANY_TITLE_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.HANDLING_AIRLINES_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.NAME_CAN_NOT_BE_BLANK;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.tyss.layover.converter.StringListConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = Include.NON_DEFAULT)
public class ExternalHandlingCompany implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "external_handling_company_id", unique = true, nullable = false, precision = 10)
	private Integer externalHandlingCompanyId;

	@NotBlank(message = EXTERNAL_HANDLING_COMPANY_NAME_CAN_NOT_BE_BLANK)
	@Column(name = "external_handling_company_name", length = 255)
	private String externalHandlingCompanyName;

	@NotBlank(message = EXTERNAL_HANDLING_COMPANY_TITLE_CAN_NOT_BE_BLANK)
	@Column(name = "external_handling_company_title", length = 255)
	private String externalHandlingCompanyTitle;

	@NotBlank(message = EXTERNAL_HANDLING_COMPANY_ADDRESS_CAN_NOT_BE_BLANK)
	@Column(name = "external_handling_company_address", length = 255)
	private String externalHandlingCompanyAddress;

	@NotBlank(message = NAME_CAN_NOT_BE_BLANK)
	@Column(length = 255)
	private String name;

	@NotBlank(message = CONTACT_NUMBER_CAN_NOT_BE_BLANK)
	@NotNull(message = CONTACT_NUMBER_CAN_NOT_BE_NULL)
	@Column(name = "contact_number",unique = true, nullable = false, length = 255)
	private String contactNumber;

	@NotNull(message = HANDLING_AIRLINES_CAN_NOT_BE_BLANK)
	@Column(name = "handling_airlines", length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> handlingAirlines;

	@NotBlank(message = EXTERNAL_HANDLING_COMPANY_COUNTRY_CAN_NOT_BE_BLANK)
	@Column(length = 45)
	private String country;

	@NotBlank(message = EXTERNAL_HANDLING_COMPANY_LOCATION_CAN_NOT_BE_BLANK)
	@Column(length = 255)
	private String location;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss",timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime registrationDate;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private UserDetails user;

}
