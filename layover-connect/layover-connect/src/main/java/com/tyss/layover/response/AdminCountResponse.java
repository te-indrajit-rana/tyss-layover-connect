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
public class AdminCountResponse {

	private Long totalAirportRegister;

	private Long totalExternHandlingCompanyRegister;

	private Long totalTransportationRegister;

	private Long totalHotelRegister;

	private Long totalAirlineRegister;

	private Long totalLayover;

	private Long totalLayoverAccepted;

	private Long totalLayoverDeclined;

	private BigDecimal amountReceivedByHotel;

	private BigDecimal totalCommission;

}
