package com.tyss.layover.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelMonthwiseFilterRequest {
	private Integer hotelId;

	private String status;

	private Integer year;

}
