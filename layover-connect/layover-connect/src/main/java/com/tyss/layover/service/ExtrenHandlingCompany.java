package com.tyss.layover.service;

import com.tyss.layover.pojo.ExternalHandlingCompanyRequest;
import com.tyss.layover.pojo.UserDeletePojo;

public interface ExtrenHandlingCompany {

	String registerExternalHandlingCompany(ExternalHandlingCompanyRequest extrenHandlingCompanyRegistrationRequest);

	String updateExternalHandlingCompanyByAdmin(
			ExternalHandlingCompanyRequest externalHandlingCompanyRequest);

	String deleteExternalHandlingCompanyByAdmin(UserDeletePojo userDeletePojo);
}
