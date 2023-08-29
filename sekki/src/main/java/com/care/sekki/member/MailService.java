package com.care.sekki.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	@Autowired	private JavaMailSender mailSender;

	public String sendAuthenticationNumEmail(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setSubject(subject); 
			messageHelper.setText(content);
			messageHelper.setTo(to);

			mailSender.send(message);
			
		} catch (MailSendException e) {
			e.printStackTrace();
			return "* 유효하지 않은 이메일주소 입니다.";
		} catch (Exception e) {
			e.printStackTrace();
			return "* 이메일 전송에 문제가 발생했습니다. 잠시 후에 다시 시도 하세요.";
		}
		return "* 입력하신 이메일 주소로 인증번호를 전송했습니다.";
	}

	public String sendTempPwEmail(String email, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setSubject(subject); 
			messageHelper.setText(content);
			messageHelper.setTo(email);

			mailSender.send(message);
			
		} catch (MailSendException e) {
			e.printStackTrace();
			return "* 유효하지 않은 이메일주소 입니다.";
		} catch (Exception e) {
			e.printStackTrace();
			return "* 이메일 전송에 문제가 발생했습니다. 잠시 후에 다시 시도 하세요.";
		}
		return "* 입력하신 이메일 주소로 임시비밀번호를 전송했습니다.";
	}
}