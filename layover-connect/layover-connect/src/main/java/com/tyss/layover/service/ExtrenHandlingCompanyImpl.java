package com.tyss.layover.service;

import static com.tyss.layover.constant.AdminConstants.USER_IS_BLOCKED_BY_ADMIN;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EMAIL_OR_PHONE_NUMBER_IS_ALREADY_REGISTERED;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERNAL_HANDLING_COMPANY_REGISTRATION_SUCCESSFULLY;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERN_HANDLING_COMPANY_DETAILS_IS_DELETED_SUCCESSFULLY;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERN_HANDLING_COMPANY_DETAILS_IS_UPDATED_SUCCESSFULLY;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTERN_HANDLING_COMPANY_DETAILS_NOT_FOUND;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.EXTRENHANDLINGCOMPANY;
import static com.tyss.layover.constant.ExtrenHandlingCompanyConstants.PENDING;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyss.layover.constant.LayoverConstant;
import com.tyss.layover.entity.ExternalHandlingCompany;
import com.tyss.layover.entity.UserDetails;
import com.tyss.layover.exception.EmailInterruptionException;
import com.tyss.layover.exception.ExternalHandlingCompanyDetailsNotFoundException;
import com.tyss.layover.pojo.ExternalHandlingCompanyRequest;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.repository.ExternCompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@Scope("singleton")
@RequiredArgsConstructor
public class ExtrenHandlingCompanyImpl implements ExtrenHandlingCompany {

	private final ExternCompanyRepository externCompanyRepository;

	@Transactional
	public @Override String registerExternalHandlingCompany(
			ExternalHandlingCompanyRequest extrenalHandlingCompanyRegistrationRequest) {
		UserDetails userDetails = UserDetails.builder().email(extrenalHandlingCompanyRegistrationRequest.getEmail())
				.isDeleted(Boolean.FALSE).isBlocked(Boolean.FALSE).status(PENDING).userType(EXTRENHANDLINGCOMPANY)
				.build();

		ExternalHandlingCompany externalHandlingCompany = ExternalHandlingCompany.builder()
				.externalHandlingCompanyName(
						extrenalHandlingCompanyRegistrationRequest.getExternalHandlingCompanyName())
				.externalHandlingCompanyAddress(
						extrenalHandlingCompanyRegistrationRequest.getExternalHandlingCompanyAddress())
				.externalHandlingCompanyTitle(
						extrenalHandlingCompanyRegistrationRequest.getExternalHandlingCompanyTitle())
				.handlingAirlines(extrenalHandlingCompanyRegistrationRequest.getHandlingAirlines())
				.name(extrenalHandlingCompanyRegistrationRequest.getName())
				.contactNumber(extrenalHandlingCompanyRegistrationRequest.getContactNumber())
				.country(extrenalHandlingCompanyRegistrationRequest.getCountry())
				.location(extrenalHandlingCompanyRegistrationRequest.getLocation())
				.registrationDate(LocalDateTime.now()).user(userDetails).build();
		ExternalHandlingCompany externalHandlingCompanyObject = new ExternalHandlingCompany();
		try {
			externalHandlingCompanyObject = externCompanyRepository.save(externalHandlingCompany);
		} catch (Exception e) {
			throw new EmailInterruptionException(LayoverConstant.EMAIL_ALREADY_REGISTER);
		}
		externalHandlingCompany.getUser().setObjectId(externalHandlingCompanyObject.getExternalHandlingCompanyId());
		return EXTERNAL_HANDLING_COMPANY_REGISTRATION_SUCCESSFULLY;
	}

	public @Override String updateExternalHandlingCompanyByAdmin(
			ExternalHandlingCompanyRequest externalHandlingCompanyRequest) {
		ExternalHandlingCompany externalHandlingCompany = externCompanyRepository
				.findByExternalHandlingCompanyId(externalHandlingCompanyRequest.getExternalHandlingCompanyId())
				.orElseThrow(() -> new ExternalHandlingCompanyDetailsNotFoundException(
						EXTERN_HANDLING_COMPANY_DETAILS_NOT_FOUND));
		UserDetails userDetails = externalHandlingCompany.getUser();
		userDetails.setAdminNote(externalHandlingCompanyRequest.getAdminNote());
		userDetails.setEmail(externalHandlingCompanyRequest.getEmail());
		userDetails.setStatus(externalHandlingCompanyRequest.getStatus());
		BeanUtils.copyProperties(externalHandlingCompanyRequest, externalHandlingCompany);
		try {
			externalHandlingCompany.setUser(userDetails);
			externCompanyRepository.save(externalHandlingCompany);
		} catch (Exception e) {
			throw new EmailInterruptionException(EMAIL_OR_PHONE_NUMBER_IS_ALREADY_REGISTERED);
		}
		return EXTERN_HANDLING_COMPANY_DETAILS_IS_UPDATED_SUCCESSFULLY;
	}

	@Transactional
	public @Override String deleteExternalHandlingCompanyByAdmin(UserDeletePojo userDeletePojo) {
		ExternalHandlingCompany externalHandlingCompany = externCompanyRepository
				.findByExternalHandlingCompanyId(userDeletePojo.getId())
				.orElseThrow(() -> new ExternalHandlingCompanyDetailsNotFoundException(
						EXTERN_HANDLING_COMPANY_DETAILS_NOT_FOUND));
		if (Boolean.TRUE.equals(userDeletePojo.getIsBlock())) {
			externalHandlingCompany.getUser().setIsBlocked(Boolean.TRUE);
			return USER_IS_BLOCKED_BY_ADMIN;
		} else {
			externalHandlingCompany.getUser().setIsDeleted(Boolean.TRUE);
			return EXTERN_HANDLING_COMPANY_DETAILS_IS_DELETED_SUCCESSFULLY;
		}
	}

}
