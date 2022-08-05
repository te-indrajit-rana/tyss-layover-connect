package com.tyss.layover.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.CareerServiceImpl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/careers")
@RequiredArgsConstructor
@SecurityRequirement(name = "layover-api")
public class CareersController {

	private final CareerServiceImpl careerServiceImpl;

	@PostMapping(value = "/career-opportunity", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<LayoverResponseBody> careerOppotunities(@RequestPart String data,
			@RequestPart MultipartFile multipartFile) {
		String careerOppotunitiesRequest = careerServiceImpl.careerOppotunitiesRequest(data, multipartFile);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(Boolean.FALSE)
				.message(careerOppotunitiesRequest).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}
}
