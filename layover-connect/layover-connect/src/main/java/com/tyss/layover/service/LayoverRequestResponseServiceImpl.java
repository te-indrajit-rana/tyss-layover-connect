package com.tyss.layover.service;

import static com.tyss.layover.constant.LayoverRequestResponseConstant.DUBLICATE_ENTRY_LAYOVER_REQUEST_ID;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.FOR_LAYOVER_RESPONSE;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.INVALID_AIRLINE_ID;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.INVALID_HOTEL_DETAIL_ID;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.INVALID_REQUEST_ID;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.LAYOVER_REQUEST_LIST_IS_EMPTY;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.LAYOVER_REQUEST_SAVE_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.LAYOVER_RESPONSE_SAVE_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.NO_REQUEST_FOUND_FOR_THE_HOTEL;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.PASSENGER_LIST_AND_LOC_UPDATED_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.PENDING;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.REQUEST_IS_NOT_PRESENT;
import static com.tyss.layover.constant.LayoverRequestResponseConstant.THIS_AIRLINE_DOESNOT_HAVE_ANY_REQUEST;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.entity.Airline;
import com.tyss.layover.entity.Commission;
import com.tyss.layover.entity.HotelDetails;
import com.tyss.layover.entity.Image;
import com.tyss.layover.entity.LayoverRequest;
import com.tyss.layover.entity.LayoverResponse;
import com.tyss.layover.entity.Payment;
import com.tyss.layover.exception.GlobalException;
import com.tyss.layover.exception.UserNotFoundException;
import com.tyss.layover.pojo.CommonPojo;
import com.tyss.layover.pojo.HotelDetailsPojo;
import com.tyss.layover.pojo.LayoverPojo;
import com.tyss.layover.repository.AirlineRepository;
import com.tyss.layover.repository.CommissionRepository;
import com.tyss.layover.repository.HotelDetailsRepository;
import com.tyss.layover.repository.ImageRepository;
import com.tyss.layover.repository.LayoverRequestRepository;
import com.tyss.layover.repository.LayoverResponseRepository;
import com.tyss.layover.repository.PaymentRepository;
import com.tyss.layover.util.SSSUploadFile;

import lombok.RequiredArgsConstructor;

/**
 * This is the service implementation class for LayoverRequestService interface.
 * Here you find implementation for saving, updating, fetching and deleting the
 * LayoverRequest Details
 * 
 * 
 * @author Akram Ladaf
 * @author Shrinivas Anant Tikare
 * @author Indrajit rana
 *
 */
@Service
@RequiredArgsConstructor
public class LayoverRequestResponseServiceImpl implements LayoverRequestResponseService {

	/**
	 * This Field Is Used To Invoke Persistence Layer Methods
	 */
	public final HotelDetailsRepository detailsRepository;

	/**
	 * This Field Is Used To Invoke Persistence Layer Methods
	 */
	public final AirlineRepository airlineRepository;

	/**
	 * This Field Is Used To Invoke Persistence Layer Methods
	 */
	public final LayoverRequestRepository layoverRequestRepository;

	/**
	 * This Field Is Used To Invoke Persistence Layer Methods
	 */
	public final ImageRepository imageRepository;

	/**
	 * This Field Is Used To Invoke Persistence Layer Methods
	 */
	public final LayoverResponseRepository layoverResponseRepository;

	/**
	 * This Field Is Used To Invoke Persistence Layer Methods
	 */
	public final SSSUploadFile sssUploadFile;

	/**
	 * This Field Is Used To Invoke Persistence Layer Methods
	 */
	public final PaymentRepository paymentRepository;

	/**
	 * This Field Is Used To Invoke Persistence Layer Methods
	 */
	public final CommissionRepository commissionRepository;

