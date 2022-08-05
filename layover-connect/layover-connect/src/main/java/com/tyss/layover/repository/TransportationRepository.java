package com.tyss.layover.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.Transportation;

public interface TransportationRepository extends JpaRepository<Transportation, Integer> {

	Optional<Transportation> findByTransportationId(Integer transportationId);

	List<Transportation> findTop10ByOrderByRegistrationDateDesc();
}
