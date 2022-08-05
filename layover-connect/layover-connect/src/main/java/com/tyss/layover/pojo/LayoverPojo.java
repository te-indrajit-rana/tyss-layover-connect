package com.tyss.layover.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LayoverPojo {

	private int layoverRequestId;
	private int requestedSingleRooms;
	private BigDecimal singleRoomPrice;
	private int requestedDoubleRooms;
	private BigDecimal doubleRoomPrice;
	private int requestedAccessibleRooms;
	private BigDecimal accessibleRoomPrice;
	private int requestedCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime checkIntime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime checkOutTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime nextFlightDepTime;
	private List<String> requestedMeals;
	private String flightNumber;

	private String specialRequest;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime timestamp;

	private String locPath;

	private String passengerListPath;
	private String airlineName;
	private Integer airlineId;
	private Integer hotelDetailsId;

	private String hotelName;

	// Layover Response Details
	private int layoverResponseId;
	private int acceptedCount;
	private int acceptedSingleRooms;
	private int acceptedDoubleRooms;
	private int acceptedAccessibleRooms;
	private List<String> acceptedMeals;
	private String status;
	private String responseNote;
	// Accomendation price
	private BigDecimal beveragePrice;
	private BigDecimal breakfastPrice;
	private BigDecimal lunchPrice;
	private BigDecimal dinnerPrice;
	private BigDecimal totalAmount;

}