	/**
	 * This Method Is Used To Fetch All Hotel Details By Airline
	 */
	@Override
	public List<HotelDetailsPojo> fetchAllHotelDetails(Integer capacity, String location) {
		List<HotelDetails> findAll = detailsRepository.findAll();
		List<HotelDetailsPojo> hotelDetailsPojos = new ArrayList<>();
		findAll.stream().map(hotelDetails -> {
			if ("Accepted".equalsIgnoreCase(hotelDetails.getHotel().getUser().getStatus())
					&& hotelDetails.getHotel().getUser().getIsDeleted().equals(Boolean.FALSE)
					&& hotelDetails.getHotel().getUser().getIsBlocked().equals(Boolean.FALSE)) {
				Integer count = hotelDetails.getSingleRooms() + hotelDetails.getDoubleRooms()
						+ hotelDetails.getAccessibleRooms();
				if (count >= capacity && hotelDetails.getHotel().getLocation().equalsIgnoreCase(location)) {
					HotelDetailsPojo hotelDetailsPojo = HotelDetailsPojo.builder().build();
					BeanUtils.copyProperties(hotelDetails, hotelDetailsPojo);
					hotelDetailsPojo.setHotelDescription(hotelDetails.getHotel().getHotelDescription());
					hotelDetailsPojo.setHotelName(hotelDetails.getHotel().getHotelName());
					hotelDetailsPojo.setHotelId(hotelDetails.getHotel().getHotelId());
					hotelDetailsPojo.setHotelStar(hotelDetails.getHotel().getHotelStars());
					hotelDetailsPojo.setTotalRooms(hotelDetails.getSingleRooms() + hotelDetails.getDoubleRooms()
							+ hotelDetails.getAccessibleRooms());
					List<Image> hotelImages = imageRepository.findByHotel(hotelDetails.getHotel());
					hotelDetailsPojo.setHotelImages(hotelImages);
					hotelDetailsPojos.add(hotelDetailsPojo);
				}

			}
			return null;
		}).collect(Collectors.toList());
		return hotelDetailsPojos;
	}

	/**
	 * This Method Is Used To Save Multipart File
	 */
	@Override
	public List<String> saveMultipartFile(List<MultipartFile> locPath) {
		List<String> pathList = new ArrayList<>();
		if (!locPath.isEmpty()) {
			pathList = locPath.stream().map(sssUploadFile::uploadFile).collect(Collectors.toList());
		}
		return pathList;
	}

	/**
	 * This Method Is Used Save LayoverRequest By Airline
	 */
	@Override
	public String saveRequest(List<LayoverPojo> layoverPojos) {

		if (!layoverPojos.isEmpty()) {
			for (LayoverPojo pojo : layoverPojos) {

				Airline airline = airlineRepository.findById(pojo.getAirlineId())
						.orElseThrow(() -> new UserNotFoundException(INVALID_AIRLINE_ID));

				HotelDetails hotelDetails = detailsRepository.findById(pojo.getHotelDetailsId())
						.orElseThrow(() -> new UserNotFoundException(INVALID_HOTEL_DETAIL_ID));
				LayoverRequest layoverRequest = new LayoverRequest();
				BeanUtils.copyProperties(pojo, layoverRequest);

				layoverRequest.setTimestamp(LocalDateTime.now());
				layoverRequest.setAirline(airline);
				layoverRequest.setHotelDetails(hotelDetails);
				layoverRequestRepository.save(layoverRequest);
			}
			return LAYOVER_REQUEST_SAVE_SUCCESSFULLY;
		}
		throw new GlobalException(LAYOVER_REQUEST_LIST_IS_EMPTY);

	}

