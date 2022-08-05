package com.tyss.layover.entity;

import static com.tyss.layover.constant.AirportConstants.AIRPORT_ADDRESS_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_COUNTRY_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_LOCATION_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_NAME_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_TITLE_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.CONTACT_NUMBER_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.CONTACT_NUMBER_CAN_NOT_BE_NULL;
import static com.tyss.layover.constant.AirportConstants.HANDLING_AIRLINES_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.AirportConstants.NAME_CAN_NOT_BE_BLANK;

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
public class Airport implements Serializable {

	private static final long serialVersionUID = -2063721604457610895L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "airport_id", unique = true, nullable = false, precision = 10)
	private Integer airportId;

	@NotBlank(message = AIRPORT_NAME_CAN_NOT_BE_BLANK)
	@Column(name = "airport_name", length = 255)
	private String airportName;

	@NotBlank(message = AIRPORT_TITLE_CAN_NOT_BE_BLANK)
	@Column(name = "airport_title", length = 255)
	private String airportTitle;

	@NotBlank(message = AIRPORT_ADDRESS_CAN_NOT_BE_BLANK)
	@Column(name = "airport_address", length = 255)
	private String airportAddress;

	@NotBlank(message = NAME_CAN_NOT_BE_BLANK)
	@Column(length = 255)
	private String name;

	@NotBlank(message = CONTACT_NUMBER_CAN_NOT_BE_BLANK)
	@NotNull(message = CONTACT_NUMBER_CAN_NOT_BE_NULL)
	@Column(name = "contact_number",unique = true, nullable = false, length = 45)
	private String contactNumber;

	@NotNull(message = HANDLING_AIRLINES_CAN_NOT_BE_BLANK)
	@Column(name = "handling_airlines", length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> handlingAirlines;

	@NotBlank(message = AIRPORT_COUNTRY_CAN_NOT_BE_BLANK)
	@Column(length = 255)
	private String country;

	@NotBlank(message = AIRPORT_LOCATION_CAN_NOT_BE_BLANK)
	@Column(length = 255)
	private String location;

	@Column(name = "logo_path", length = 255)
	private String logoPath;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss",timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime registrationDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private UserDetails user;

}
