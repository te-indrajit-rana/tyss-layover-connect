package com.tyss.layover.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tyss.layover.entity.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = Include.NON_DEFAULT)
public class HotelAdminResponse {
	private int hotelId;
	private String hotelName;
	private String hotelTitle;
	private String hotelAddress;
	private String name;
	private String contactNumber;
	private String country;
	private String location;
	private String logoPath;
	private int hotelStars;
	private String hotelDescription;
	private String email;
	private String userType;
	private Boolean isDeleted;
	private Boolean isBlocked;
	private String status;
	private String adminNote;
	private List<Image> imageList;

}
