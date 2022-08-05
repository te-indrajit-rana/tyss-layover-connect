package com.tyss.layover.service;

import static com.tyss.layover.constant.CareerConstants.SERVER_IS_BUSY_TRY_AGAIN;
import static com.tyss.layover.constant.CareerConstants.SUCCESSFULLY_APPLIED_TO_CAREER_OPPORTUNITY;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.layover.exception.EmailInterruptionException;
import com.tyss.layover.pojo.CareersRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@Scope("singleton")
@RequiredArgsConstructor
public class CareerServiceImpl implements CareerService {

	

	private final ObjectMapper objectMapper;

	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String mailToCareerDepartment;

	public @Override String careerOppotunitiesRequest(String data, MultipartFile multipartFile) {
		try {
			CareersRequest careersRequest = objectMapper.readValue(data, CareersRequest.class);
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setTo(mailToCareerDepartment);
			messageHelper.setSubject("Applying for Career Opportunity");
			messageHelper.setText("Applicant Name : " + careersRequest.getFullName().substring(0, 1).toUpperCase()
					+ careersRequest.getFullName().substring(1) + "\n" + "Applicant Email : "
					+ careersRequest.getEmailId() + "\n" + "Phone Number : " + careersRequest.getPhone() + "\n"
					+ "Current Company : " + careersRequest.getCurrentCompany() + "\n" + "Additional Information : "
					+ careersRequest.getAdditionalInformation() + "\n\n\n\n" + "Thanks and Regards\n"
					+ careersRequest.getFullName() + "\n" + "Date : "
					+ LocalDate.now().format((DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))));
			messageHelper.addAttachment("" + multipartFile.getOriginalFilename(), multipartFile);
			careerAcknowledgementMessageToUser(careersRequest);
			javaMailSender.send(mimeMessage);
		} catch (Exception exception) {
			throw new EmailInterruptionException(SERVER_IS_BUSY_TRY_AGAIN);
		}
		return SUCCESSFULLY_APPLIED_TO_CAREER_OPPORTUNITY;
	}

	private void careerAcknowledgementMessageToUser(CareersRequest careersRequest) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setTo(careersRequest.getEmailId());
			messageHelper.setSubject("Thank you for applying career opportuninty at Layover Connect");
			messageHelper.setText("Dear  " + careersRequest.getFullName().substring(0, 1).toUpperCase()
					+ careersRequest.getFullName().substring(1) + "," + "\n\n"
					+ "Your application is registered for career opportunity" + "\n\n"
					+ "We will shortly get in touch with you next step if requirement matches your profile " + "\n\n\n"
					+ "Thank you\n\n" + "Best regards" + "\n\n" + "Thanks and Regards\n" + "Layover Connect" + "\n"
					+ "Date : " + LocalDate.now().format((DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))));
			javaMailSender.send(mimeMessage);
		} catch (Exception exception) {
			throw new EmailInterruptionException(SERVER_IS_BUSY_TRY_AGAIN);
		}
	}
}
