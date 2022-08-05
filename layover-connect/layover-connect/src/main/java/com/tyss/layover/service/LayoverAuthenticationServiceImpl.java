package com.tyss.layover.service;

import static com.tyss.layover.constant.LayoverConstant.*;
import static com.tyss.layover.constant.LayoverConstant.ACCOUNT_HAS_BEEN_NOT_APPROVED_BY_ADMIN;
import static com.tyss.layover.constant.LayoverConstant.GENERATED_OTP;
import static com.tyss.layover.constant.LayoverConstant.INVALID_EMAIL_ADDRESS;
import static com.tyss.layover.constant.LayoverConstant.OTP_HAS_BEEN_SENT_TO_THE_REGISTERED_EMAIL;
import static com.tyss.layover.constant.LayoverConstant.ONE_TIME_PASSWORD_IS;
import static com.tyss.layover.constant.LayoverConstant.OTP_EXPIRED;
import static com.tyss.layover.constant.LayoverConstant.OTP_FOR_AUTHENTICATION;
import static com.tyss.layover.constant.LayoverConstant.OTP_IS_NOT_VALID;
import static com.tyss.layover.constant.LayoverConstant.USER_HAS_BEEN_DELETED;
import static com.tyss.layover.constant.LayoverConstant.USER_NOT_FOUND;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.tyss.layover.entity.UserDetails;
import com.tyss.layover.exception.AccountNotApprovedException;
import com.tyss.layover.exception.EmailInterruptionException;
import com.tyss.layover.exception.IncorrectOtpException;
import com.tyss.layover.exception.UserNotFoundException;
import com.tyss.layover.pojo.OtpValidationPojo;
import com.tyss.layover.repository.UserRepository;
import com.tyss.layover.util.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This is the service implementation class for LayoverAuthenticationService interface. Here you
 * find implementation for  fetching and Validating the Userdetails
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
public class LayoverAuthenticationServiceImpl implements LayoverAuthenticationService {

	/**
	 * This Field Is Invoke The Method Which Is Used To Send Otp To The UserDetails Email Address
	 */
	private final JavaMailSender javaMailSender;

	/**
	 * This Field Is Invoke The Method To Fetch The UserDetails
	 */
	private final UserRepository userRepository;

	/**
	 * This Field Is Invoke The Method To Generate Access Token
	 */
	private final JwtUtils jwtUtils;

	/**
	 *This Method Use To Fetch The UserDetails And Validate It. 
	 */
	@Override
	public String authenticate(OtpValidationPojo otpValidationPojo) {
		UserDetails user = userRepository.findByEmail(otpValidationPojo.getEmail()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
		if (!user.getStatus().equalsIgnoreCase(ACCEPTED)) {
			log.info(ACCOUNT_HAS_BEEN_NOT_APPROVED_BY_ADMIN);
			throw new AccountNotApprovedException(ACCOUNT_HAS_BEEN_NOT_APPROVED_BY_ADMIN);
		}
		if (Boolean.TRUE.equals(user.getIsDeleted())) {
			log.info(USER_HAS_BEEN_DELETED);
			throw new UserNotFoundException(USER_HAS_BEEN_DELETED);
		}
		if (Boolean.TRUE.equals(user.getIsBlocked())) {
			log.info(USER_HAS_BEEN_BLOCKED_BY_ADMIN);
			throw new UserNotFoundException(USER_HAS_BEEN_BLOCKED_BY_ADMIN);
		}

		return sendEmail(user);
	}//End Of authenticate

	/**
	 * @param user
	 * @return String
	 * This Is a Private Method Used To Send Otp To The User Email Address.
	 */
	@Async
	private String sendEmail(UserDetails user) {

		SecureRandom random = new SecureRandom();
		try {
			int otp = random.nextInt(900000) + 100000;
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setTo(user.getEmail());
			messageHelper.setSubject(OTP_FOR_AUTHENTICATION);
			messageHelper.setText("Otp Going To Expires In Next 10 Minutes");
			messageHelper.setText(ONE_TIME_PASSWORD_IS + otp);
			log.info(GENERATED_OTP + otp);
			javaMailSender.send(mimeMessage);
			user.setTimestamp(LocalDateTime.now());
			user.setOtp(otp);

			userRepository.save(user);
			log.info(OTP_HAS_BEEN_SENT_TO_THE_REGISTERED_EMAIL + user.getEmail());
			return OTP_HAS_BEEN_SENT_TO_THE_REGISTERED_EMAIL;
		} catch (Exception exception) {
			log.info(INVALID_EMAIL_ADDRESS);
			throw new EmailInterruptionException("Server Is Busy Unable To Generate OTP");
		}
	}//End Of SendEmail

	/**
	 *This Method Used To Validate The otp.
	 */
	@Override
	public Map<String, Object> validateOtp(OtpValidationPojo otpValidation) {
		 UserDetails userDetails = userRepository.findByEmail(otpValidation.getEmail())
				.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
		LocalDateTime localDateTime = userDetails.getTimestamp().plusMinutes(10);
		LocalDateTime now = LocalDateTime.now();
		if (!userDetails.getOtp().equals(otpValidation.getOtp())) {
			throw new IncorrectOtpException(OTP_IS_NOT_VALID);
		}
		if (now.isAfter(localDateTime)) {
			throw new IncorrectOtpException(OTP_EXPIRED);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + userDetails.getUserType()));
		return jwtUtils.generateToken(userDetails, authorities);
	}//End Of validate

}
