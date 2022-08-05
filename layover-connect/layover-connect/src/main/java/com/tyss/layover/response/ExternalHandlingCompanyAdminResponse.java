package com.tyss.layover.response;

import java.util.List;

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
public class ExternalHandlingCompanyAdminResponse {

	private Integer externalHandlingCompanyId;
	private String externalHandlingCompanyName;

	private String externalHandlingCompanyTitle;

	private String externalHandlingCompanyAddress;

	private String name;

	private String contactNumber;

	private List<String> handlingAirlines;

	private String country;

	private String location;

	private String email;

	private String status;
	
	private String adminNote;
	
	private Boolean isDeleted;
	
	private Boolean isBlocked;
}
