// Generated with g9.

package com.tyss.layover.entity;

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
public class LayoverRequest implements Serializable {

	private static final long serialVersionUID = 1247427430503586996L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "layover_request_id", unique = true, nullable = false, precision = 10)
	private int layoverRequestId;
	@Column(name = "requested_single_rooms", precision = 10)
	private int requestedSingleRooms;
	@Column(name = "requested_double_rooms", precision = 10)
	private int requestedDoubleRooms;
	@Column(name = "requested_accessible_rooms", precision = 10)
	private int requestedAccessibleRooms;
	@Column(name = "requested_count", precision = 10)
	private int requestedCount;
	@Column(name = "check_in_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime checkIntime;
	@Column(name = "check_out_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime checkOutTime;
	@Column(name = "next_flight_dep_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime nextFlightDepTime;
	@Column(name = "requested_meals", length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> requestedMeals;
	@Column(name = "flight_number", length = 255)
	private String flightNumber;
	@Column(name = "special_request", length = 255)
	private String specialRequest;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime timestamp;
	@Column(length = 255)
	private String locPath;
	@Column(name = "passenger_list", length = 255)
	private String passengerListPath;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "airline_id")
	private Airline airline;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_details_id")
	private HotelDetails hotelDetails;

}
