package com.tyss.layover.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = Include.NON_DEFAULT)
public class BillingUser implements Serializable {

	private static final long serialVersionUID = -8352645803678333306L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "billing_user_id", unique = true, nullable = false, precision = 10)
	private int billingUserId;
	@Column(name = "employee_id", length = 255)
	private String employeeId;
	@Column(length = 255)
	private String name;
	@Column(length = 255)
	private String password;
	@Column(precision = 19)
	private long pin;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "airline_id")
	private Airline airline;
}
