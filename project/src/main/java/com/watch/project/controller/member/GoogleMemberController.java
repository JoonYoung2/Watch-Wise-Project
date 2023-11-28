package com.watch.project.controller.member;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.LiveSearchDTO;
import com.watch.project.dto.MemberDTO;
import com.watch.project.service.SearchService;
import com.watch.project.service.admin.MemberService;
import com.watch.project.service.member.GoogleMemberService;

@Controller
public class GoogleMemberController {

	@Autowired 
	private GoogleMemberService service;
	@Autowired
	private SearchService searchService;
	@Autowired
	private MemberService adminMemberService;
	
	@GetMapping("/google/login")
	public String redirected(@RequestParam("code") String authCode, HttpServletResponse res, HttpSession session, Model model, RedirectAttributes redirectAttr) throws IOException {
		MemberDTO userInfo = service.loginWithGoogle(authCode);
		String msg = service.storageIfNewOne(userInfo);//기존 회원인지, 새로운 유저면 회원 정보 저장
		
		if(!msg.equals("환영합니다.")) {//정상적으로 저장되지 않았을 경우
			model.addAttribute("msg", msg);
			searchService.searchModel(model);
			return "member/select_sign_up_type";
		}//정상적 처리일 경우
		int isBlack = adminMemberService.checkIfBlack(userInfo.getUserEmail());
		System.out.println("isBlack=============>"+isBlack);
		int isNewNoti = adminMemberService.checkIfNewNoti(userInfo.getUserEmail());
		System.out.println("isNewNoti=============>"+isNewNoti);

		session.setAttribute("isBlack", isBlack);
		session.setAttribute("newNoti", isNewNoti);
		session.setAttribute("userEmail", userInfo.getUserEmail());
		session.setAttribute("userLoginType", 4);
		session.setAttribute("accessToken", userInfo.getAccessToken());//id_token
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/";
	}
	
	@GetMapping("/googleSignOut")
	public String signout(HttpSession session, RedirectAttributes redirectAttr) throws IOException {
		session.removeAttribute("isBlack");
		session.removeAttribute("newNoti");
		session.removeAttribute("userEmail");
		session.removeAttribute("userLoginType");
		session.removeAttribute("accessToken");
		redirectAttr.addFlashAttribute("signOutAlert", true);
		return "redirect:/";//redirect 하면 알림 안뜸.
	}
}