	/**
	 * This Method Is Used To Fetch All LayoverRequest By Hotel
	 */
	@Override
	public List<LayoverPojo> fetchAllLayoverRequestByHotel(CommonPojo layoverRequestPojo) {
		List<LayoverRequest> listlayoverRequest = null;
		if (layoverRequestPojo.getStartDateTime() == null || layoverRequestPojo.getEndDateTime() == null) {
			listlayoverRequest = layoverRequestRepository
					.findByHotelDetailsHotelDetailsId(layoverRequestPojo.getRequestId())
					.orElseThrow(() -> new GlobalException(NO_REQUEST_FOUND_FOR_THE_HOTEL));
		} else {
			listlayoverRequest = layoverRequestRepository
					.findByHotelDetailsHotelDetailsIdAndTimestampBetween(layoverRequestPojo.getRequestId(),
							layoverRequestPojo.getStartDateTime(), layoverRequestPojo.getEndDateTime())
					.orElseThrow(() -> new GlobalException(NO_REQUEST_FOUND_FOR_THE_HOTEL));
		}
		List<LayoverPojo> layoverRequestPojos = new ArrayList<>();
		for (LayoverRequest layoverRequest : listlayoverRequest) {
			LayoverResponse layoverResponse = layoverResponseRepository.findByLayoverRequest(layoverRequest);
			LayoverPojo layoverPojo = new LayoverPojo();
			if (layoverResponse != null) {
				layoverRequestPojos.add(setPojo(layoverRequest, layoverResponse));
			} else {
				BeanUtils.copyProperties(layoverRequest.getHotelDetails(), layoverPojo);
				BeanUtils.copyProperties(layoverRequest, layoverPojo);
				layoverPojo.setAirlineId(layoverRequest.getAirline().getAirlineId());
				layoverPojo.setAirlineName(layoverRequest.getAirline().getAirlineName());
				layoverPojo.setHotelName(layoverRequest.getHotelDetails().getHotel().getHotelName());
				layoverRequestPojos.add(layoverPojo);
			}

		}

		return layoverRequestPojos;
	}

	/**
	 * This Method Is Used Save LayoverResponse By Hotel
	 */
	@Override
	public String saveLayoverResponse(LayoverPojo layoverPojo) {
		LayoverRequest layoverRequest = layoverRequestRepository.findById(layoverPojo.getLayoverRequestId())
				.orElseThrow(() -> new GlobalException(REQUEST_IS_NOT_PRESENT));

		if ("Denied".equalsIgnoreCase(layoverPojo.getStatus()) || "Cancelled".equalsIgnoreCase(layoverPojo.getStatus())) {

			LayoverResponse layoverResponse = LayoverResponse.builder().status(layoverPojo.getStatus())
					.responseNote(layoverPojo.getResponseNote()).layoverRequest(layoverRequest).build();
			try {
				layoverResponseRepository.save(layoverResponse);
			} catch (Exception e) {
				throw new GlobalException(
						DUBLICATE_ENTRY_LAYOVER_REQUEST_ID + layoverPojo.getLayoverRequestId() + FOR_LAYOVER_RESPONSE);
			}
		} else {
			LayoverResponse layoverResponse = new LayoverResponse();
			BeanUtils.copyProperties(layoverPojo, layoverResponse);
			layoverResponse.setTotalAmount(
					BigDecimal.valueOf((layoverRequest.getHotelDetails().getSingleRoomPrice().doubleValue()
							* layoverPojo.getAcceptedSingleRooms())
							+ (layoverRequest.getHotelDetails().getDoubleRoomPrice().doubleValue()
									* layoverPojo.getAcceptedDoubleRooms())
							+ (layoverRequest.getHotelDetails().getAccessibleRoomPrice().doubleValue()
									* layoverPojo.getAcceptedAccessibleRooms())
							+ (layoverPojo.getBeveragePrice().doubleValue() * layoverPojo.getAcceptedCount())
							+ (layoverPojo.getBreakfastPrice().doubleValue() * layoverPojo.getAcceptedCount())
							+ (layoverPojo.getDinnerPrice().doubleValue() * layoverPojo.getAcceptedCount())
							+ (layoverPojo.getLunchPrice().doubleValue() * layoverPojo.getAcceptedCount())));
			layoverResponse.setLayoverRequest(layoverRequest);
			LayoverResponse response = layoverResponseRepository.save(layoverResponse);
			double commissionAmount = layoverResponse.getTotalAmount() == null ? 0
					: layoverResponse.getTotalAmount().doubleValue() * 0.20;

			Payment payment = Payment.builder().layoverResponse(response).hotelStatus(PENDING).airlineStatus(PENDING)
					.timestamp(LocalDateTime.now()).build();
			Payment savePayment = paymentRepository.save(payment);
			Commission commission = Commission.builder().payment(savePayment).commissionStatus(PENDING)
					.commissionAmount(BigDecimal.valueOf(commissionAmount)).timestamp(LocalDateTime.now()).build();
			commissionRepository.save(commission);
		}

		return LAYOVER_RESPONSE_SAVE_SUCCESSFULLY;
	}

