package com.tyss.layover.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.pojo.HotelDetailsPojo;
import com.tyss.layover.pojo.LayoverPojo;
import com.tyss.layover.pojo.CommonPojo;

public interface LayoverRequestResponseService {
	
	List<HotelDetailsPojo>  fetchAllHotelDetails(Integer capacity , String location);
	
	List<String> saveMultipartFile(List<MultipartFile> File);
	
	String saveRequest(List<LayoverPojo> layoverPojos);
	
	List<LayoverPojo> fetchAllLayoverRequestByHotel(CommonPojo layoverRequestPojo);
	
	List<LayoverPojo> fetchOpenRequestByAirline(CommonPojo openRequest);
	
	String saveLayoverResponse(LayoverPojo layoverRequestPojo);
	
	List<LayoverPojo> fetchAllAcceptedRequestByAirline(CommonPojo layoverRequestPojo);
	
	String uploadPassengerListAndLoc(MultipartFile passengerList,MultipartFile loc,Integer request);
	
	List<LayoverPojo> fetchAllRequestByAirline(CommonPojo layoverRequestPojo);
	
	
	
	

}
