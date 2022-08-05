package com.tyss.layover.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = Include.NON_DEFAULT)
public class AirlineDashbordCountResponse {

	private Long totalLayover;

	private Long totalLayoverAccepted;

	private Long totalLayoverDeclined;
	
	private Long totalLayoverCancelled;

//	private BigDecimal amountReceivedByHotel;

	private BigDecimal openPayments;

	private BigDecimal closedPayments;
	
	private BigDecimal totalPayment;
	
	
}
