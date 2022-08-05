package com.tyss.layover.pojo;

import static com.tyss.layover.constant.HotelConstant.HOTEL_ADDRESS_CANNOT_BE_BLANK;
import static com.tyss.layover.constant.HotelConstant.HOTEL_ADDRESS_CANNOT_BE_NULL;
import static com.tyss.layover.constant.HotelConstant.HOTEL_COUNTRY_CANNOT_BE_BLANK;
import static com.tyss.layover.constant.HotelConstant.HOTEL_COUNTRY_CANNOT_BE_NULL;
import static com.tyss.layover.constant.HotelConstant.HOTEL_LOCATION_CANNOT_BE_BLANK;
import static com.tyss.layover.constant.HotelConstant.HOTEL_LOCATION_CANNOT_BE_NULL;
import static com.tyss.layover.constant.HotelConstant.HOTEL_NAME_CANNOT_BE_BLANK;
import static com.tyss.layover.constant.HotelConstant.HOTEL_NAME_CANNOT_BE_NULL;
import static com.tyss.layover.constant.HotelConstant.HOTEL_PERSON_NAME_CANNOT_BE_BLANK;
import static com.tyss.layover.constant.HotelConstant.HOTEL_PERSON_NAME_CANNOT_BE_NULL;
import static com.tyss.layover.constant.HotelConstant.HOTEL_TITLE_CANNOT_BE_BLANK;
import static com.tyss.layover.constant.HotelConstant.HOTEL_TITLE_CANNOT_BE_NULL;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.tyss.layover.entity.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelPojo {
	private int hotelId;
	@NotNull(message = HOTEL_NAME_CANNOT_BE_NULL)
	@NotBlank(message = HOTEL_NAME_CANNOT_BE_BLANK)
	private String hotelName;
	@NotNull(message = HOTEL_TITLE_CANNOT_BE_NULL)
	@NotBlank(message = HOTEL_TITLE_CANNOT_BE_BLANK)
	private String hotelTitle;
	@NotNull(message = HOTEL_ADDRESS_CANNOT_BE_NULL)
	@NotBlank(message = HOTEL_ADDRESS_CANNOT_BE_BLANK)
	private String hotelAddress;
	private String hotelContactNumber;
	@NotNull(message = HOTEL_PERSON_NAME_CANNOT_BE_NULL)
	@NotBlank(message = HOTEL_PERSON_NAME_CANNOT_BE_BLANK)
	private String name;
//	@Pattern(regexp = "^\\+[0-9]{1,3}\\ [0-9]{4,14}(?:x.+)?$",message = PLEASE_ENTER_THE_VALID_PHONE_NUMBER)
	private String contactNumber;
	@NotNull(message = HOTEL_COUNTRY_CANNOT_BE_NULL)
	@NotBlank(message = HOTEL_COUNTRY_CANNOT_BE_BLANK)
	private String country;
	@NotNull(message = HOTEL_LOCATION_CANNOT_BE_NULL)
	@NotBlank(message = HOTEL_LOCATION_CANNOT_BE_BLANK)
	private String location;
	private String logoPath;
	private int hotelStars;
	private String hotelDescription;
//	@Email(message = PLEASE_ENTER_THE_VALID_EMAIL_ADDRESS)
//	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")
	private String email;
	private String userType;
	private Boolean isDeleted;
	private Boolean isBlocked;
	private String status;
	private String adminNote;
	private List<Image> imageList;

}
