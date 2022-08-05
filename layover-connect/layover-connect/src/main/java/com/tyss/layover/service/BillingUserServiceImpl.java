package com.tyss.layover.service;

import static com.tyss.layover.constant.AirlineConstant.INVALID_USER_ID;
import static com.tyss.layover.constant.LayoverConstant.BILLING_USER_DELETED_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverConstant.BILLING_USER_UPDATED_SUCCESSFULLY;
import static com.tyss.layover.constant.BillingUserConstant.BILLING_USER_FETCH_SUCCESSFULLY;
import static com.tyss.layover.constant.BillingUserConstant.BILLING_USER_SAVE_SUCCESSFULLY;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tyss.layover.constant.LayoverConstant;
import com.tyss.layover.entity.Airline;
import com.tyss.layover.entity.BillingUser;
import com.tyss.layover.exception.UserNotFoundException;
import com.tyss.layover.pojo.BillingUserPojo;
import com.tyss.layover.repository.AirlineRepository;
import com.tyss.layover.repository.BillingUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This is the service implementation class for BillingUserService interface.
 * Here you find implementation for saving, updating, fetching and deleting the
 * BillingUser Details
 * 
 * 
 * @author Akram
 * @author Shrinivas Anant Tikare
 * @author Indrajit rana
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BillingUserServiceImpl implements BillingUserService {

	/**
	 * This Field Invoke The Persistent Layer Methods
	 */
	private final BillingUserRepository billingUserRepository;

	/**
	 * This Field Invoke The Persistent Layer Methods
	 */
	private final AirlineRepository airlineRepository;

	/**
	 * This Method Is Implemented To Save BillingUserDetails
	 */
	@Override
	public BillingUserPojo saveBillingUser(BillingUserPojo billingUserPojo) {
		Airline airline = airlineRepository.findById(billingUserPojo.getAirlineId())
				.orElseThrow(() -> new UserNotFoundException(INVALID_USER_ID));
		BillingUser billingUser = billingUserRepository.findByAirline(airline)
				.orElse(BillingUser.builder().employeeId(billingUserPojo.getEmployeeId())
						.name(billingUserPojo.getName()).password(billingUserPojo.getPassword())
						.pin(billingUserPojo.getPin()).build());
		billingUser.setAirline(airline);
		log.info(BILLING_USER_SAVE_SUCCESSFULLY);
		BillingUser saveBillingUser = billingUserRepository.save(billingUser);
		BeanUtils.copyProperties(saveBillingUser, billingUserPojo);
		return billingUserPojo;
	}// End Of The Billing User Save

	/**
	 * This Method Is Implemented To Fetch BillingUserDetails By Airline ID
	 */
	@Override
	public BillingUserPojo fetchBillingUserByAirlineId(Integer id) {
		Airline airline = airlineRepository.findById(id).orElseThrow(() -> new UserNotFoundException(INVALID_USER_ID));
		BillingUser billingUser = billingUserRepository.findByAirline(airline)
				.orElseThrow(() -> new UserNotFoundException(INVALID_USER_ID));
		BillingUserPojo billingUserPojo = new BillingUserPojo();
		log.info(BILLING_USER_FETCH_SUCCESSFULLY);
		BeanUtils.copyProperties(billingUser, billingUserPojo);
		return billingUserPojo;
	}// End Of The Billing User Fetch by Airline Id

	/**
	 * This Method Is Implemented To Update BillingUserDetails
	 */
	@Override
	public String updateBillingUser(BillingUserPojo billingUserPojo) {
		BillingUser billingUser = billingUserRepository.findByBillingUserId(billingUserPojo.getBillingUserId())
				.orElseThrow(() -> new UserNotFoundException(LayoverConstant.GIVEN_USER_IS_NOT_PRESENT));
		billingUser.setEmployeeId(billingUserPojo.getEmployeeId());
		billingUser.setName(billingUserPojo.getName());
		billingUser.setPassword(billingUserPojo.getPassword());
		billingUser.setPin(billingUserPojo.getPin());
		billingUserRepository.save(billingUser);
		log.info(BILLING_USER_UPDATED_SUCCESSFULLY);
		return BILLING_USER_UPDATED_SUCCESSFULLY;
	}// End Of The Update BillingUserDetails

	/**
	 * This Method Is Implemented To Delete BillingUserDetails
	 */
	@Override
	public String deleteBillingUser(Integer billingUserId) {
		billingUserRepository.deleteById(billingUserId);
		log.info(BILLING_USER_DELETED_SUCCESSFULLY);
		return BILLING_USER_DELETED_SUCCESSFULLY;
	}// End Of Delete BillingUser

}
