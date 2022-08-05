package com.tyss.layover.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

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
public class Commission implements Serializable {

	private static final long serialVersionUID = 5814044411639358370L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "commission_id", unique = true, nullable = false, precision = 10)
	private int commissionId;
	@Column(name = "commission_amount", precision = 19, scale = 2)
	private BigDecimal commissionAmount;
	@Column(length = 255)
	private String commissionInvoicePath;
	@Column(length = 255)
	private String commissionStatus;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss",timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime timestamp;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;

}
