package com.tyss.layover.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.layover.entity.Commission;
import com.tyss.layover.entity.Payment;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {

	Commission findByPayment(Payment payment);

	List<Commission> findAllByCommissionAmountNotNull();
	
	Commission  findByPaymentAndTimestampBetween(Payment payment,
			LocalDateTime startdate,LocalDateTime endDate);

}