	/**
	 * This Method Is Used To Fetch All Accepted LayoverRequest By Airline
	 */
	@Override
	public List<LayoverPojo> fetchAllAcceptedRequestByAirline(CommonPojo layoverRequestPojo) {
		List<LayoverPojo> layoverPojos = new ArrayList<>();
		List<LayoverRequest> layoverRequests = null;
		if (layoverRequestPojo.getStartDateTime() == null || layoverRequestPojo.getEndDateTime() == null) {
			layoverRequests = layoverRequestRepository.findByAirlineAirlineId(layoverRequestPojo.getRequestId())
					.orElseThrow(() -> new GlobalException(THIS_AIRLINE_DOESNOT_HAVE_ANY_REQUEST));
		} else {
			layoverRequests = layoverRequestRepository
					.findByAirlineAirlineIdAndTimestampBetween(layoverRequestPojo.getRequestId(),
							layoverRequestPojo.getStartDateTime(), layoverRequestPojo.getEndDateTime())
					.orElseThrow(() -> new GlobalException(THIS_AIRLINE_DOESNOT_HAVE_ANY_REQUEST));
		}

		for (LayoverRequest layoverRequest : layoverRequests) {
			LayoverResponse layoverResponse = layoverResponseRepository.findByLayoverRequest(layoverRequest);
			if (layoverResponse != null && "Accepted".equalsIgnoreCase(layoverResponse.getStatus())) {

				LayoverPojo layoverPojo = setPojo(layoverRequest, layoverResponse);
				layoverPojos.add(layoverPojo);

			}
		}
		return layoverPojos;
	}

	/**
	 * This Method Is Used To Fetch All Open LayoverRequest By Airline
	 */
	@Override
	public List<LayoverPojo> fetchOpenRequestByAirline(CommonPojo openRequest) {
		List<LayoverPojo> layoverPojos = new ArrayList<>();
		List<LayoverRequest> layoverRequests = null;
		if (openRequest.getStartDateTime() == null || openRequest.getEndDateTime() == null) {
			layoverRequests = layoverRequestRepository.findByAirlineAirlineId(openRequest.getRequestId())
					.orElseThrow(() -> new GlobalException(THIS_AIRLINE_DOESNOT_HAVE_ANY_REQUEST));
		} else {
			layoverRequests = layoverRequestRepository
					.findByAirlineAirlineIdAndTimestampBetween(openRequest.getRequestId(),
							openRequest.getStartDateTime(), openRequest.getEndDateTime())
					.orElseThrow(() -> new GlobalException(THIS_AIRLINE_DOESNOT_HAVE_ANY_REQUEST));
		}

		for (LayoverRequest layoverRequest : layoverRequests) {
			LayoverResponse layoverResponse = layoverResponseRepository.findByLayoverRequest(layoverRequest);
			if (layoverResponse == null) {
				LayoverPojo layoverPojo = new LayoverPojo();
				BeanUtils.copyProperties(layoverRequest.getHotelDetails(), layoverPojo);
				BeanUtils.copyProperties(layoverRequest, layoverPojo);
				layoverPojo.setAirlineId(layoverRequest.getAirline().getAirlineId());
				layoverPojo.setAirlineName(layoverRequest.getAirline().getAirlineName());
				layoverPojo.setHotelName(layoverRequest.getHotelDetails().getHotel().getHotelName());

				layoverPojos.add(layoverPojo);
			}
		}
		return layoverPojos;
	}

