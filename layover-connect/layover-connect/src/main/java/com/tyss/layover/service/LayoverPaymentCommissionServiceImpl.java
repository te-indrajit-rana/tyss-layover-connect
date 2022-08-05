package com.tyss.layover.service;

import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.COMMISSION_UPDATED_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.INVALID_COMMISSION_ID;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.INVALID_PAYMENT_ID;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.INVOICE_UPLOADED_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.LAYOVER_REQUEST_IS_NOT_PRESENT_FOR_GIVEN_AIRLINE;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.NO_REQUEST_FOUND_FOR_THE_HOTEL;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.PAYMENT_STATUS_UPDATED_SUCCESSFULLY;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.layover.entity.Commission;
import com.tyss.layover.entity.LayoverRequest;
import com.tyss.layover.entity.LayoverResponse;
import com.tyss.layover.entity.Payment;
import com.tyss.layover.exception.GlobalException;
import com.tyss.layover.exception.UserNotFoundException;
import com.tyss.layover.pojo.CommissionUpdatePojo;
import com.tyss.layover.pojo.CommonPojo;
import com.tyss.layover.pojo.PaymentCommissionPojo;
import com.tyss.layover.repository.AirlineRepository;
import com.tyss.layover.repository.CommissionRepository;
import com.tyss.layover.repository.HotelDetailsRepository;
import com.tyss.layover.repository.LayoverRequestRepository;
import com.tyss.layover.repository.LayoverResponseRepository;
import com.tyss.layover.repository.PaymentRepository;
import com.tyss.layover.util.SSSUploadFile;

import lombok.RequiredArgsConstructor;

/**
 * This is the service implementation class for LayoverPaymentCommissionService
 * interface. Here you find implementation for saving, updating, fetching the
 * LayoverPaymentCommissionService Details
 * 
 * 
 * @author Akram
 * @author Shrinivas Anant Tikare
 * @author Indrajit rana
 *
 */
@Service
@RequiredArgsConstructor
public class LayoverPaymentCommissionServiceImpl implements LayoverPaymentCommissionService {

	/**
	 * This Field Is Used To Invoke The Persistence Layer Methods
	 */
	public final HotelDetailsRepository hotelDetailsRepository;

	/**
	 * This Field Is Used To Invoke The Persistence Layer Methods
	 */
	public final AirlineRepository airlineRepository;

	/**
	 * This Field Is Used To Invoke The Persistence Layer Methods
	 */
	public final LayoverRequestRepository layoverRequestRepository;

	/**
	 * This Field Is Used To Invoke The Persistence Layer Methods
	 */
	public final LayoverResponseRepository layoverResponseRepository;

	/**
	 * This Field Is Used To Invoke The Persistence Layer Methods
	 */
	public final PaymentRepository paymentRepository;

	/**
	 * This Field Is Used To Invoke The Business Layer Methods
	 */
	public final SSSUploadFile sssUploadFile;

	public final ObjectMapper objectMapper;

	/**
	 * This Field Is Used To Invoke The Persistence Layer Methods
	 */
	public final CommissionRepository commissionRepository;

	/**
	 * This Method Is Used Fetch Payment Details By Hotel Based On HotelDetails Id
	 */
	@Override
	public List<PaymentCommissionPojo> fetchPaymentDetailsByHotel(CommonPojo layoverRequestPojo) {
		List<LayoverRequest> layoverRequestList = layoverRequestRepository
				.findByHotelDetailsHotelDetailsId(layoverRequestPojo.getRequestId())
				.orElseThrow(() -> new GlobalException(NO_REQUEST_FOUND_FOR_THE_HOTEL));
		List<PaymentCommissionPojo> paymentCommissionPojos = new ArrayList<>();
		for (LayoverRequest layoverRequest : layoverRequestList) {
			LayoverResponse layoverResponse = layoverResponseRepository.findByLayoverRequest(layoverRequest);
			if (layoverResponse != null) {
				Payment payment = null;
				if (layoverRequestPojo.getStartDateTime() != null) {
					payment = paymentRepository.findByLayoverResponseAndTimestampBetween(layoverResponse,
							layoverRequestPojo.getStartDateTime(), layoverRequestPojo.getEndDateTime());
				} else {
					payment = paymentRepository.findByLayoverResponse(layoverResponse);
				}

				PaymentCommissionPojo paymentCommissionPojo = new PaymentCommissionPojo();
				BeanUtils.copyProperties(layoverRequest, paymentCommissionPojo);
				BeanUtils.copyProperties(layoverResponse, paymentCommissionPojo);
				BeanUtils.copyProperties(layoverRequest.getAirline(), paymentCommissionPojo);
				BeanUtils.copyProperties(layoverRequest.getHotelDetails(), paymentCommissionPojo);
				BeanUtils.copyProperties(layoverRequest.getHotelDetails().getHotel(), paymentCommissionPojo);
				if (payment != null) {
					BeanUtils.copyProperties(payment, paymentCommissionPojo);
					paymentCommissionPojos.add(paymentCommissionPojo);
				}
			}
		}
		return paymentCommissionPojos;
	}

