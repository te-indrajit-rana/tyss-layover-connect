package com.tyss.layover.controller;

import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.COMMISSION_DETAILS_FETCH_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.COMMISSION_FETCH_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.PAYMENT_DETAILS_FETCH_SUCCESSFULLY;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.THERE_ARE_NO_COMMISSION_PRESENT;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.THERE_NO_COMMISSION_FOR_GIVEN_HOTEL;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.THIS_API_IS_USED_TO_FETCH_PAYMENT_DETAILS_BASED_ON_FROM_DATE_AND_END_DATE_AND_AIRLINE_ID_BY_AIRLINE;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.THIS_API_IS_USED_TO_FETCH_PAYMENT_DETAILS_BASED_ON_FROM_DATE_AND_END_DATE_HOTEL_DETAILS_ID_BY_HOTEL;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.THIS_API_IS_USED_TO_FETCH_THE_COMMISSION_DETAILS_BY_HOTEL;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.THIS_API_IS_USED_TO_UPDATE_THE_COMMISSION_DETAILS;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.THIS_API_IS_USED_TO_UPDATE_THE_PAYMENT_DETAILS;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.THIS_API_IS_USED_TO_UPLOAD_INVOICE_BASED_ON_PAYMENT_ID_BY_HOTEL;
import static com.tyss.layover.constant.LayoverPaymentCommissionConstant.THIS_API_USED_TO_FETCH_THE_COMMISSION_DETAILS_BY_ADMIN;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.pojo.CommonPojo;
import com.tyss.layover.pojo.PaymentCommissionPojo;
import com.tyss.layover.response.LayoverResponseBody;
import com.tyss.layover.service.LayoverPaymentCommissionService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

/**
 * This is a controller class for LayoverPayment And Commission Page . Here you
 * find GET, PUT, controllers for the LayoverPayment And Commission Page. Here
 * you can find the URL path for all the Airline Page services.
 * 
 * 
 * @author Akram
 * @author Shrinivas Anant Tikare
 * @author Indrajit rana
 *
 */

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/layover/payment-commission")
//@SecurityRequirement(name = "layover-api")
public class LayoverPaymentCommissionController {
	/**
	 * This Field Is Used To Invoke The Business Layer Method
	 */
	public final LayoverPaymentCommissionService layoverPaymentService;

	/**
	 * @param hotelDetailId
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_FETCH_PAYMENT_DETAILS_BASED_ON_FROM_DATE_AND_END_DATE_HOTEL_DETAILS_ID_BY_HOTEL)
	@PostMapping("/fetchPaymentDetailsByHotel")
	public ResponseEntity<LayoverResponseBody> fetchPaymentDetailsByHotel(@RequestBody CommonPojo layoverRequestPojo) {
		List<PaymentCommissionPojo> paymentDetails = layoverPaymentService
				.fetchPaymentDetailsByHotel(layoverRequestPojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(PAYMENT_DETAILS_FETCH_SUCCESSFULLY).data(paymentDetails).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

	/**
	 * @param multipartFile
	 * @param paymentId
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_UPLOAD_INVOICE_BASED_ON_PAYMENT_ID_BY_HOTEL)
	@PutMapping(value = "/upload-invoice/{paymentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<LayoverResponseBody> uploadInvoice(@RequestPart MultipartFile multipartFile,
			@PathVariable Integer paymentId) {
		String uploadInvoice = layoverPaymentService.uploadInvoice(multipartFile, paymentId);

		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false).message(uploadInvoice)
				.build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

	/**
	 * @param airlineId
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_FETCH_PAYMENT_DETAILS_BASED_ON_FROM_DATE_AND_END_DATE_AND_AIRLINE_ID_BY_AIRLINE)
	@PostMapping("/fetchPaymentDetailsByAirline")
	public ResponseEntity<LayoverResponseBody> fetchPaymentDetailsByAirline(
			@RequestBody CommonPojo layoverRequestPojo) {
		List<PaymentCommissionPojo> paymentDetails = layoverPaymentService
				.fetchPaymentDetailsByAirline(layoverRequestPojo);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false)
				.message(PAYMENT_DETAILS_FETCH_SUCCESSFULLY).data(paymentDetails).build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

	/**
	 * @param paymentCommissionPojo
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_UPDATE_THE_PAYMENT_DETAILS)
	@PutMapping(value = "/update-paymentDetails")
	public ResponseEntity<LayoverResponseBody> updatePaymentDetails(
			@RequestBody PaymentCommissionPojo paymentCommissionPojo) {
		String updatePayment = layoverPaymentService.updateLayoverPayment(paymentCommissionPojo);

		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false).message(updatePayment)
				.build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}

	/**
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_USED_TO_FETCH_THE_COMMISSION_DETAILS_BY_ADMIN)
	@GetMapping("/fetchComissionByAdmin")
	public ResponseEntity<LayoverResponseBody> fetchCommissionDetail() {
		List<List<PaymentCommissionPojo>> commission = layoverPaymentService.fetchCommissionByAdmin();
		LayoverResponseBody layoverResponseBody = null;
		if (commission.isEmpty()) {
			layoverResponseBody = LayoverResponseBody.builder().isError(false)
					.message(THERE_ARE_NO_COMMISSION_PRESENT).build();
		} else {
			layoverResponseBody = LayoverResponseBody.builder().isError(false)
					.message(COMMISSION_DETAILS_FETCH_SUCCESSFULLY).data(commission).build();
		}
		return ResponseEntity.ok().body(layoverResponseBody);
	}

	/**
	 * @param commonPojo
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_FETCH_THE_COMMISSION_DETAILS_BY_HOTEL)
	@PostMapping("/fetchCommissionByHotel")
	public ResponseEntity<LayoverResponseBody> fetchCommissionDetailByHotel(@RequestBody CommonPojo commonPojo) {
		List<PaymentCommissionPojo> commissions = layoverPaymentService.fetchAllCommissionByHotel(commonPojo);
		LayoverResponseBody layoverResponseBody = null;
		if (commissions.isEmpty()) {
			layoverResponseBody = LayoverResponseBody.builder().isError(false)
					.message(THERE_NO_COMMISSION_FOR_GIVEN_HOTEL).build();
		} else {
			layoverResponseBody = LayoverResponseBody.builder().isError(false).message(COMMISSION_FETCH_SUCCESSFULLY)
					.data(commissions).build();
		}
		return ResponseEntity.ok().body(layoverResponseBody);
	}

	/**
	 * @param message
	 * @param file
	 * @return ResponseEntity
	 */
	@Operation(summary = THIS_API_IS_USED_TO_UPDATE_THE_COMMISSION_DETAILS)
	@PutMapping(value = "Update-Commission", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<LayoverResponseBody> updateCommission(@RequestPart String message,
			@RequestPart( required = false) MultipartFile multipartFile) {
		String commission = layoverPaymentService.uploadCommissionInvoice(message, multipartFile);
		LayoverResponseBody layoverResponseBody = LayoverResponseBody.builder().isError(false).message(commission)
				.build();
		return ResponseEntity.ok().body(layoverResponseBody);
	}
}
