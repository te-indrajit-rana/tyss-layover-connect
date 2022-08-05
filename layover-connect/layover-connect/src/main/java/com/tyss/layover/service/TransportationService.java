package com.tyss.layover.service;

import com.tyss.layover.pojo.TransportationRequest;
import com.tyss.layover.pojo.UserDeletePojo;

public interface TransportationService {

	String registerTransportationRegistration(TransportationRequest transportationRequest);

	String updateTransportationDetailsByAdmin(TransportationRequest transportationRequest);

	String deleteTransportationDetailsByAdmin(UserDeletePojo userDeletePojo);

}
