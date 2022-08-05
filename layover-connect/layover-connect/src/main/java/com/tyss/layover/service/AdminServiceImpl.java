package com.tyss.layover.service;

import static com.tyss.layover.constant.AdminConstants.ACCEPTED;
import static com.tyss.layover.constant.AdminConstants.AIRLINE;
import static com.tyss.layover.constant.AdminConstants.AIRPORT;
import static com.tyss.layover.constant.AdminConstants.DECLINED;
import static com.tyss.layover.constant.AdminConstants.DENIED;
import static com.tyss.layover.constant.AdminConstants.EXTERN_HANDLING_COMPANY;
import static com.tyss.layover.constant.AdminConstants.HOTEL;
import static com.tyss.layover.constant.AdminConstants.PENDING;
import static com.tyss.layover.constant.AdminConstants.RECIVIED;
import static com.tyss.layover.constant.AdminConstants.TRANSPORTATION;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyss.layover.entity.Airline;
import com.tyss.layover.entity.Airport;
import com.tyss.layover.entity.Commission;
import com.tyss.layover.entity.ExternalHandlingCompany;
import com.tyss.layover.entity.Hotel;
import com.tyss.layover.entity.LayoverRequest;
import com.tyss.layover.entity.LayoverResponse;
import com.tyss.layover.entity.Transportation;
import com.tyss.layover.entity.UserDetails;
import com.tyss.layover.repository.AirlineRepository;
import com.tyss.layover.repository.AirportRepository;
import com.tyss.layover.repository.CommissionRepository;
import com.tyss.layover.repository.ExternCompanyRepository;
import com.tyss.layover.repository.HotelRepository;
import com.tyss.layover.repository.LayoverRequestRepository;
import com.tyss.layover.repository.LayoverResponseRepository;
import com.tyss.layover.repository.PaymentRepository;
import com.tyss.layover.repository.TransportationRepository;
import com.tyss.layover.response.AdminActionResponse;
import com.tyss.layover.response.AdminCountResponse;
import com.tyss.layover.response.AdminLatestLayoversResponse;
import com.tyss.layover.response.AdminLatestRegistrationResponse;
import com.tyss.layover.response.AirlineAdminResponse;
import com.tyss.layover.response.AirportAdminResponse;
import com.tyss.layover.response.ExternalHandlingCompanyAdminResponse;
import com.tyss.layover.response.HotelAdminResponse;
import com.tyss.layover.response.TransportationAdminResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@Scope("singleton")
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AirportRepository airportRepository;

	private final TransportationRepository transportationRepository;

	private final ExternCompanyRepository externCompanyRepository;

	private final AirlineRepository airlineRepository;

	private final HotelRepository hotelRepository;

	private final LayoverRequestRepository layoverRequestRepository;

	private final LayoverResponseRepository layoverResponseRepository;

	private final CommissionRepository commissionRepository;

	private final PaymentRepository paymentRepository;

	public @Override AdminCountResponse adminCountResponse() {
		Long airportCount = airportRepository.count();
		Long transportationCount = transportationRepository.count();
		Long externCompanyCount = externCompanyRepository.count();
		Long airlineCount = airlineRepository.count();
		Long hotelCount = hotelRepository.count();
		Long layovercCount = layoverRequestRepository.count();
		Long acceptedCount = layoverResponseRepository.countByStatus(ACCEPTED);
		Long declinedCount = layoverResponseRepository.countByStatus(DECLINED);

		BigDecimal toatlAmountReceivedByHotel = BigDecimal
				.valueOf(layoverResponseRepository.findAllByTotalAmountNotNull().stream()
						.map(LayoverResponse::getTotalAmount).mapToDouble(BigDecimal::doubleValue).sum());

		BigDecimal totalCommision = BigDecimal.valueOf(commissionRepository.findAllByCommissionAmountNotNull().stream()
				.map(Commission::getCommissionAmount).mapToDouble(BigDecimal::doubleValue).sum());

		return AdminCountResponse.builder().totalAirportRegister(airportCount)
				.totalTransportationRegister(transportationCount).totalExternHandlingCompanyRegister(externCompanyCount)
				.totalAirlineRegister(airlineCount).totalHotelRegister(hotelCount).totalLayover(layovercCount)
				.amountReceivedByHotel(toatlAmountReceivedByHotel).totalLayoverAccepted(acceptedCount)
				.totalLayoverDeclined(declinedCount).totalCommission(totalCommision).build();
	}

	public @Override List<AdminLatestRegistrationResponse> adminLatestRegistrationResponses(String userType) {
		if (Boolean.TRUE.equals(userType.equalsIgnoreCase(AIRLINE))) {
			List<Airline> airlines = airlineRepository.findAll().stream()
					.sorted(Comparator.comparing(Airline::getAirlineId).reversed()).collect(Collectors.toList());
			return airlines.stream().map(airline -> {
				UserDetails userDetails = airline.getUser();
				return AdminLatestRegistrationResponse.builder().registrationTime(airline.getRegistrationDate())
						.status(userDetails.getStatus()).userType(userType.toUpperCase()).build();
			}).limit(10).collect(Collectors.toList());
		} else if (Boolean.TRUE.equals(userType.equalsIgnoreCase(AIRPORT))) {
			List<Airport> airports = airportRepository.findAll().stream()
					.sorted(Comparator.comparing(Airport::getAirportId).reversed()).collect(Collectors.toList());
			return airports.stream().map(airport -> {
				UserDetails userDetails = airport.getUser();
				return AdminLatestRegistrationResponse.builder().registrationTime(airport.getRegistrationDate())
						.status(userDetails.getStatus()).userType(userType.toUpperCase()).build();
			}).limit(10).collect(Collectors.toList());
		} else if (Boolean.TRUE.equals(userType.equalsIgnoreCase(TRANSPORTATION))) {
			List<Transportation> transportations = transportationRepository.findAll().stream()
					.sorted(Comparator.comparing(Transportation::getTransportationId).reversed())
					.collect(Collectors.toList());
			return transportations.stream().map(transportation -> {
				UserDetails userDetails = transportation.getUser();
				return AdminLatestRegistrationResponse.builder().registrationTime(transportation.getRegistrationDate())
						.status(userDetails.getStatus()).userType(userType.toUpperCase()).build();
			}).limit(10).collect(Collectors.toList());
		} else if (Boolean.TRUE.equals(userType.equalsIgnoreCase(EXTERN_HANDLING_COMPANY))) {
			List<ExternalHandlingCompany> externalHandlingCompanies = externCompanyRepository.findAll().stream()
					.sorted(Comparator.comparing(ExternalHandlingCompany::getExternalHandlingCompanyId).reversed())
					.collect(Collectors.toList());
			return externalHandlingCompanies.stream().map(externalHandlingCompany -> {
				UserDetails userDetails = externalHandlingCompany.getUser();
				return AdminLatestRegistrationResponse.builder()
						.registrationTime(externalHandlingCompany.getRegistrationDate()).status(userDetails.getStatus())
						.userType(userType.toUpperCase()).build();
			}).limit(10).collect(Collectors.toList());
		} else if (Boolean.TRUE.equals(userType.equalsIgnoreCase(HOTEL))) {
			List<Hotel> hotels = hotelRepository.findAll().stream()
					.sorted(Comparator.comparing(Hotel::getHotelId).reversed()).collect(Collectors.toList());
			return hotels.stream().map(hotel -> {
				UserDetails userDetails = hotel.getUser();
				return AdminLatestRegistrationResponse.builder().registrationTime(hotel.getRegistrationDate())
						.status(userDetails.getStatus()).userType(userType.toUpperCase()).build();
			}).limit(10).collect(Collectors.toList());
		} else
			return Collections.emptyList();

	}

	public @Override List<AdminActionResponse> getAllTheRegisteredUserDetails(String userType) {
		AdminActionResponse adminActionResponse = AdminActionResponse.builder().build();
		if (Boolean.TRUE.equals(userType.equalsIgnoreCase(AIRLINE)))
			adminActionResponse.setGetAllTheAirlines(getAllTheAirlines());
		else if (Boolean.TRUE.equals(userType.equalsIgnoreCase(AIRPORT)))
			adminActionResponse.setGetAllTheAirports(getAllTheAirports());
		else if (Boolean.TRUE.equals(userType.equalsIgnoreCase(TRANSPORTATION)))
			adminActionResponse.setGetAllTheTransportation(getAllTheTransportation());
		else if (Boolean.TRUE.equals(userType.equalsIgnoreCase(EXTERN_HANDLING_COMPANY)))
			adminActionResponse.setGetAllTheExternHandlingCompany(getAllTheExternHandlingCompany());
		else if (Boolean.TRUE.equals(userType.equalsIgnoreCase(HOTEL)))
			adminActionResponse.setGetAllTheHotels(getAllTheHotels());
		return List.of(adminActionResponse);
	}

	private List<AirlineAdminResponse> getAllTheAirlines() {
		List<Airline> airlines = airlineRepository.findAll().stream()
				.sorted(Comparator.comparing(Airline::getAirlineId).reversed()).collect(Collectors.toList());
		return airlines.stream().map(airline -> {
			AirlineAdminResponse airlineAdminResponse = AirlineAdminResponse.builder().build();
			BeanUtils.copyProperties(airline, airlineAdminResponse);
			airlineAdminResponse.setStatus(airline.getUser().getStatus());
			airlineAdminResponse.setAdminNote(airline.getUser().getAdminNote());
			airlineAdminResponse.setIsDeleted(airline.getUser().getIsDeleted());
			airlineAdminResponse.setIsBlocked(airline.getUser().getIsBlocked());
			airlineAdminResponse.setEmail(airline.getUser().getEmail());
			return airlineAdminResponse;
		}).collect(Collectors.toList());
	}

	private List<AirportAdminResponse> getAllTheAirports() {
		List<Airport> airports = airportRepository.findAll().stream()
				.sorted(Comparator.comparing(Airport::getAirportId).reversed()).collect(Collectors.toList());
		return airports.stream().map(airport -> {
			AirportAdminResponse airportAdminResponse = AirportAdminResponse.builder().build();
			BeanUtils.copyProperties(airport, airportAdminResponse);
			airportAdminResponse.setStatus(airport.getUser().getStatus());
			airportAdminResponse.setAdminNote(airport.getUser().getAdminNote());
			airportAdminResponse.setIsDeleted(airport.getUser().getIsDeleted());
			airportAdminResponse.setIsBlocked(airport.getUser().getIsBlocked());
			airportAdminResponse.setEmail(airport.getUser().getEmail());
			return airportAdminResponse;
		}).collect(Collectors.toList());
	}

	private List<HotelAdminResponse> getAllTheHotels() {
		List<Hotel> hotels = hotelRepository.findAll().stream()
				.sorted(Comparator.comparing(Hotel::getHotelId).reversed()).collect(Collectors.toList());
		return hotels.stream().map(hotel -> {
			HotelAdminResponse hotelAdminResponse = HotelAdminResponse.builder().build();
			BeanUtils.copyProperties(hotel, hotelAdminResponse);
			hotelAdminResponse.setStatus(hotel.getUser().getStatus());
			hotelAdminResponse.setAdminNote(hotel.getUser().getAdminNote());
			hotelAdminResponse.setIsDeleted(hotel.getUser().getIsDeleted());
			hotelAdminResponse.setIsBlocked(hotel.getUser().getIsBlocked());
			hotelAdminResponse.setEmail(hotel.getUser().getEmail());
			return hotelAdminResponse;
		}).collect(Collectors.toList());
	}

	private List<ExternalHandlingCompanyAdminResponse> getAllTheExternHandlingCompany() {
		List<ExternalHandlingCompany> externalHandlingCompanies = externCompanyRepository.findAll().stream()
				.sorted(Comparator.comparing(ExternalHandlingCompany::getExternalHandlingCompanyId).reversed())
				.collect(Collectors.toList());
		return externalHandlingCompanies.stream().map(externalHandlingCompany -> {
			ExternalHandlingCompanyAdminResponse externalHandlingCompanyAdminResponse = ExternalHandlingCompanyAdminResponse
					.builder().build();
			BeanUtils.copyProperties(externalHandlingCompany, externalHandlingCompanyAdminResponse);
			externalHandlingCompanyAdminResponse.setStatus(externalHandlingCompany.getUser().getStatus());
			externalHandlingCompanyAdminResponse.setAdminNote(externalHandlingCompany.getUser().getAdminNote());
			externalHandlingCompanyAdminResponse.setIsDeleted(externalHandlingCompany.getUser().getIsDeleted());
			externalHandlingCompanyAdminResponse.setIsBlocked(externalHandlingCompany.getUser().getIsBlocked());
			externalHandlingCompanyAdminResponse.setEmail(externalHandlingCompany.getUser().getEmail());
			return externalHandlingCompanyAdminResponse;
		}).collect(Collectors.toList());
	}

	private List<TransportationAdminResponse> getAllTheTransportation() {
		List<Transportation> transportations = transportationRepository.findAll().stream()
				.sorted(Comparator.comparing(Transportation::getTransportationId).reversed())
				.collect(Collectors.toList());
		return transportations.stream().map(transportation -> {
			TransportationAdminResponse transportationAdminResponse = TransportationAdminResponse.builder().build();
			BeanUtils.copyProperties(transportation, transportationAdminResponse);
			transportationAdminResponse.setStatus(transportation.getUser().getStatus());
			transportationAdminResponse.setAdminNote(transportation.getUser().getAdminNote());
			transportationAdminResponse.setIsDeleted(transportation.getUser().getIsDeleted());
			transportationAdminResponse.setIsBlocked(transportation.getUser().getIsBlocked());
			transportationAdminResponse.setEmail(transportation.getUser().getEmail());
			return transportationAdminResponse;
		}).collect(Collectors.toList());
	}

	public @Override List<AdminLatestLayoversResponse> adminLatestLayoversResponses() {
		List<LayoverRequest> layoverRequests = layoverRequestRepository.findAll().stream()
				.sorted(Comparator.comparing(LayoverRequest::getTimestamp).reversed()).limit(10)
				.collect(Collectors.toList());
		return layoverRequests.stream().map(layoverRequest -> {
			AdminLatestLayoversResponse adminLatestLayoversResponse = AdminLatestLayoversResponse.builder().build();
			adminLatestLayoversResponse.setHotelName(layoverRequest.getHotelDetails().getHotel().getHotelName());
			adminLatestLayoversResponse.setAirlineName(layoverRequest.getAirline().getAirlineName());
			adminLatestLayoversResponse.setDateAndTime(layoverRequest.getTimestamp());
			adminLatestLayoversResponse.setStatus(layoverRequest.getAirline().getUser().getStatus());
			return adminLatestLayoversResponse;
		}).limit(10).collect(Collectors.toList());
	}

	public @Override SortedMap<String, Long> totalLayoverCountBasedOnMonth(String status, Integer year) {
		if (status.equalsIgnoreCase(ACCEPTED)) {
			return layoverResponseRepository.findAll().stream()
					.filter(layoverResponse -> status.equalsIgnoreCase(layoverResponse.getStatus()))
					.filter(layoverResponse -> layoverResponse.getLayoverRequest().getTimestamp().getYear() == year)
					.collect(
							Collectors
									.groupingBy(
											layoverResponse -> layoverResponse.getLayoverRequest().getTimestamp()
													.getMonthValue() + "-" + year,
											TreeMap::new, Collectors.counting()));
		} else if (status.equalsIgnoreCase(DENIED)) {
			return layoverResponseRepository.findAll().stream()
					.filter(layoverResponse -> status.equalsIgnoreCase(layoverResponse.getStatus()))
					.filter(layoverResponse -> layoverResponse.getLayoverRequest().getTimestamp().getYear() == year)
					.collect(
							Collectors
									.groupingBy(
											layoverResponse -> layoverResponse.getLayoverRequest().getTimestamp()
													.getMonthValue() + "-" + year,
											TreeMap::new, Collectors.counting()));
		} else {
			return layoverRequestRepository.findAll().stream()
					.filter(layoverRequest -> layoverRequest.getTimestamp().getYear() == year)
					.collect(Collectors.groupingBy(
							layoverRequest -> layoverRequest.getTimestamp().getMonthValue() + "-" + year, TreeMap::new,
							Collectors.counting()));
		}
	}

	public @Override SortedMap<String, Double> totalAmountGeneratedBasedOnMonth(String status, Integer year) {
		if (status.equalsIgnoreCase(RECIVIED)) {
			return paymentRepository.findAll().stream()
					.filter(payment -> payment.getHotelStatus().equalsIgnoreCase(RECIVIED))
					.filter(payment -> payment.getTimestamp().getYear() == year)
					.collect(Collectors.groupingBy(payment -> payment.getTimestamp().getMonthValue() + "-" + year,
							TreeMap::new, Collectors.summingDouble(
									payment -> payment.getLayoverResponse().getTotalAmount().doubleValue())));
		} else if (status.equalsIgnoreCase(PENDING)) {
			return paymentRepository.findAll().stream()
					.filter(payment -> payment.getHotelStatus().equalsIgnoreCase(PENDING))
					.filter(payment -> payment.getTimestamp().getYear() == year)
					.collect(Collectors.groupingBy(payment -> payment.getTimestamp().getMonthValue() + "-" + year,
							TreeMap::new, Collectors.summingDouble(
									payment -> payment.getLayoverResponse().getTotalAmount().doubleValue())));
		} else {
			return paymentRepository.findAll().stream().filter(payment -> payment.getTimestamp().getYear() == year)
					.collect(Collectors.groupingBy(payment -> payment.getTimestamp().getMonthValue() + "-" + year,
							TreeMap::new, Collectors.summingDouble(
									payment -> payment.getLayoverResponse().getTotalAmount().doubleValue())));
		}
	}
}
