package com.tyss.layover.entity;
import java.io.Serializable;
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
public class Airline implements Serializable {

	private static final long serialVersionUID = -913926433513671280L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "airline_id", unique = true, nullable = false, precision = 10)
	private int airlineId;
	@Column(name = "airline_name", length = 255)
	private String airlineName;
	@Column(name = "airline_title", length = 255)
	private String airlineTitle;
	@Column(name = "airline_address", length = 255)
	private String airlineAddress;
	@Column(length = 255)
	private String name;
	@Column(name = "contact_number",unique = true, nullable = false, length = 45)
	private String contactNumber;
	@Column(length = 255)
	private String country;
	@Column(length = 255)
	private String location;
	@Column(name = "logo_path", length = 255)
	private String logoPath;
	private String generalLocPath;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss",timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime registrationDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private UserDetails user;

	

}
