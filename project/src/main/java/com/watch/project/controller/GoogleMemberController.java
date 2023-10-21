package com.watch.project.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.MemberDTO;
import com.watch.project.service.CommonMethods;
import com.watch.project.service.GoogleMemberService;

@Controller
public class GoogleMemberController {

	@Autowired 
	private GoogleMemberService service;
	@Autowired
	private CommonMethods common;
	
	@GetMapping("/google/login")
	public String redirected(@RequestParam("code") String authCode, HttpServletResponse res, HttpSession session) throws IOException {
		MemberDTO userInfo = service.loginWithGoogle(authCode);
		String msg = service.storageIfNewOne(userInfo);//기존 회원인지, 새로운 유저면 회원 정보 저장
		
		if(msg != "0") {//정상적으로 저장되지 않았을 경우
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.print(msg);
			return "redirect:/selectSignUpType";
		}//정상적 처리일 경우
		session.setAttribute("userEmail", userInfo.getUserEmail());
		session.setAttribute("userLoginType", 4);
		session.setAttribute("accessToken", userInfo.getAccessToken());//id_token
		return "redirect:/";
	}
	
	@GetMapping("/googleSignOut")
	public String signout(HttpSession session, Model model) throws IOException {
		session.invalidate();
		model.addAttribute("signOutAlert", true);
		return "home";//redirect 하면 알림 안뜸.
	}
	
	@GetMapping("/googleUnregister")
	public String unregister(HttpSession session, Model model) {
		String msg = common.unregister((String)session.getAttribute("userEmail"));
		if(msg=="회원탈퇴가 완료되었습니다.") {
		session.invalidate();
		}
		model.addAttribute("msg", msg);
		return "home";
	}
}
