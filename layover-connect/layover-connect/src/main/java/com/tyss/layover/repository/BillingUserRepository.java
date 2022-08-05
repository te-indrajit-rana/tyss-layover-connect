package com.tyss.layover.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.Airline;
import com.tyss.layover.entity.BillingUser;

public interface BillingUserRepository extends JpaRepository<BillingUser, Integer> {

	Optional<BillingUser> findByBillingUserId(Integer userid);

	Optional<BillingUser> findByAirline(Airline airline);
}
