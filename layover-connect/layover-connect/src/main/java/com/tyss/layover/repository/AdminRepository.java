package com.tyss.layover.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.layover.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
