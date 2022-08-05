package com.tyss.layover.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.ExternalHandlingCompany;

public interface ExternCompanyRepository extends JpaRepository<ExternalHandlingCompany, Integer> {
	Optional<ExternalHandlingCompany> findByExternalHandlingCompanyId(Integer externalHandlingCompanyId);
	
	List<ExternalHandlingCompany> findTop10ByOrderByRegistrationDateDesc();
}
