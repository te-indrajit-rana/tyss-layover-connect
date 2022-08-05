package com.tyss.layover.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.pojo.CommonPojo;
import com.tyss.layover.pojo.PaymentCommissionPojo;

public interface LayoverPaymentCommissionService {

	List<PaymentCommissionPojo> fetchPaymentDetailsByHotel(CommonPojo layoverRequestPojo);
	
	String uploadInvoice(MultipartFile invoice ,Integer paymentId);
	
	List<PaymentCommissionPojo> fetchPaymentDetailsByAirline(CommonPojo layoverRequestPojo);
	
	String updateLayoverPayment(PaymentCommissionPojo payment);
	
	List<List<PaymentCommissionPojo>> fetchCommissionByAdmin();
	
	String uploadCommissionInvoice(String pojo,  MultipartFile file);
	
	List<PaymentCommissionPojo> fetchAllCommissionByHotel(CommonPojo layoverRequestPojo);
	
	
}
