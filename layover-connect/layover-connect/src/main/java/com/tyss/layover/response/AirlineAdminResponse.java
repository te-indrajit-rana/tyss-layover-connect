package com.tyss.layover.response;

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
public class AirlineAdminResponse {
	
	private int airlineId;
	private String airlineName;
	private String airlineTitle;
	private String airlineAddress;
	private String name;
	private String contactNumber;
	private String country;
	private String location;
	private String logoPath;
	private String email;
	private String userType;
	private Boolean isDeleted;
	private Boolean isBlocked;
	private String status;
	private String adminNote;
}
