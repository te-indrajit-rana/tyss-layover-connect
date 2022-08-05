package com.tyss.layover.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_DEFAULT)
public class AdminActionResponse {

	List<AirlineAdminResponse> getAllTheAirlines;
	
	List<TransportationAdminResponse> getAllTheTransportation;
	
	List<ExternalHandlingCompanyAdminResponse> getAllTheExternHandlingCompany;
	
	List<HotelAdminResponse> getAllTheHotels;
	
	List<AirportAdminResponse> getAllTheAirports;
	
}
