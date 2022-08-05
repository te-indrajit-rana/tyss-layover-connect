package com.tyss.layover.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpValidationPojo {
	
	private String email;
	private Integer otp;

}
