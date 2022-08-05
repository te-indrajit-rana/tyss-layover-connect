package com.tyss.layover.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class UserDetails implements Serializable {

	private static final long serialVersionUID = 162996961028812725L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false, precision = 10)
	private int userId;
	@Column(unique = true, length = 100)
	private String email;
	@Column(name = "user_type", nullable = false, length = 45)
	private String userType;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss",timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime timestamp;
	@Column(precision = 10)
	private Integer otp;
	@Column(name = "is_deleted", precision = 3)
	private Boolean isDeleted;
	@Column(length = 45)
	private String status;
	@Column(name = "admin_note", length = 599)
	private String adminNote;
	private Boolean isBlocked;
	private Integer objectId;

}
