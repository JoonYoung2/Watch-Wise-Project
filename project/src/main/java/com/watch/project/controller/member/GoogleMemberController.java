package com.watch.project.controller.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.common.CommonMethods;
import com.watch.project.dto.MemberDTO;
import com.watch.project.service.member.GoogleMemberService;

@Controller
public class GoogleMemberController {

	@Autowired 
	private GoogleMemberService service;
	@Autowired
	private CommonMethods common;
	
	@GetMapping("/google/login")
	public String redirected(@RequestParam("code") String authCode, HttpServletResponse res, HttpSession session, Model model, RedirectAttributes redirectAttr) throws IOException {
		MemberDTO userInfo = service.loginWithGoogle(authCode);
		String msg = service.storageIfNewOne(userInfo);//기존 회원인지, 새로운 유저면 회원 정보 저장
		
		if(msg != "환영합니다.") {//정상적으로 저장되지 않았을 경우
			model.addAttribute("msg", msg);
			return "member/select_sign_up_type";
		}//정상적 처리일 경우
		session.setAttribute("userEmail", userInfo.getUserEmail());
		session.setAttribute("userLoginType", 4);
		session.setAttribute("accessToken", userInfo.getAccessToken());//id_token
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/";
	}
	
	@GetMapping("/googleSignOut")
	public String signout(HttpSession session, RedirectAttributes redirectAttr) throws IOException {
		session.invalidate();
		redirectAttr.addFlashAttribute("signOutAlert", true);
		return "redirect:/";//redirect 하면 알림 안뜸.
	}
	
//	@GetMapping("/googleUnregister")
//	public String unregister(HttpSession session, Model model) {
//		String msg = common.unregister((String)session.getAttribute("userEmail"));
//		if(msg=="회원탈퇴가 완료되었습니다.") {
//		session.invalidate();
//		}
//		model.addAttribute("msg", msg);
//		return "home";
//	}
}