	/**
	 * This Method Is Used To Upload PassengerList And Loc By Airline
	 */
	@Transactional
	@Override
	public String uploadPassengerListAndLoc(MultipartFile passengerList, MultipartFile loc, Integer request) {
		LayoverRequest layoverRequest = layoverRequestRepository.findById(request)
				.orElseThrow(() -> new GlobalException(INVALID_REQUEST_ID));
		if (passengerList != null) {
			String uploadFile = sssUploadFile.uploadFile(passengerList);

			if (layoverRequest.getPassengerListPath() != null) {
				sssUploadFile.deleteS3Folder(layoverRequest.getPassengerListPath());
			}
			layoverRequest.setPassengerListPath(uploadFile);
		}
		if (loc != null) {
			String uploadFile = sssUploadFile.uploadFile(loc);

			if (layoverRequest.getLocPath() != null) {
				sssUploadFile.deleteS3Folder(layoverRequest.getLocPath());
			}
			layoverRequest.setLocPath(uploadFile);

		}
		return PASSENGER_LIST_AND_LOC_UPDATED_SUCCESSFULLY;
	}

	/**
	 * This Method Is Used To Fetch All LayoverRequest By Airline
	 */
	@Override
	public List<LayoverPojo> fetchAllRequestByAirline(CommonPojo layoverRequestPojo) {
		List<LayoverPojo> layoverPojos = new ArrayList<>();
		List<LayoverRequest> layoverRequests = null;
		if (layoverRequestPojo.getStartDateTime() == null && layoverRequestPojo.getEndDateTime() == null) {
			layoverRequests = layoverRequestRepository.findByAirlineAirlineId(layoverRequestPojo.getRequestId())
					.orElseThrow(() -> new GlobalException(THIS_AIRLINE_DOESNOT_HAVE_ANY_REQUEST));
		} else {
			layoverRequests = layoverRequestRepository
					.findByAirlineAirlineIdAndTimestampBetween(layoverRequestPojo.getRequestId(),
							layoverRequestPojo.getStartDateTime(), layoverRequestPojo.getEndDateTime())
					.orElseThrow(() -> new GlobalException(THIS_AIRLINE_DOESNOT_HAVE_ANY_REQUEST));
		}

		for (LayoverRequest layoverRequest : layoverRequests) {
			LayoverResponse layoverResponse = layoverResponseRepository.findByLayoverRequest(layoverRequest);

			if (layoverResponse != null) {
				layoverPojos.add(setPojo(layoverRequest, layoverResponse));
			} else {
				LayoverPojo layoverPojo = new LayoverPojo();
				BeanUtils.copyProperties(layoverRequest.getHotelDetails(), layoverPojo);
				BeanUtils.copyProperties(layoverRequest, layoverPojo);
				layoverPojo.setAirlineId(layoverRequest.getAirline().getAirlineId());
				layoverPojo.setAirlineName(layoverRequest.getAirline().getAirlineName());
				layoverPojo.setHotelName(layoverRequest.getHotelDetails().getHotel().getHotelName());
				layoverPojos.add(layoverPojo);
			}

		}
		return layoverPojos;
	}

	private LayoverPojo setPojo(LayoverRequest layoverRequest, LayoverResponse layoverResponse) {
		LayoverPojo layoverPojo = new LayoverPojo();
		BeanUtils.copyProperties(layoverRequest, layoverPojo);
		BeanUtils.copyProperties(layoverResponse, layoverPojo);
		BeanUtils.copyProperties(layoverRequest.getHotelDetails(), layoverPojo);
		layoverPojo.setAirlineId(layoverRequest.getAirline().getAirlineId());
		layoverPojo.setAirlineName(layoverRequest.getAirline().getAirlineName());
		layoverPojo.setHotelName(layoverRequest.getHotelDetails().getHotel().getHotelName());
		return layoverPojo;

	}

}
