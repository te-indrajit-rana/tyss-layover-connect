
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Hotel implements Serializable {

	private static final long serialVersionUID = -8869884858218669273L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_id", unique = true, nullable = false, precision = 10)
	private int hotelId;
	@Column(name = "hotel_name", length = 255)
	private String hotelName;
	@Column(name = "hotel_title", length = 255)
	private String hotelTitle;
	@Column(name = "hotel_address", length = 255)
	private String hotelAddress;
	private String hotelContactNumber;
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
	@Column(name = "hotel_stars", precision = 10)
	private int hotelStars;
	@Column(name = "hotel_description", length = 255)
	private String hotelDescription;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss",timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime registrationDate;
	@JsonIgnoreProperties
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private UserDetails user;

}
