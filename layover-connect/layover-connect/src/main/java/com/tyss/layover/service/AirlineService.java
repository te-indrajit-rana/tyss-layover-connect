package com.tyss.layover.service;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.pojo.AirlinePojo;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.response.AirlineDashbordCountResponse;

public interface AirlineService {

	AirlinePojo saveAirline(AirlinePojo airlinePojo);

	AirlinePojo fetchById(Integer id);

	AirlinePojo updateAirlineLogo(MultipartFile locPath, MultipartFile generalPath, Integer id);

	String deleteAirline(UserDeletePojo userDeletePojo);

	AirlinePojo updateAirlineDetails(AirlinePojo airlinePojo);

	AirlineDashbordCountResponse airlineCount(Integer airlineId);

}
