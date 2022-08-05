package com.tyss.layover.service;

import static com.tyss.layover.constant.AirlineConstant.AIRLINE;
import static com.tyss.layover.constant.AirlineConstant.AIRLINE_DELETED_SUCCESSFULLY;
import static com.tyss.layover.constant.AirlineConstant.AIRLINE_HAS_BLOCK_BY_ADMIN;
import static com.tyss.layover.constant.AirlineConstant.INVALID_USER_ID;
import static com.tyss.layover.constant.AirlineConstant.INVALID_VALUE;
import static com.tyss.layover.constant.AirlineConstant.PENDING;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THIS_AIRLINE_DOESNOT_HAVE_ANY_REQUEST;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.constant.LayoverConstant;
import com.tyss.layover.entity.Airline;
import com.tyss.layover.entity.LayoverRequest;
import com.tyss.layover.entity.LayoverResponse;
import com.tyss.layover.entity.Payment;
import com.tyss.layover.entity.UserDetails;
import com.tyss.layover.exception.EmailInterruptionException;
import com.tyss.layover.exception.GlobalException;
import com.tyss.layover.exception.UserNotFoundException;
import com.tyss.layover.pojo.AirlinePojo;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.repository.AirlineRepository;
import com.tyss.layover.repository.LayoverRequestRepository;
import com.tyss.layover.repository.LayoverResponseRepository;
import com.tyss.layover.repository.PaymentRepository;
import com.tyss.layover.response.AirlineDashbordCountResponse;
import com.tyss.layover.util.SSSUploadFile;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This is the service implementation class for AirlineService interface. Here
 * you find implementation for saving, updating, fetching and deleting the
 * Airline Details
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
public class AirlineServiceImpl implements AirlineService {

	/**
	 * This field is used for invoking persistence layer methods
	 */
	private final AirlineRepository airlineRepository;

	/**
	 * This field is used for invoking AmazonS3 layer methods
	 */
	private final SSSUploadFile sssUploadFile;

	private final LayoverRequestRepository layoverRequestRepository;

	private final LayoverResponseRepository layoverResponseRepository;

	private final PaymentRepository paymentRepository;

	/**
	 * This Method is Implemented to Save Airline Details
	 */

	@Transactional
	@Override
	public AirlinePojo saveAirline(AirlinePojo airlinePojo) {

		UserDetails userDetails = UserDetails.builder().email(airlinePojo.getEmail()).userType(AIRLINE)
				.isDeleted(Boolean.FALSE).isBlocked(Boolean.FALSE).status(PENDING).build();
		Airline airline = new Airline();
		BeanUtils.copyProperties(airlinePojo, airline);
		airline.setUser(userDetails);
		airline.setRegistrationDate(LocalDateTime.now());
		Airline saveAirline = new Airline();
		try {
			saveAirline = airlineRepository.save(airline);
		} catch (Exception exception) {
			log.error(exception.getMessage());
			log.error(LayoverConstant.EMAIL_ALREADY_REGISTER);
			throw new EmailInterruptionException(LayoverConstant.EMAIL_ALREADY_REGISTER);
		}
		saveAirline.getUser().setObjectId(saveAirline.getAirlineId());
		BeanUtils.copyProperties(saveAirline, airlinePojo);
		airlinePojo.setEmail(saveAirline.getUser().getEmail());
		airlinePojo.setUserType(saveAirline.getUser().getUserType());
		airlinePojo.setStatus(saveAirline.getUser().getStatus());
		airlinePojo.setAdminNote(saveAirline.getUser().getAdminNote());
		airlinePojo.setIsDeleted(saveAirline.getUser().getIsDeleted());
		airlinePojo.setIsBlocked(saveAirline.getUser().getIsBlocked());
		return airlinePojo;
	}

	/**
	 * This Method is Implemented To Get The Airline Details By ID
	 */
	@Operation(description = "This Method is Implemented To Get The Airline Details By ID")
	@Override
	public AirlinePojo fetchById(Integer id) {
		Airline airline = airlineRepository.findById(id).orElseThrow(() -> new UserNotFoundException(INVALID_USER_ID));
		AirlinePojo airlinePojo = new AirlinePojo();
		BeanUtils.copyProperties(airline, airlinePojo);
		airlinePojo.setEmail(airline.getUser().getEmail());
		airlinePojo.setUserType(airline.getUser().getUserType());
		airlinePojo.setStatus(airline.getUser().getStatus());
		airlinePojo.setAdminNote(airline.getUser().getAdminNote());
		airlinePojo.setIsDeleted(airline.getUser().getIsDeleted());
		airlinePojo.setIsBlocked(airline.getUser().getIsBlocked());
		return airlinePojo;

	}

