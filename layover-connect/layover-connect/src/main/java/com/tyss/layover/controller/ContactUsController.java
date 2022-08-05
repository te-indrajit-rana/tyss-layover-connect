package com.tyss.layover.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.layover.pojo.ContactUsRequest;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.ContactUsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("api/v1/contact-us")
@RequiredArgsConstructor
@SecurityRequirement(name = "layover-api")
public class ContactUsController {
	private final ContactUsService contactUsService;

	@PostMapping("/contact-us")
	public ResponseEntity<LayoverResponseBody> careerOppotunities(@RequestBody ContactUsRequest contactUsRequest) {
		String request = contactUsService.contactUsRequest(contactUsRequest);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE).message(request)
				.build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

}