	/**
	 * This Method Is Used To Upload Invoice By Hotel Based On PaymentId
	 */
	@Transactional
	@Override
	public String uploadInvoice(MultipartFile invoice, Integer paymentId) {
		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new UserNotFoundException(INVALID_PAYMENT_ID));
		String uploadFile = sssUploadFile.uploadFile(invoice);
		payment.setInvoicePath(uploadFile);
		return INVOICE_UPLOADED_SUCCESSFULLY;
	}

	/**
	 * This Method Is Used Fetch Payment Details By Airline Based On Airline Id
	 */
	@Override
	public List<PaymentCommissionPojo> fetchPaymentDetailsByAirline(CommonPojo layoverRequestPojo) {

		List<LayoverRequest> layoverRequests = layoverRequestRepository
				.findByAirlineAirlineId(layoverRequestPojo.getRequestId())
				.orElseThrow(() -> new GlobalException(LAYOVER_REQUEST_IS_NOT_PRESENT_FOR_GIVEN_AIRLINE));
		List<PaymentCommissionPojo> paymentCommissionPojos = new ArrayList<>();
		for (LayoverRequest layoverRequest : layoverRequests) {
			LayoverResponse layoverResponse = layoverResponseRepository.findByLayoverRequest(layoverRequest);
			if (layoverResponse != null) {
				Payment payment = null;
				if (layoverRequestPojo.getStartDateTime() != null) {
					payment = paymentRepository.findByLayoverResponseAndTimestampBetween(layoverResponse,
							layoverRequestPojo.getStartDateTime(), layoverRequestPojo.getEndDateTime());
				} else {
					payment = paymentRepository.findByLayoverResponse(layoverResponse);
				}
				PaymentCommissionPojo paymentCommissionPojo = new PaymentCommissionPojo();
				BeanUtils.copyProperties(layoverRequest, paymentCommissionPojo);
				BeanUtils.copyProperties(layoverResponse, paymentCommissionPojo);
				BeanUtils.copyProperties(layoverRequest.getAirline(), paymentCommissionPojo);
				BeanUtils.copyProperties(layoverRequest.getHotelDetails(), paymentCommissionPojo);
				BeanUtils.copyProperties(layoverRequest.getHotelDetails().getHotel(), paymentCommissionPojo);
				if (payment != null) {
					BeanUtils.copyProperties(payment, paymentCommissionPojo);
					paymentCommissionPojos.add(paymentCommissionPojo);
				}

			}
		}

		return paymentCommissionPojos;
	}

	/**
	 * This Method Is Used To Update Layover Payment details
	 */
	@Transactional
	@Override
	public String updateLayoverPayment(PaymentCommissionPojo paymentCommissionPojo) {
		Payment payment = paymentRepository.findById(paymentCommissionPojo.getPaymentId())
				.orElseThrow(() -> new GlobalException(INVALID_PAYMENT_ID));
		payment.setAirlineStatus(paymentCommissionPojo.getAirlineStatus());
		payment.setHotelStatus(paymentCommissionPojo.getHotelStatus());
		payment.setHotelAcknowledgement(paymentCommissionPojo.getHotelAcknowledgement());
		return PAYMENT_STATUS_UPDATED_SUCCESSFULLY;
	}

	/**
	 * This Method Is Used To Fetch Commission Details By Admin
	 */
	@Override
	public List<List<PaymentCommissionPojo>> fetchCommissionByAdmin() {
		List<List<PaymentCommissionPojo>> adminRequest = new ArrayList<>();
		List<PaymentCommissionPojo> paymentAcceptedList = new ArrayList<>();
		List<PaymentCommissionPojo> paymentDeniedList = new ArrayList<>();
		List<PaymentCommissionPojo> paymentPendingList = new ArrayList<>();
		List<LayoverRequest> layoverRequests = layoverRequestRepository.findAll();

		if (!layoverRequests.isEmpty()) {

			for (LayoverRequest layoverRequest : layoverRequests) {

				LayoverResponse layoverResponse = layoverResponseRepository.findByLayoverRequest(layoverRequest);

				if (layoverResponse != null && "Accepted".equalsIgnoreCase(layoverResponse.getStatus())) {

					paymentAcceptedList.add(layoverAccepted(layoverRequest, layoverResponse));
				} else if (layoverResponse != null && "Denied".equalsIgnoreCase(layoverResponse.getStatus())) {

					paymentDeniedList.add(layoverDenied(layoverRequest, layoverResponse));
				} else {

					paymentPendingList.add(layoverPending(layoverRequest, layoverResponse));
				}

			}

		}
		adminRequest.add(paymentAcceptedList);
		adminRequest.add(paymentPendingList);
		adminRequest.add(paymentDeniedList);
		return adminRequest;
	}

	/**
	 * This Method Is Used To Upload Invoice And Update Commission Details By Hotel
	 */
	@Transactional
	@Override
	public String uploadCommissionInvoice(String pojo, MultipartFile file) {
		CommissionUpdatePojo updatePojo = new CommissionUpdatePojo();
		try {
			updatePojo = objectMapper.readValue(pojo, CommissionUpdatePojo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Commission commission = commissionRepository.findById(updatePojo.getCommissionId())
				.orElseThrow(() -> new GlobalException(INVALID_COMMISSION_ID));
		commission.setCommissionStatus(updatePojo.getMessage());
		if (file != null && !file.isEmpty()) {
			String uploadFile = sssUploadFile.uploadFile(file);
			commission.setCommissionInvoicePath(uploadFile);
		}
		return COMMISSION_UPDATED_SUCCESSFULLY;
	}

	/**
	 * This Method Is Used To Fetch Commission Details By Hotel Based On
	 * HotelDetails Id
	 */
	@Override
	public List<PaymentCommissionPojo> fetchAllCommissionByHotel(CommonPojo layoverRequestPojo) {

		List<LayoverRequest> layoverRequestList = layoverRequestRepository
				.findByHotelDetailsHotelDetailsId(layoverRequestPojo.getRequestId())
				.orElseThrow(() -> new GlobalException(NO_REQUEST_FOUND_FOR_THE_HOTEL));
		List<PaymentCommissionPojo> paymentCommissionPojos = new ArrayList<>();

		for (LayoverRequest layoverRequest : layoverRequestList) {
			LayoverResponse layoverResponse = layoverResponseRepository.findByLayoverRequest(layoverRequest);
			Payment payment = paymentRepository.findByLayoverResponse(layoverResponse);
			if (layoverResponse != null && payment != null) {
				Commission commission = null;
				if (layoverRequestPojo.getStartDateTime() != null) {
					commission = commissionRepository.findByPaymentAndTimestampBetween(payment,
							layoverRequestPojo.getStartDateTime(), layoverRequestPojo.getEndDateTime());
				} else {
					commission = commissionRepository.findByPayment(payment);
				}
				if (commission != null) {
					PaymentCommissionPojo paymentCommissionPojo = new PaymentCommissionPojo();
					BeanUtils.copyProperties(commission, paymentCommissionPojo);
					BeanUtils.copyProperties(layoverRequest, paymentCommissionPojo);
					BeanUtils.copyProperties(payment, paymentCommissionPojo);
					BeanUtils.copyProperties(layoverResponse, paymentCommissionPojo);

					paymentCommissionPojos.add(paymentCommissionPojo);
				}

			}
		}
		return paymentCommissionPojos;
	}

	private PaymentCommissionPojo layoverPending(LayoverRequest layoverRequest, LayoverResponse layoverResponse) {
		PaymentCommissionPojo paymentCommissionPojo = new PaymentCommissionPojo();
		if (layoverResponse != null) {
			paymentCommissionPojo.setStatus(layoverResponse.getStatus());
		}

		paymentCommissionPojo.setLayoverRequestId(layoverRequest.getLayoverRequestId());
		paymentCommissionPojo.setLocation(layoverRequest.getHotelDetails().getHotel().getLocation());
		paymentCommissionPojo.setAirlineName(layoverRequest.getAirline().getAirlineName());
		paymentCommissionPojo.setHotelName(layoverRequest.getHotelDetails().getHotel().getHotelName());

		return paymentCommissionPojo;
	}

	private PaymentCommissionPojo layoverAccepted(LayoverRequest layoverRequest, LayoverResponse layoverResponse) {
		Payment payment = paymentRepository.findByLayoverResponse(layoverResponse);
		Commission commission = commissionRepository.findByPayment(payment);
		PaymentCommissionPojo paymentCommissionPojo = new PaymentCommissionPojo();
		if (payment != null) {
			BeanUtils.copyProperties(payment, paymentCommissionPojo);
		}
		if (commission != null) {
			BeanUtils.copyProperties(commission, paymentCommissionPojo);
		}
		BeanUtils.copyProperties(layoverRequest, paymentCommissionPojo);
		BeanUtils.copyProperties(layoverResponse, paymentCommissionPojo);
		paymentCommissionPojo.setLocation(layoverRequest.getHotelDetails().getHotel().getLocation());
		paymentCommissionPojo.setAirlineName(layoverRequest.getAirline().getAirlineName());
		paymentCommissionPojo.setHotelName(layoverRequest.getHotelDetails().getHotel().getHotelName());
		return paymentCommissionPojo;
	}

	private PaymentCommissionPojo layoverDenied(LayoverRequest layoverRequest, LayoverResponse layoverResponse) {
		PaymentCommissionPojo paymentCommissionPojo = new PaymentCommissionPojo();
		paymentCommissionPojo.setLayoverRequestId(layoverRequest.getLayoverRequestId());
		paymentCommissionPojo.setLocation(layoverRequest.getHotelDetails().getHotel().getLocation());
		paymentCommissionPojo.setAirlineName(layoverRequest.getAirline().getAirlineName());
		paymentCommissionPojo.setHotelName(layoverRequest.getHotelDetails().getHotel().getHotelName());
		paymentCommissionPojo.setStatus(layoverResponse.getStatus());
		return paymentCommissionPojo;
	}

}
