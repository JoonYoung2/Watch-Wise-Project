package com.watch.project.service;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.watch.project.repository.MemberRepository;

import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
public class MailService {
	@Autowired JavaMailSender mailSender;
	@Autowired LocalMemberService localService;
	@Autowired HttpSession session;
	public void send3(String email) {
		String authKey = rand();
		session.setAttribute("authKey", authKey);
		session.setAttribute("userEmail", email);

		String body = "<h3>WatchWise 이메일 인증 코드입니다.</h3>인증란에 아래의 코드를 입력해주세요.<br><b>" + authKey + "</b>";

		MimeMessage msg = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
			helper.setTo(email);
			helper.setSubject("WatchWise 이메일 인증 코드");
			helper.setText(body, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		mailSender.send(msg);

	}

	private String rand() {
		Random ran = new Random();
		String str = "";
		while (str.length() <= 8) {
			int num = ran.nextInt(75) + 48;
			if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
				str += (char) num;
			}
		}
		return str;//asdf1o334
	}
	

	
	public String issueCh(String email) {
		String msg = null;
		if(email==null || email.equals("")) {
			msg="<script>alert('이메일을 입력해주세요.');location.href='/checkEmail';</script>";
		}
		int existence = localService.existingEmailCh(email);
		if(existence==1) {//이미 존재하는 이메일일 경우
			msg = "<script>alert('이미 존재하는 회원의 이메일 주소입니다. 로그인을 진행해주세요.');location.href='/signIn';</script>";
		}
		return msg;
	}

//	public static String createNumber() {// Math.random 라이브러리를 이용하여 랜덤으로 난수를 생성하게 해준다.
//		String number =  (Math.random() * (90000)) + 100000+"";// (int) Math.random() * (최댓값-최소값+1) + 최소값
//		return number;
//	}
}
