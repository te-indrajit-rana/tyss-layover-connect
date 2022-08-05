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
public class PaymentCommissionPojo {
	private int paymentId;
	private String airlineStatus;
	private String hotelAcknowledgement;
	private String hotelStatus;
	private String invoicePath;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime timestamp;
	private Integer layoverResponseId;
	private Integer layoverRequestId;
	
	
	//commission
	private int commissionId;
	private BigDecimal commissionAmount;
	private String commissionInvoicePath;
	private String commissionStatus;
	
	//ExtraFields
	private BigDecimal totalAmount;
	private Integer airlineId;
	private Integer hotelDetailsId;
	private String flightNumber;
	private String airlineName;
	private String hotelName;
	private String location;
	private String status;
	private List<String> acceptedMeals;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime checkIntime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime checkOutTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime nextFlightDepTime;
	private String locPath;
	private String passengerListPath;
	private int acceptedCount;
	//Pricess
	private BigDecimal beveragePrice;
	private BigDecimal breakfastPrice;
	private BigDecimal lunchPrice;
	private BigDecimal dinnerPrice;
	private BigDecimal singleRoomPrice;
	private BigDecimal doubleRoomPrice;
	private BigDecimal accessibleRoomPrice;
	
	
}
