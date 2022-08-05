package com.tyss.layover.service;

import static com.tyss.layover.constant.CareerConstants.SERVER_IS_BUSY_TRY_AGAIN;
import static com.tyss.layover.constant.ContactUsConstants.THANK_YOU_FOR_CONTACTING_US;

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

import com.tyss.layover.exception.EmailInterruptionException;
import com.tyss.layover.pojo.ContactUsRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@Scope("singleton")
@RequiredArgsConstructor
public class ContactUsServiceImpl implements ContactUsService {

	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String mailToContactUsDepartment;

	@Override
	public String contactUsRequest(ContactUsRequest contactUsRequest) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setTo(mailToContactUsDepartment);
			messageHelper.setSubject("Contact us Information");
			messageHelper.setText("Name : " + contactUsRequest.getName().substring(0, 1).toUpperCase()
					+ contactUsRequest.getName().substring(1) + "\n\nEmail Address : " + contactUsRequest.getEmail()
					+ "\nPhone Number : " + contactUsRequest.getPhone() + "\nProject Descrption : "
					+ contactUsRequest.getProjectDescription() + "Thanks and Regards\n" + contactUsRequest.getName()
					+ "\n" + "Date : "
					+ LocalDate.now().format((DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))));
			contactUsRequestAcknowledementToUser(contactUsRequest);
			javaMailSender.send(mimeMessage);
		} catch (Exception exception) {
			throw new EmailInterruptionException(SERVER_IS_BUSY_TRY_AGAIN);
		}
		return THANK_YOU_FOR_CONTACTING_US;
	}

	private void contactUsRequestAcknowledementToUser(ContactUsRequest contactUsRequest) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setTo(contactUsRequest.getEmail());
			messageHelper.setSubject("Layover Connect");
			messageHelper.setText("Dear " + contactUsRequest.getName().substring(0, 1).toUpperCase()
					+ contactUsRequest.getName().substring(1) + ",\n\nThank you for contacting layover connect"
					+ "\n\nThank you" + "\n\nBest regards" + "\n" + "\nThanks and Regards\n" + "Layover Connect" + "\n"
					+ "Date : " + LocalDate.now().format((DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))));
			javaMailSender.send(mimeMessage);
		} catch (Exception exception) {
			throw new EmailInterruptionException(SERVER_IS_BUSY_TRY_AGAIN);
		}
	}
}
