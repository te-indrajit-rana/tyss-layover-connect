package com.tyss.layover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.layover.entity.Hotel;
import com.tyss.layover.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

//	List<Image> findByUser(User user);
	List<Image> findByHotel(Hotel hotel);
}
