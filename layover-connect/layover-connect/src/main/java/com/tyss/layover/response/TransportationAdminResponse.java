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
public class TransportationAdminResponse {

	private Integer transportationId;
	private String transportationName;

	private String transportationTitle;

	private String transportationAddress;

	private String name;

	private String contactNumber;

	private String country;

	private String location;

	private String email;

	private String status;

	private String adminNote;
	
	private Boolean isDeleted;
	
	private Boolean isBlocked;

}
