package com.tyss.layover.service;

import java.util.List;
import java.util.SortedMap;

import com.tyss.layover.response.AdminActionResponse;
import com.tyss.layover.response.AdminCountResponse;
import com.tyss.layover.response.AdminLatestLayoversResponse;
import com.tyss.layover.response.AdminLatestRegistrationResponse;

public interface AdminService {

	AdminCountResponse adminCountResponse();

	List<AdminLatestRegistrationResponse> adminLatestRegistrationResponses(String userType);

	List<AdminActionResponse> getAllTheRegisteredUserDetails(String userType);

	List<AdminLatestLayoversResponse> adminLatestLayoversResponses();

	SortedMap<String, Long> totalLayoverCountBasedOnMonth(String status, Integer year);

	SortedMap<String, Double> totalAmountGeneratedBasedOnMonth(String status, Integer year);

}
