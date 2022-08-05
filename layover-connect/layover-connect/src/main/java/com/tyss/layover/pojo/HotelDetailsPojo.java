package com.tyss.layover.pojo;

import java.math.BigDecimal;
import java.util.List;

import com.tyss.layover.entity.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDetailsPojo {
	
	private int hotelDetailsId;
	private int singleRooms;
	private int doubleRooms;
	private int accessibleRooms;
	private BigDecimal singleRoomPrice;
	private BigDecimal doubleRoomPrice;
	private BigDecimal accessibleRoomPrice;
	private BigDecimal beveragePrice;
	private BigDecimal breakfastPrice;
	private BigDecimal lunchPrice;
	private BigDecimal dinnerPrice;
	private int totalRooms;
	private Integer hotelId;
	private String hotelName;
	private int hotelStar;
	private String hotelDescription;
	private List<Image> hotelImages;
	
	


}
