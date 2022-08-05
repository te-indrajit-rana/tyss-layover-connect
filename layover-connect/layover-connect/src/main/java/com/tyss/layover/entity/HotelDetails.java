// Generated with g9.

package com.tyss.layover.entity;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class HotelDetails implements Serializable {

	private static final long serialVersionUID = -356087971209913899L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_details_id", unique = true, nullable = false, precision = 10)
	private int hotelDetailsId;

	@Column(name = "single_rooms", precision = 10)
	private int singleRooms;
	@Column(name = "double_rooms", precision = 10)
	private int doubleRooms;
	@Column(name = "accessible_rooms", precision = 10)
	private int accessibleRooms;
	private int totalRooms;
	@Column(name = "single_room_price", precision = 19, scale = 2)
	private BigDecimal singleRoomPrice;
	@Column(name = "double_room_price", precision = 19, scale = 2)
	private BigDecimal doubleRoomPrice;
	@Column(name = "accessible_room_price", precision = 19, scale = 2)
	private BigDecimal accessibleRoomPrice;
	@Column(name = "beverage_price", precision = 19, scale = 2)
	private BigDecimal beveragePrice;
	@Column(name = "breakfast_price", precision = 19, scale = 2)
	private BigDecimal breakfastPrice;
	@Column(name = "lunch_price", precision = 19, scale = 2)
	private BigDecimal lunchPrice;
	@Column(name = "dinner_price", precision = 19, scale = 2)
	private BigDecimal dinnerPrice;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

}
