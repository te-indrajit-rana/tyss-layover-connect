package com.tyss.layover.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Career implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "career_id", unique = true, nullable = false, precision = 10)
	private Integer careerId;
	
	@Column(name = "additional_information", length = 255)
	private String additionalInformation;
	
	@Column(name = "current_company", length = 255)
	private String currentCompany;
	
	@Column(length = 255)
	private String email;
	
	@Column(name = "full_name", length = 255)
	private String fullName;
	
	@Column(name = "phone_number", precision = 19)
	private long phoneNumber;
	
	@Column(length = 255)
	private String resumePath;

}
