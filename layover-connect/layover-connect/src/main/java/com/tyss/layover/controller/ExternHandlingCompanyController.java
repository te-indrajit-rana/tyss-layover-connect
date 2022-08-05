package com.tyss.layover.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.layover.pojo.ExternalHandlingCompanyRequest;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.ExtrenHandlingCompany;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*" )
@RestController
@RequiredArgsConstructor
//@SecurityRequirement(name = "layover-api")
@RequestMapping("api/v1/layover/extern-Handling-company-details")
public class ExternHandlingCompanyController {

	private final ExtrenHandlingCompany extrenHandlingCompany;

	@PostMapping("/registratation")
	public ResponseEntity<LayoverResponseBody> registerExtrenHandlingCompany(
			@Valid @RequestBody ExternalHandlingCompanyRequest externalHandlingCompanyRequest) {
		String registerExternalHandlingCompany = extrenHandlingCompany
				.registerExternalHandlingCompany(externalHandlingCompanyRequest);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(registerExternalHandlingCompany).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

//	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_EXTRENHANDLINGCOMPANY')")
	@PutMapping("/update-details-by-admin")
	public ResponseEntity<LayoverResponseBody> updateExtrenHandlingCompanyByAdmin(
			@Valid @RequestBody ExternalHandlingCompanyRequest externalHandlingCompanyRequest) {
		String updateExternalHandlingCompanyByAdmin = extrenHandlingCompany
				.updateExternalHandlingCompanyByAdmin(externalHandlingCompanyRequest);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(updateExternalHandlingCompanyByAdmin).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

//	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_EXTRENHANDLINGCOMPANY')")
	@DeleteMapping("/delete-details-by-admin")
	public ResponseEntity<LayoverResponseBody> deleteExtrenHandlingCompanyByAdmin(
			@RequestBody UserDeletePojo userDeletePojo) {
		String deleteExternalHandlingCompanyByAdmin = extrenHandlingCompany
				.deleteExternalHandlingCompanyByAdmin(userDeletePojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(deleteExternalHandlingCompanyByAdmin).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

}
