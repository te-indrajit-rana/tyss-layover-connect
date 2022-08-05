package com.tyss.layover.entity;

import static com.tyss.layover.constant.TransportationConstants.CONTACT_NUMBER_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.CONTACT_NUMBER_CAN_NOT_BE_NULL;
import static com.tyss.layover.constant.TransportationConstants.NAME_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_ADDRESS_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_COUNTRY_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_LOCATION_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_NAME_CAN_NOT_BE_BLANK;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_TITLE_CAN_NOT_BE_BLANK;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class Transportation implements Serializable {

	private static final long serialVersionUID = -188745028168424050L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transportation_id", unique = true, nullable = false, precision = 10)
	private Integer transportationId;

	@NotBlank(message = TRANSPORTATION_NAME_CAN_NOT_BE_BLANK)
	@Column(name = "transportation_name", length = 255)
	private String transportationName;

	@NotBlank(message = TRANSPORTATION_TITLE_CAN_NOT_BE_BLANK)
	@Column(name = "transportation_title", length = 255)
	private String transportationTitle;

	@NotBlank(message = TRANSPORTATION_ADDRESS_CAN_NOT_BE_BLANK)
	@Column(name = "transportation_address", length = 255)
	private String transportationAddress;

	@NotBlank(message = NAME_CAN_NOT_BE_BLANK)
	@Column(length = 255)
	private String name;

	@NotBlank(message = CONTACT_NUMBER_CAN_NOT_BE_BLANK)
	@NotNull(message = CONTACT_NUMBER_CAN_NOT_BE_NULL)
	@Column(name = "contact_number", unique = true, nullable = false, length = 45)
	private String contactNumber;

	@NotBlank(message = TRANSPORTATION_COUNTRY_CAN_NOT_BE_BLANK)
	@Column(length = 45)
	private String country;

	@NotBlank(message = TRANSPORTATION_LOCATION_CAN_NOT_BE_BLANK)
	@Column(length = 255)
	private String location;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime registrationDate;

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private UserDetails user;

}
