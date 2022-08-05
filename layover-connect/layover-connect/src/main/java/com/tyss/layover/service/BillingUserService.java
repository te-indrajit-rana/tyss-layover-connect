package com.tyss.layover.service;

import com.tyss.layover.pojo.BillingUserPojo;

public interface BillingUserService {

	BillingUserPojo saveBillingUser(BillingUserPojo billingUserPojo);

	BillingUserPojo fetchBillingUserByAirlineId(Integer id);

	String updateBillingUser(BillingUserPojo billingUserPojo);
	
	String deleteBillingUser(Integer billingUserId);
}
