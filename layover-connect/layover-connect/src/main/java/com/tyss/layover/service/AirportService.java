package com.tyss.layover.service;

import com.tyss.layover.pojo.AirportRequest;
import com.tyss.layover.pojo.UserDeletePojo;

public interface AirportService {

	String registerAirport(AirportRequest airportRequest);

	String updateAirportDetailsByAdmin(AirportRequest airportRequest);

	String deleteAirportDetailsByAdmin(UserDeletePojo userDeletePojo);

}
