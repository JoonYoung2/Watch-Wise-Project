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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.MemberDTO;
import com.watch.project.service.MailService;
import com.watch.project.service.SearchService;
import com.watch.project.service.member.KakaoMemberService;

@Controller
public class MailController {
	@Autowired 
	private MailService mailservice;
	@Autowired 
	private KakaoMemberService kakaoMemberService;
	@Autowired 
	private SearchService searchService;
	
	@PostMapping("/sendEmailforAuth")
	public String authCheck(@RequestParam("userEmail") String email,  HttpServletResponse res, Model model) throws IOException {
		String msg = mailservice.issueCh(email);
		if(msg!=null) {
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.print(msg);
		}else {
			mailservice.send3(email);//session이 꼭 있어야 하나..?
			searchService.searchModel(model);
			return "member/local_member/email_auth";
		}
		return null;
	}
	
	@PostMapping("/emailVerification") //email인증 먼저 하고 다른 정보 받는 것.
	public String check(@RequestParam String authKey, HttpSession session, Model model) {
		//setAttribute("authKey", authKey)
		String sessionKey = (String)session.getAttribute("authKey");
		if(sessionKey.equals(authKey)) {
			model.addAttribute("userEmail", session.getAttribute("userEmail"));
			searchService.searchModel(model);
			return "member/local_member/sign_up";
		}else {
			model.addAttribute("msg", "인증코드가 불일치합니다. 다시시도해주세요.");
			searchService.searchModel(model);
			return "member/local_member/email_auth";			
		}
	}
	
	@PostMapping("/kakaoEmailVerification") //다른 정보를 먼저 소셜로 받고, email 인증하는 것.
	public String Kakaocheck(@RequestParam String authKey,MemberDTO dto, HttpSession session, Model model, RedirectAttributes redirectAttr) {
		String sessionKey = (String)session.getAttribute("authKey");
		if(sessionKey.equals(authKey)) {
			dto.setUserEmail((String)session.getAttribute("userEmail"));
			kakaoMemberService.register(dto);
			redirectAttr.addFlashAttribute("msg", "인증과 회원가입이 완료되었습니다.");
			return "redirect:/";
		}else {
			model.addAttribute("msg", "인증코드가 불일치합니다. 다시시도해주세요.");
			searchService.searchModel(model);
			return "member/social_member/email_auth";			
		}
	}
}