package com.tyss.layover.service;

import static com.tyss.layover.constant.AdminConstants.DENIED;
import static com.tyss.layover.constant.AdminConstants.PENDING;
import static com.tyss.layover.constant.AdminConstants.RECIVIED;
import static com.tyss.layover.constant.HotelConstant.ACCEPTED;
import static com.tyss.layover.constant.HotelConstant.HOTEL;
import static com.tyss.layover.constant.HotelConstant.HOTEL_DELETED_SUCCESSFULLY;
import static com.tyss.layover.constant.HotelConstant.HOTEL_DETAILS_NOT_FOUND;
import static com.tyss.layover.constant.HotelConstant.HOTEL_HAS_BEEN_BLOCK_BY_ADMIN;
import static com.tyss.layover.constant.HotelConstant.INVALID_VALUE;
import static com.tyss.layover.constant.HotelConstant.RECIEVED;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.layover.constant.LayoverConstant;
import com.tyss.layover.entity.Hotel;
import com.tyss.layover.entity.HotelDetails;
import com.tyss.layover.entity.Image;
import com.tyss.layover.entity.LayoverRequest;
import com.tyss.layover.entity.LayoverResponse;
import com.tyss.layover.entity.Payment;
import com.tyss.layover.entity.UserDetails;
import com.tyss.layover.exception.EmailInterruptionException;
import com.tyss.layover.exception.HotelDetailsNotFoundException;
import com.tyss.layover.pojo.HotelMonthwiseFilterRequest;
import com.tyss.layover.pojo.HotelPojo;
import com.tyss.layover.pojo.UserDeletePojo;
import com.tyss.layover.repository.HotelDetailsRepository;
import com.tyss.layover.repository.HotelRepository;
import com.tyss.layover.repository.ImageRepository;
import com.tyss.layover.repository.LayoverRequestRepository;
import com.tyss.layover.repository.LayoverResponseRepository;
import com.tyss.layover.repository.PaymentRepository;
import com.tyss.layover.response.HotelDashbordCountResponse;
import com.tyss.layover.util.SSSUploadFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This is the service implementation class for HotelService interface. Here you
 * find implementation for saving, updating, fetching and deleting the Hotel
 * Details
 * 
 * 
 * @author Akram
 * @author Shrinivas Anant Tikare
 * @author Indrajit rana
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

	/**
	 * This Field Invoke The Persistence Layer Methods
	 */
	private final HotelRepository hotelRepository;

	/**
	 * This Field Invoke The Util Layer Methods
	 */
	private final SSSUploadFile sssUploadFile;

	/**
	 * This Field Invoke The Persistence Layer Methods
	 */
	private final ImageRepository imageRepository;

	/**
	 * This Field Invoke The Persistence Layer Methods
	 */
	private final HotelDetailsRepository hotelDetailsRepository;

	/**
	 * This Method is Implemented to Save the Hotel Details
	 */

	private final LayoverRequestRepository layoverRequestRepository;

	private final LayoverResponseRepository layoverResponseRepository;

	private final PaymentRepository paymentRepository;

	@Transactional
	@Override
	public HotelPojo saveHotel(HotelPojo hotelPojo) {

		Hotel hotel = new Hotel();
		UserDetails userDetails = UserDetails.builder().email(hotelPojo.getEmail()).userType(HOTEL)
				.isDeleted(Boolean.FALSE).isBlocked(Boolean.FALSE).status(PENDING).build();

		BeanUtils.copyProperties(hotelPojo, hotel);
		hotel.setUser(userDetails);
		hotel.setRegistrationDate(LocalDateTime.now());
		Hotel saveHotel = new Hotel();
		try {
			saveHotel = hotelRepository.save(hotel);
		} catch (Exception exception) {
			log.info(exception.getMessage());
			log.error(LayoverConstant.EMAIL_ALREADY_REGISTER);
			throw new EmailInterruptionException(LayoverConstant.EMAIL_ALREADY_REGISTER);
		}
		HotelDetails hotelDetails = HotelDetails.builder().hotel(saveHotel).build();
		hotelDetailsRepository.save(hotelDetails);
		saveHotel.getUser().setObjectId(saveHotel.getHotelId());
		BeanUtils.copyProperties(saveHotel, hotelPojo);
		hotelPojo.setEmail(saveHotel.getUser().getEmail());
		hotelPojo.setUserType(saveHotel.getUser().getUserType());
		hotelPojo.setStatus(saveHotel.getUser().getStatus());
		hotelPojo.setAdminNote(saveHotel.getUser().getAdminNote());
		hotelPojo.setIsDeleted(saveHotel.getUser().getIsDeleted());
		hotelPojo.setIsBlocked(saveHotel.getUser().getIsBlocked());

		return hotelPojo;

	}// End Of The Registration

	/**
	 * This Method Is Implemented To Fetch The Hotel details By ID
	 */
	@Override
	public HotelPojo fetchById(Integer id) {
		Hotel hotel = hotelRepository.findById(id)
				.orElseThrow(() -> new HotelDetailsNotFoundException(HOTEL_DETAILS_NOT_FOUND));
		HotelPojo hotelPojo = new HotelPojo();
		BeanUtils.copyProperties(hotel, hotelPojo);
		hotelPojo.setEmail(hotel.getUser().getEmail());
		hotelPojo.setUserType(hotel.getUser().getUserType());
		hotelPojo.setStatus(hotel.getUser().getStatus());
		hotelPojo.setAdminNote(hotel.getUser().getAdminNote());
		hotelPojo.setIsDeleted(hotel.getUser().getIsDeleted());
		hotelPojo.setIsBlocked(hotel.getUser().getIsBlocked());
		List<Image> findByHotel = imageRepository.findByHotel(hotel);
		hotelPojo.setImageList(findByHotel);

		return hotelPojo;
	}// End Of The FetchById

	/**
	 * This Method Is Implemented To Delete The Hotel Detail By ID
	 */
	@Transactional
	@Override
	public String deleteById(UserDeletePojo userDeletePojo) {
		Hotel hotel = hotelRepository.findById(userDeletePojo.getId())
				.orElseThrow(() -> new HotelDetailsNotFoundException(HOTEL_DETAILS_NOT_FOUND));
		if (Boolean.TRUE.equals(userDeletePojo.getIsBlock())) {
			hotel.getUser().setIsBlocked(Boolean.TRUE);
			log.info(HOTEL_HAS_BEEN_BLOCK_BY_ADMIN);
			return HOTEL_HAS_BEEN_BLOCK_BY_ADMIN;
		} else if (Boolean.TRUE.equals(userDeletePojo.getIsDeleted())) {
			hotel.getUser().setIsDeleted(Boolean.TRUE);
			log.info(HOTEL_DELETED_SUCCESSFULLY);
			return HOTEL_DELETED_SUCCESSFULLY;
		}
		log.info(INVALID_VALUE);
		return INVALID_VALUE;
	}// End of The Delete By ID

	/**
	 * This Method Is Implemented to Update The Hotel Details
	 */
	@Override
	public HotelPojo updateHotelDetails(HotelPojo hotelPojo) {
		Hotel hotel = hotelRepository.findById(hotelPojo.getHotelId())
				.orElseThrow(() -> new HotelDetailsNotFoundException(HOTEL_DETAILS_NOT_FOUND));

		UserDetails userDetails = hotel.getUser();
		userDetails.setStatus(hotelPojo.getStatus());
		userDetails.setAdminNote(hotelPojo.getAdminNote());
		userDetails.setEmail(hotelPojo.getEmail());
		try {
			BeanUtils.copyProperties(hotelPojo, hotel);
			hotel.setUser(userDetails);
			hotelRepository.save(hotel);
		} catch (Exception exception) {
			log.info(exception.getMessage());
			log.error(LayoverConstant.EMAIL_ALREADY_REGISTER);
			throw new EmailInterruptionException(LayoverConstant.EMAIL_ALREADY_REGISTER);
		}

		return hotelPojo;

	}// End Of The Update Hotel Details

	/**
	 * This Method Is Implemented To Update the Hotel Images and logo
	 */
	@Transactional
	@Override
	public HotelPojo updateHotelImages(List<MultipartFile> images, MultipartFile logo, Integer id) {
		Hotel hotel = hotelRepository.findById(id)
				.orElseThrow(() -> new HotelDetailsNotFoundException(HOTEL_DETAILS_NOT_FOUND));
		List<Image> imageList = imageRepository.findByHotel(hotel);
		if (!imageList.isEmpty() && !images.isEmpty()) {
			for (Image image : imageList) {
				sssUploadFile.deleteS3Folder(image.getImagePath());
				imageRepository.delete(image);
			}
		}
		List<Image> newImageList = new ArrayList<>();
		if (!images.isEmpty()) {
			for (MultipartFile multipartFile : images) {
				Image image = new Image();
				String uploadFile = sssUploadFile.uploadFile(multipartFile);
				image.setHotel(hotel);
				image.setImageName(multipartFile.getOriginalFilename());
				image.setImagePath(uploadFile);
				newImageList.add(image);

			}

		}
		if (logo != null) {

			String uploadFile = sssUploadFile.uploadFile(logo);
			if (hotel.getLogoPath() != null) {
				sssUploadFile.deleteS3Folder(hotel.getLogoPath());
			}
			hotel.setLogoPath(uploadFile);

		}
		List<Image> saveAll = imageRepository.saveAll(newImageList);
		HotelPojo hotelPojo = new HotelPojo();
		BeanUtils.copyProperties(hotel, hotelPojo);
		hotelPojo.setStatus(hotel.getUser().getStatus());
		hotelPojo.setAdminNote(hotel.getUser().getAdminNote());
		hotelPojo.setEmail(hotel.getUser().getEmail());
		hotelPojo.setIsDeleted(hotel.getUser().getIsDeleted());
		hotelPojo.setUserType(hotel.getUser().getUserType());
		hotelPojo.setIsBlocked(hotel.getUser().getIsBlocked());
		hotelPojo.setImageList(saveAll);
		return hotelPojo;
	}// End Of The Update Hotel Images

	@Transactional
	public @Override HotelDashbordCountResponse hotelCount(Integer hotelId) {

		List<LayoverRequest> layoverRequests = layoverRequestRepository.findByHotelDetailsHotelHotelId(hotelId);

		Long totalLayoverCount = layoverRequests.stream().count();

		List<LayoverResponse> findByLayoverRequestHotelDetailsHotelHotelId = layoverResponseRepository
				.findByLayoverRequestHotelDetailsHotelHotelId(hotelId);

		Long totalLayoverAccepted = findByLayoverRequestHotelDetailsHotelHotelId.stream()
				.filter(layoverResponse -> layoverResponse.getStatus().equalsIgnoreCase(ACCEPTED)).count();

		Long totalLayoverDeclined = findByLayoverRequestHotelDetailsHotelHotelId.stream()
				.filter(layoverResponse -> layoverResponse.getStatus().equalsIgnoreCase(DENIED)).count();

		List<Payment> findByLayoverResponseLayoverRequestHotelDetailsHotelHotelId = paymentRepository
				.findByLayoverResponseLayoverRequestHotelDetailsHotelHotelId(hotelId);

		BigDecimal openPayments = BigDecimal.valueOf(findByLayoverResponseLayoverRequestHotelDetailsHotelHotelId
				.stream().filter(payment -> payment.getHotelStatus().equalsIgnoreCase(PENDING))
				.collect(Collectors.summingDouble(
						layoverResponse -> layoverResponse.getLayoverResponse().getTotalAmount().doubleValue())));

		BigDecimal closedPayments = BigDecimal.valueOf(findByLayoverResponseLayoverRequestHotelDetailsHotelHotelId
				.stream().filter(payment -> payment.getHotelStatus().equalsIgnoreCase(RECIEVED))
				.collect(Collectors.summingDouble(
						layoverResponse -> layoverResponse.getLayoverResponse().getTotalAmount().doubleValue())));

		BigDecimal amountReceivedByHotel = BigDecimal.valueOf(
				findByLayoverResponseLayoverRequestHotelDetailsHotelHotelId.stream().collect(Collectors.summingDouble(
						layoverResponse -> layoverResponse.getLayoverResponse().getTotalAmount().doubleValue())));

		return HotelDashbordCountResponse.builder().totalLayover(totalLayoverCount)
				.totalLayoverAccepted(totalLayoverAccepted).totalLayoverDeclined(totalLayoverDeclined)
				.openPayments(openPayments).amountReceivedByHotel(amountReceivedByHotel).closedPayments(closedPayments)
				.build();
	}

	@Transactional
	public @Override SortedMap<String, Long> totalLayoverCountBasedOnMonth(
			HotelMonthwiseFilterRequest hotelMonthwiseFilterRequest) {
		if (hotelMonthwiseFilterRequest.getStatus().equalsIgnoreCase(ACCEPTED)) {
			return layoverResponseRepository
					.findByLayoverRequestHotelDetailsHotelHotelId(hotelMonthwiseFilterRequest.getHotelId()).stream()
					.filter(layoverResponse -> hotelMonthwiseFilterRequest.getStatus()
							.equalsIgnoreCase(layoverResponse.getStatus()))
					.filter(layoverResponse -> layoverResponse.getLayoverRequest().getTimestamp()
							.getYear() == hotelMonthwiseFilterRequest.getYear())
					.collect(
							Collectors.groupingBy(
									layoverResponse -> layoverResponse.getLayoverRequest().getTimestamp()
											.getMonthValue() + "-" + hotelMonthwiseFilterRequest.getYear(),
									TreeMap::new, Collectors.counting()));
		} else if (hotelMonthwiseFilterRequest.getStatus().equalsIgnoreCase(DENIED)) {
			return layoverResponseRepository
					.findByLayoverRequestHotelDetailsHotelHotelId(hotelMonthwiseFilterRequest.getHotelId()).stream()
					.filter(layoverResponse -> hotelMonthwiseFilterRequest.getStatus()
							.equalsIgnoreCase(layoverResponse.getStatus()))
					.filter(layoverResponse -> layoverResponse.getLayoverRequest().getTimestamp()
							.getYear() == hotelMonthwiseFilterRequest.getYear())
					.collect(
							Collectors.groupingBy(
									layoverResponse -> layoverResponse.getLayoverRequest().getTimestamp()
											.getMonthValue() + "-" + hotelMonthwiseFilterRequest.getYear(),
									TreeMap::new, Collectors.counting()));
		} else {
			return layoverRequestRepository.findByHotelDetailsHotelHotelId(hotelMonthwiseFilterRequest.getHotelId())
					.stream()
					.filter(layoverRequest -> layoverRequest.getTimestamp().getYear() == hotelMonthwiseFilterRequest
							.getYear())
					.collect(
							Collectors.groupingBy(
									layoverRequest -> layoverRequest.getTimestamp().getMonthValue() + "-"
											+ hotelMonthwiseFilterRequest.getYear(),
									TreeMap::new, Collectors.counting()));
		}
	}

	@Transactional
	public @Override SortedMap<String, Double> totalLayoverPaymentGeneratedBasedOnMonth(
			HotelMonthwiseFilterRequest hotelMonthwiseFilterRequest) {
		if (hotelMonthwiseFilterRequest.getStatus().equalsIgnoreCase(RECIVIED)) {
			return paymentRepository
					.findByLayoverResponseLayoverRequestHotelDetailsHotelHotelId(
							hotelMonthwiseFilterRequest.getHotelId())
					.stream().filter(payment -> payment.getHotelStatus().equalsIgnoreCase(RECIVIED))
					.filter(payment -> payment.getTimestamp().getYear() == hotelMonthwiseFilterRequest.getYear())
					.collect(Collectors.groupingBy(
							payment -> payment.getTimestamp().getMonthValue() + "-"
									+ hotelMonthwiseFilterRequest.getYear(),
							TreeMap::new, Collectors.summingDouble(
									payment -> payment.getLayoverResponse().getTotalAmount().doubleValue())));
		} else if (hotelMonthwiseFilterRequest.getStatus().equalsIgnoreCase(PENDING)) {
			return paymentRepository
					.findByLayoverResponseLayoverRequestHotelDetailsHotelHotelId(
							hotelMonthwiseFilterRequest.getHotelId())
					.stream().filter(payment -> payment.getHotelStatus().equalsIgnoreCase(PENDING))
					.filter(payment -> payment.getTimestamp().getYear() == hotelMonthwiseFilterRequest.getYear())
					.collect(Collectors.groupingBy(
							payment -> payment.getTimestamp().getMonthValue() + "-"
									+ hotelMonthwiseFilterRequest.getYear(),
							TreeMap::new, Collectors.summingDouble(
									payment -> payment.getLayoverResponse().getTotalAmount().doubleValue())));
		} else {
			return paymentRepository
					.findByLayoverResponseLayoverRequestHotelDetailsHotelHotelId(
							hotelMonthwiseFilterRequest.getHotelId())
					.stream()
					.filter(payment -> payment.getTimestamp().getYear() == hotelMonthwiseFilterRequest.getYear())
					.collect(Collectors.groupingBy(
							payment -> payment.getTimestamp().getMonthValue() + "-"
									+ hotelMonthwiseFilterRequest.getYear(),
							TreeMap::new, Collectors.summingDouble(
									payment -> payment.getLayoverResponse().getTotalAmount().doubleValue())));
		}
	}
}
