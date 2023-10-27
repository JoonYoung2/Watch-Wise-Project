package com.watch.project.controller.member;

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
import com.watch.project.service.member.CommonMemberService;
import com.watch.project.service.member.KakaoMemberService;

@Controller
public class KakaoMemberController {
	@Autowired
	private KakaoMemberService service;
	
	@Autowired
	private CommonMemberService common;
	
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
//			mav.setViewName("member/social_member/email_form");
//			return mav;
//		}

		MemberDTO userInfo = common.getMemberInfoByEmail(kakaoInput.getUserEmail());//유저 정보를 가져오기
		System.out.println("userInfo ----> " + userInfo);			

		
		if (userInfo != null) {  //이미 회원인 경우
			if(userInfo.getKakaoAgreement()!=1) {
				repo.updateKakaoAgreement(kakaoInput.getUserEmail());
			}
			userInfo.setKakaoRefreshToken((String)session.getAttribute("refreshToken"));
			repo.updateKakaoRefreshToken(userInfo);
			session.setAttribute("userEmail", userInfo.getUserEmail());
			session.setAttribute("accessToken", accessToken);
			session.setAttribute("userLoginType", 2);
			re.addFlashAttribute("msg", "환영합니다.");
			mav.setViewName("redirect:/");
			return mav;
		} 
		else {  //새로운 유저일 경우
//			if((int)session.getAttribute("email_needs")==0) {//이메일 제공 동의 한 사람의 경우
				kakaoInput.setUserPw("kakaoMember");
				kakaoInput.setUserLoginType(2);
				
				session.setAttribute("userEmail", kakaoInput.getUserEmail());
				session.setAttribute("accessToken", accessToken);
				session.setAttribute("userLoginType", 2);
//				kakaoInput.setKakaoAccessToken(accessToken);
//				service.storageAccessToken(accessToken);
				repo.saveMemberInfo(kakaoInput);
				repo.updateKakaoAgreement(kakaoInput.getUserEmail());
				kakaoInput.setKakaoRefreshToken((String)session.getAttribute("refreshToken"));
				repo.updateKakaoRefreshToken(kakaoInput);
				re.addFlashAttribute("msg", "환영합니다.");
				mav.setViewName("redirect:/");
//			}else { //이메일 제공 동의 안한 사람의 경우
//				mav.addObject("member", kakaoInput); 
//				mav.addObject("msg", "이메일 인증 과정이 필요합니다. 본인의 이메일 주소를 입력해주세요.");
//				mav.setViewName("member/social_member/email_form");
//				session.setAttribute("accessToken", accessToken);
//			}
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
			return "member/social_member/email_auth";
		} else {
			model.addAttribute("msg", msg);
			model.addAttribute("member", dto);
			return "member/social_member/email_form";
		}
	}

	@GetMapping("/kakaoSignOut")
	public String logout(HttpSession session, RedirectAttributes redirectAttr) {
		session.invalidate();
		redirectAttr.addFlashAttribute("signOutAlert", true);
		return "redirect:/";//redirect 하면 알림 안뜸.

	}
	
	
//	@GetMapping("/kakaoUnregister")
//	public void kakaoUnregister(HttpSession session, Model model) {
//		String accessToken = service.getAccessTokenByRefreshToken();
//		service.kakaoRevokeAgreement((String)session.getAttribute("accessToken"));
//		String msg = common.unregister((String)session.getAttribute("userEmail"));
//		if(msg=="회원탈퇴가 완료되었습니다.") {
//		session.invalidate();
//		}
//		model.addAttribute("msg", msg);
//		return "home";
//	}


}
