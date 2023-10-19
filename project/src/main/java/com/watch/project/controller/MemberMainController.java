package com.watch.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.watch.project.service.GoogleMemberService;
import com.watch.project.service.NaverMemberService;

@Controller
public class MemberMainController {
	@Autowired 
	private NaverMemberService naverService;
	@Autowired
	private GoogleMemberService googleService;
	
	@GetMapping("/selectSignUpType")
	public String selectSignUpType(Model model, HttpSession session) {
        String naverUrl = naverService.getAuthorizationUrl(session);
        String googleUrl = googleService.getAuthorizationUrl(session);
        model.addAttribute("naverLoginUrl", naverUrl);
        model.addAttribute("googleLoginUrl", googleUrl);
		return "member/select_sign_up_type";
	}
	
	@GetMapping("/signIn")
	public String SignIn(Model model, HttpSession session) {
        String naverUrl = naverService.getAuthorizationUrl(session);
        String googleUrl = googleService.getAuthorizationUrl(session);
        model.addAttribute("naverLoginUrl", naverUrl);
        model.addAttribute("googleLoginUrl", googleUrl);
		return "member/sign_in";
	}
}
