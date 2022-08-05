package com.tyss.layover.service;

import static com.tyss.layover.constant.AdminConstants.USER_IS_BLOCKED_BY_ADMIN;
import static com.tyss.layover.constant.AirportConstants.AIRPORT;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_DETAILS_DELETED_BY_ADMIN;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_DETAILS_IS_UPDATED_SUCCESSFULLY;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_DETAILS_NOT_FOUND;
import static com.tyss.layover.constant.AirportConstants.AIRPORT_REGISTRATION_IS_SUCCESSFULLY_DONE;
import static com.tyss.layover.constant.AirportConstants.EMAIL_OR_PHONE_NUMBER_IS_ALREADY_REGISTERED;
import static com.tyss.layover.constant.AirportConstants.PENDING;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyss.layover.constant.LayoverConstant;
import com.tyss.layover.entity.Airport;
import com.tyss.layover.entity.UserDetails;
import com.tyss.layover.exception.AirportDetailsNotFoundException;
import com.tyss.layover.exception.EmailInterruptionException;
import com.tyss.layover.pojo.AirportRequest;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.repository.AirportRepository;

import lombok.RequiredArgsConstructor;

@Service
@Scope("singleton")
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

	private final AirportRepository airportRepository;

	@Transactional
	public @Override String registerAirport(AirportRequest airportRequest) {
		UserDetails userDetails = UserDetails.builder().email(airportRequest.getEmail()).userType(AIRPORT)
				.isDeleted(Boolean.FALSE).isBlocked(Boolean.FALSE).status(PENDING).build();

		Airport airport = Airport.builder().airportName(airportRequest.getAirportName())
				.airportAddress(airportRequest.getAirportAddress()).airportTitle(airportRequest.getAirportTitle())
				.name(airportRequest.getName()).contactNumber(airportRequest.getContactNumber())
				.handlingAirlines(airportRequest.getHandlingAirlines()).location(airportRequest.getLocation())
				.country(airportRequest.getCountry()).registrationDate(LocalDateTime.now()).user(userDetails).build();
		Airport airportObject = new Airport();
		try {
			airportObject = airportRepository.save(airport);
		} catch (Exception e) {
			throw new EmailInterruptionException(LayoverConstant.EMAIL_ALREADY_REGISTER);
		}
		airport.getUser().setObjectId(airportObject.getAirportId());
		return AIRPORT_REGISTRATION_IS_SUCCESSFULLY_DONE;
	}

	public @Override String updateAirportDetailsByAdmin(AirportRequest airportRequest) {
		Airport airport = airportRepository.findByAirportId(airportRequest.getAirportId())
				.orElseThrow(() -> new AirportDetailsNotFoundException(AIRPORT_DETAILS_NOT_FOUND));
		UserDetails userDetails = airport.getUser();
		userDetails.setStatus(airportRequest.getStatus());
		userDetails.setAdminNote(airportRequest.getAdminNote());
		userDetails.setEmail(airportRequest.getEmail());
		BeanUtils.copyProperties(airportRequest, airport);
		try {
			airport.setUser(userDetails);
			airportRepository.save(airport);
		} catch (Exception exception) {
			throw new EmailInterruptionException(EMAIL_OR_PHONE_NUMBER_IS_ALREADY_REGISTERED);
		}

		return AIRPORT_DETAILS_IS_UPDATED_SUCCESSFULLY;
	}

	@Transactional
	public @Override String deleteAirportDetailsByAdmin(UserDeletePojo userDeletePojo) {
		Airport airport = airportRepository.findByAirportId(userDeletePojo.getId())
				.orElseThrow(() -> new AirportDetailsNotFoundException(AIRPORT_DETAILS_NOT_FOUND));
		if (Boolean.TRUE.equals(userDeletePojo.getIsBlock())) {
			airport.getUser().setIsBlocked(Boolean.TRUE);
			return USER_IS_BLOCKED_BY_ADMIN;
		} else {
			airport.getUser().setIsDeleted(Boolean.TRUE);
			return AIRPORT_DETAILS_DELETED_BY_ADMIN;
		} 
	}

}
