package com.watch.project.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.MemberDTO;
import com.watch.project.service.KakaoMemberService;
import com.watch.project.service.MailService;

@Controller
public class MailController {
	@Autowired MailService mailservice;
	@Autowired KakaoMemberService kakaoMemberService;

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:auth";
	}
	
	@PostMapping("/sendEmailforAuth")
	public String authCheck(@RequestParam("userEmail") String email,  HttpServletResponse res) throws IOException {
		String msg = mailservice.issueCh(email);
		if(msg!=null) {
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.print(msg);
		}else {
//			String[] e = email.split("@");
//			System.out.println(e[0]);
//			System.out.println(e[1]);
			
			mailservice.send3(email);//session이 꼭 있어야 하나..?
			
			return "member/email_auth";
		}
		return null;
	}
	
	@PostMapping("/emailVerification")
	public String check(@RequestParam String authKey, HttpSession session, Model model) {
		//setAttribute("authKey", authKey)
		String sessionKey = (String)session.getAttribute("authKey");
		if(sessionKey.equals(authKey)) {
			System.out.println("일치하는 authkey임"+session.getAttribute("userEmail"));
			model.addAttribute("userEmail", session.getAttribute("userEmail"));
			return "member/sign_up";
		}else {
			model.addAttribute("msg", "인증코드가 불일치합니다. 다시시도해주세요.");
			System.out.println("일치하지 않는 authkey임");
			return "member/email_auth";			
		}
	}
	
	@PostMapping("/kakaoEmailVerification")
	public String Kakaocheck(@RequestParam String authKey,MemberDTO dto, HttpSession session, Model model) {
		//setAttribute("authKey", authKey)
		String sessionKey = (String)session.getAttribute("authKey");
		if(sessionKey.equals(authKey)) {
			System.out.println("일치하는 authkey임"+session.getAttribute("userEmail"));
			dto.setUserEmail((String)session.getAttribute("userEmail"));
			kakaoMemberService.register(dto);
			return "redirect:/";
		}else {
			model.addAttribute("msg", "인증코드가 불일치합니다. 다시시도해주세요.");
			System.out.println("일치하지 않는 authkey임");
			return "member/email_auth";			
		}
	}
}