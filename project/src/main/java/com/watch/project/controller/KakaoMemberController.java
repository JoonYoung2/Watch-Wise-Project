package com.watch.project.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.watch.project.dto.MemberDTO;
import com.watch.project.service.KakaoMemberService;

@Controller
public class KakaoMemberController {
	@Autowired
	private KakaoMemberService service;
	
	@RequestMapping(value = "/signIn/kakao")
	public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// 1번 인증코드 요청 전달"
		String accessToken = service.getAccessToken(code);
		// 2번 인증코드로 토큰 전달
		MemberDTO kakaoInput = new MemberDTO();
		try {
			kakaoInput = service.getUserInfo(accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		MemberDTO userInfo = service.getMember(kakaoInput.getUserId());
		//stem.out.println("login info : " + userInfo.toString());
		System.out.println("userInfo ----> " + userInfo);
		
		if (userInfo != null) {  //이미 회원인 경우
			session.setAttribute("user_id", userInfo.getUserId());
			//session.setAttribute("accessToken", accessToken);
			session.setAttribute("loginType", userInfo.getUserLoginType());
			mav.setViewName("redirect:/");
			return mav;
		} 
		else {  //새로운 유저일 경우
			mav.addObject("member", kakaoInput);
			mav.setViewName("user/member/kakao_join/step1");
		return mav;
		}
	}
	
	@PostMapping("/kakao_join.do")
	public String Store(@Valid MemberDTO dto, BindingResult br, Model model ) {
		System.out.println("gdgd");
		String msg = "";
		if (br.hasErrors()) {
			msg="입력이 올바르지 않습니다.";
			model.addAttribute("msg", msg);
			model.addAttribute("member", dto);
			return "user/member/kakao_join/step1";
		}
		msg = service.getJoinMsg(dto);
		if(msg=="가입완료")	
		return "redirect:/";
		else {
			model.addAttribute("msg", msg);
			model.addAttribute("member", dto);
			return "user/member/kakao_join/step1";
		}
	}

	@RequestMapping(value="/kakao_logout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		session.invalidate();
		mav.setViewName("redirect:/");
		return mav;
	}


}
