package com.tyss.layover.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingUserPojo {
	private Integer billingUserId;
	private String name;
	private String password;
	private String employeeId;
	private Long pin;
	private Integer airlineId;
}
