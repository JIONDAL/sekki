package com.care.sekki.member;

import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class SmsService {

	public String sendAuthenticationNumSms(String sendTo, String auth) {
		DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("api키", "api-secret키",
				"https://api.coolsms.co.kr");
		Message message = new Message();
		message.setFrom("01035056792");
		message.setTo(sendTo);
		message.setText("(자취세끼) 가입을 환영합니다. 인증번호 6자리 [" + auth + "] 를 입력해주세요.");

		try {
			messageService.send(message);
			return "* 입력하신 전화번호로 인증번호를 전송했습니다.";
		} catch (NurigoMessageNotReceivedException exception) {
			System.out.println(exception.getFailedMessageList());
			System.out.println(exception.getMessage());
			return "* 인증번호 전송을 실패했습니다.";
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			return "* 인증번호 전송을 실패했습니다.";
		}

	}

	public String sendTempPwSms(String phoneNumber, String tempPw) {
		DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("api키", "api-secret키",
				"https://api.coolsms.co.kr");
		Message message = new Message();
		message.setFrom("01035056792");
		message.setTo(phoneNumber);
		message.setText("(자취세끼) 임시비밀번호 입니다. 로그인 후 반드시 비밀번호를 변경하세요."
				+ "임시비밀번호 [" + tempPw + "]");

		try {
			messageService.send(message);
			return "* 입력하신 전화번호로 임시비밀번호를 전송했습니다.";
		} catch (NurigoMessageNotReceivedException exception) {
			System.out.println(exception.getFailedMessageList());
			System.out.println(exception.getMessage());
			return "* 인증번호 전송을 실패했습니다.";
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			return "* 인증번호 전송을 실패했습니다.";
		}
	}

	public void sendAdminReply(String phoneNumber) {
		DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("api키", "secret-api키",
				"https://api.coolsms.co.kr");
		Message message = new Message();
		message.setFrom("01035056792");
		message.setTo(phoneNumber);
		message.setText("(자취세끼) 문의글에 답변이 등록되었습니다. 확인바랍니다.");

		try {
			messageService.send(message);
		} catch (NurigoMessageNotReceivedException exception) {
			System.out.println(exception.getFailedMessageList());
			System.out.println(exception.getMessage());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

}
