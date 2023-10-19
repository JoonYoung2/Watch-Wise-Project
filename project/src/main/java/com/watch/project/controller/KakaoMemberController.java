package com.watch.project.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.MemberDTO;
import com.watch.project.repository.MemberRepository;
import com.watch.project.service.KakaoMemberService;

@Controller
public class KakaoMemberController {
	@Autowired
	private KakaoMemberService service;
	
	@Autowired
	private MemberRepository repo;
	
	@RequestMapping(value = "/signIn/kakao")
	public ModelAndView login(@RequestParam("code") String code, HttpSession session, RedirectAttributes re) {
		String msg = "";
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
		
//		if(kakaoInput.getUserEmail()==null) {
//			mav.setViewName("member/social_sign_up/email_form");
//			return mav;
//		}

			MemberDTO userInfo = service.getMember(kakaoInput.getSocialLoginId());//유저 정보를 가져오기
			//stem.out.println("login info : " + userInfo.toString());
			System.out.println("userInfo ----> " + userInfo);			

		
		if (userInfo != null) {  //이미 회원인 경우
			session.setAttribute("userEmail", userInfo.getUserEmail());
			//session.setAttribute("accessToken", accessToken);
			session.setAttribute("userLoginType", 2);
			mav.setViewName("redirect:/");
			return mav;
		} 
		else {  //새로운 유저일 경우
			if((int)session.getAttribute("email_needs")==0) {//이메일 제공 동의 한 사람의 경우
				kakaoInput.setUserPw("kakaoMember");
				kakaoInput.setUserLoginType(2);
				
				session.setAttribute("userEmail", kakaoInput.getUserEmail());
				session.setAttribute("userLoginType", 2);
				
				repo.saveMemberInfo(kakaoInput);
				mav.setViewName("redirect:/");
			}else { //이메일 제공 동의 안한 사람의 경우
				mav.addObject("member", kakaoInput); 
				mav.setViewName("member/social_sign_up/email_form");

			}
		}
		return mav;
	}
	
	@PostMapping("/kakaoEmailSend")
	public String kakaoEmailSend( MemberDTO dto, Model model ) {
		System.out.println("gdgd");
		String msg = "";
		
		msg = service.getMsgOrSend(dto);
		if(msg==null) {
			model.addAttribute("dto", dto);
			return "member/social_sign_up/email_auth";
		} else {
			model.addAttribute("msg", msg);
			model.addAttribute("member", dto);
			return "member/social_sign_up/email_form";
		}
	}

	@GetMapping("/kakaoSignOut")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		session.invalidate();
		mav.setViewName("redirect:/");
		return mav;
	}
	
	

}
