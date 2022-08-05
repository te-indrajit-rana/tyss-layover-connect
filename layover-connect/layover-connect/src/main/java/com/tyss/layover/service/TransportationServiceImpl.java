package com.tyss.layover.service;

import static com.tyss.layover.constant.AdminConstants.USER_IS_BLOCKED_BY_ADMIN;
import static com.tyss.layover.constant.TransportationConstants.EMAIL_OR_PHONE_NUMBER_IS_ALREADY_REGISTERED;
import static com.tyss.layover.constant.TransportationConstants.PENDING;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_DETAILS_IS_DELETED_BY_ADMIN;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_DETAILS_NOT_FOUND;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_DETAILS_UPDATE_BY_ADMIN;
import static com.tyss.layover.constant.TransportationConstants.TRANSPORTATION_REGISTRATION;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyss.layover.entity.Transportation;
import com.tyss.layover.entity.UserDetails;
import com.tyss.layover.exception.EmailInterruptionException;
import com.tyss.layover.exception.TransportationDetailsNotFoundException;
import com.tyss.layover.pojo.TransportationRequest;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.repository.TransportationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Scope("singleton")
@RequiredArgsConstructor
public class TransportationServiceImpl implements TransportationService {

	private final TransportationRepository transportationRepository;

	@Transactional
	public @Override String registerTransportationRegistration(TransportationRequest transportationRequest) {
		UserDetails userDetails = UserDetails.builder().email(transportationRequest.getEmail()).status(PENDING)
				.isDeleted(Boolean.FALSE).isBlocked(Boolean.FALSE).userType(TRANSPORTATION).build();

		Transportation transportation = Transportation.builder()
				.transportationName(transportationRequest.getTransportationName())
				.transportationTitle(transportationRequest.getTransportationTitle())
				.transportationAddress(transportationRequest.getTransportationAddress())
				.name(transportationRequest.getName()).contactNumber(transportationRequest.getContactNumber())
				.country(transportationRequest.getCountry()).location(transportationRequest.getLocation())
				.registrationDate(LocalDateTime.now()).user(userDetails).build();
		Transportation transportationObject = new Transportation();
		try {
			transportationObject = transportationRepository.save(transportation);
		} catch (Exception e) {
			throw new EmailInterruptionException(EMAIL_OR_PHONE_NUMBER_IS_ALREADY_REGISTERED);
		}
		transportation.getUser().setObjectId(transportationObject.getTransportationId());
		return TRANSPORTATION_REGISTRATION;
	}

	public @Override String updateTransportationDetailsByAdmin(TransportationRequest transportationRequest) {
		Transportation transportation = transportationRepository
				.findByTransportationId(transportationRequest.getTransportationId())
				.orElseThrow(() -> new TransportationDetailsNotFoundException(TRANSPORTATION_DETAILS_NOT_FOUND));
		UserDetails userDetails = transportation.getUser();
		userDetails.setAdminNote(transportationRequest.getAdminNote());
		userDetails.setEmail(transportationRequest.getEmail());
		userDetails.setStatus(transportationRequest.getStatus());
		BeanUtils.copyProperties(transportationRequest, transportation);
		try {
			transportation.setUser(userDetails);
			transportationRepository.save(transportation);
		} catch (Exception e) {
			throw new EmailInterruptionException(EMAIL_OR_PHONE_NUMBER_IS_ALREADY_REGISTERED);
		}
		return TRANSPORTATION_DETAILS_UPDATE_BY_ADMIN;
	}

	@Transactional
	public @Override String deleteTransportationDetailsByAdmin(UserDeletePojo userDeletePojo) {
		Transportation transportation = transportationRepository.findByTransportationId(userDeletePojo.getId())
				.orElseThrow(() -> new TransportationDetailsNotFoundException(TRANSPORTATION_DETAILS_NOT_FOUND));
		if (Boolean.TRUE.equals(userDeletePojo.getIsBlock())) {
			transportation.getUser().setIsBlocked(Boolean.TRUE);
			return USER_IS_BLOCKED_BY_ADMIN;
		} else {
			transportation.getUser().setIsDeleted(Boolean.TRUE);
			return TRANSPORTATION_DETAILS_IS_DELETED_BY_ADMIN;
		}
	}

}
