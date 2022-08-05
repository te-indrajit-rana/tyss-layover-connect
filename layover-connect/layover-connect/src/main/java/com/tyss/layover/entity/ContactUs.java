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
public class ContactUs implements Serializable {

	private static final long serialVersionUID = -3918673922127112327L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contact_us_id", unique = true, nullable = false, precision = 10)
	private Integer contactUsId;
	
	@Column(length = 255)
	private String email;
	
	@Column(length = 255)
	private String name;
	
	@Column(name = "phone_number", precision = 19)
	private long phoneNumber;
	
	@Column(name = "project_description", length = 255)
	private String projectDescription;

}