	/**
	 * This Method is Implemented To Update The Airline Logo
	 */
	@Transactional
	@Override
	public AirlinePojo updateAirlineLogo(MultipartFile locPath, MultipartFile generalPath, Integer id) {
		Airline airline = airlineRepository.findById(id).orElseThrow(() -> new UserNotFoundException(INVALID_USER_ID));

		if (!locPath.isEmpty()) {

			String uploadFile = sssUploadFile.uploadFile(locPath);
			if (airline.getLogoPath() != null) {
				sssUploadFile.deleteS3Folder(airline.getLogoPath());
			}
			airline.setLogoPath(uploadFile);
		}
		if (!generalPath.isEmpty()) {
			String uploadFile = sssUploadFile.uploadFile(generalPath);
			if (airline.getGeneralLocPath() != null) {
				sssUploadFile.deleteS3Folder(airline.getGeneralLocPath());
			}
			airline.setGeneralLocPath(uploadFile);

		}

		AirlinePojo airlinePojo = new AirlinePojo();
		BeanUtils.copyProperties(airline, airlinePojo);
		airlinePojo.setEmail(airline.getUser().getEmail());
		airlinePojo.setUserType(airline.getUser().getUserType());
		airlinePojo.setStatus(airline.getUser().getStatus());
		airlinePojo.setAdminNote(airline.getUser().getAdminNote());
		airlinePojo.setIsDeleted(airline.getUser().getIsDeleted());
		airlinePojo.setIsBlocked(airline.getUser().getIsBlocked());
		return airlinePojo;
	}

	/**
	 * This Method is Implemented To Delete The Airline Details
	 */
	@Transactional
	@Override
	public String deleteAirline(UserDeletePojo userDeletePojo) {
		Airline airline = airlineRepository.findById(userDeletePojo.getId())
				.orElseThrow(() -> new UserNotFoundException(INVALID_USER_ID));
		if (Boolean.TRUE.equals(userDeletePojo.getIsBlock())) {
			airline.getUser().setIsBlocked(Boolean.TRUE);
			log.info(AIRLINE_HAS_BLOCK_BY_ADMIN);
			return AIRLINE_HAS_BLOCK_BY_ADMIN;
		} else if (Boolean.TRUE.equals(userDeletePojo.getIsDeleted())) {
			airline.getUser().setIsDeleted(Boolean.TRUE);
			log.info(AIRLINE_DELETED_SUCCESSFULLY);
			return AIRLINE_DELETED_SUCCESSFULLY;
		}
		log.info(INVALID_VALUE);
		return INVALID_VALUE;
	}

	/**
	 * This Method is Implemented To Update The Airline Details
	 */

	@Override
	public AirlinePojo updateAirlineDetails(AirlinePojo airlinePojo) {
		Airline airline = airlineRepository.findById(airlinePojo.getAirlineId())
				.orElseThrow(() -> new UserNotFoundException(INVALID_USER_ID));
		UserDetails userDetails = airline.getUser();
		userDetails.setAdminNote(airlinePojo.getAdminNote());
		userDetails.setEmail(airlinePojo.getEmail());
		userDetails.setStatus(airlinePojo.getStatus());
		BeanUtils.copyProperties(airlinePojo, airline);

		try {
			airline.setUser(userDetails);
			airlineRepository.save(airline);
		} catch (Exception exception) {
			log.error(exception.getMessage());
			log.error(LayoverConstant.EMAIL_ALREADY_REGISTER);
			throw new EmailInterruptionException(LayoverConstant.EMAIL_ALREADY_REGISTER);
		}

		return airlinePojo;

	}

	@Override
	public AirlineDashbordCountResponse airlineCount(Integer airlineId) {
		List<LayoverRequest> layoverRequests = layoverRequestRepository.findByAirlineAirlineId(airlineId)
				.orElseThrow(() -> new GlobalException(THIS_AIRLINE_DOESNOT_HAVE_ANY_REQUEST));

		Long totalLayoverCount = layoverRequests.stream().count();

		List<LayoverResponse> findByLayoverRequestAirlineAirlineId = layoverResponseRepository
				.findByLayoverRequestAirlineAirlineId(airlineId);

		Long totalLayoverAccepted = findByLayoverRequestAirlineAirlineId.stream()
				.filter(layoverResponse -> layoverResponse.getStatus().equalsIgnoreCase("Accepted")).count();

		Long totalLayoverDeclined = findByLayoverRequestAirlineAirlineId.stream()
				.filter(layoverResponse -> layoverResponse.getStatus().equalsIgnoreCase("Denied")).count();
		Long totalLayoverCancelled = findByLayoverRequestAirlineAirlineId.stream()
				.filter(layoverResponse -> layoverResponse.getStatus().equalsIgnoreCase("cancelled")).count();

		List<Payment> findByLayoverResponseLayoverRequestAirlineAirlineId = paymentRepository
				.findByLayoverResponseLayoverRequestAirlineAirlineId(airlineId);

		BigDecimal openPayments = BigDecimal.valueOf(findByLayoverResponseLayoverRequestAirlineAirlineId.stream()
				.filter(payment -> payment.getHotelStatus().equalsIgnoreCase(PENDING)).collect(Collectors.summingDouble(
						layoverResponse -> layoverResponse.getLayoverResponse().getTotalAmount().doubleValue())));

		BigDecimal closedPayments = BigDecimal.valueOf(findByLayoverResponseLayoverRequestAirlineAirlineId.stream()
				.filter(payment -> payment.getHotelStatus().equalsIgnoreCase("Received"))
				.collect(Collectors.summingDouble(
						layoverResponse -> layoverResponse.getLayoverResponse().getTotalAmount().doubleValue())));

		BigDecimal totalAmount = closedPayments.add(openPayments);

		return AirlineDashbordCountResponse.builder().totalLayover(totalLayoverCount)
				.totalLayoverAccepted(totalLayoverAccepted).totalLayoverDeclined(totalLayoverDeclined)
				.openPayments(openPayments).closedPayments(closedPayments).totalPayment(totalAmount)
				.totalLayoverCancelled(totalLayoverCancelled).build();
	}

}
