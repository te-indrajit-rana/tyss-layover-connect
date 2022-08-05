package com.tyss.layover.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Integer> {

	Optional<UserDetails> findByEmail(String email);

	List<UserDetails> findAllByStatus(String status);
	
}
