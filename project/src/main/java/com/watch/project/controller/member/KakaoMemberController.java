package com.watch.project.controller.member;

import java.util.List;

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

import com.watch.project.dto.LiveSearchDTO;
import com.watch.project.dto.MemberDTO;
import com.watch.project.repository.MemberRepository;
import com.watch.project.service.SearchService;
import com.watch.project.service.admin.MemberService;
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
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private MemberService adminMemberService;
	
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

		MemberDTO userInfo = common.getMemberInfoByEmail(kakaoInput.getUserEmail());//유저 정보를 가져오기

		if (userInfo != null) {  //이미 회원인 경우
			if(userInfo.getKakaoAgreement()!=1) {
				repo.updateKakaoAgreement(kakaoInput.getUserEmail());
			}
			userInfo.setKakaoRefreshToken((String)session.getAttribute("refreshToken"));
			repo.updateKakaoRefreshToken(userInfo);
			int isBlack = adminMemberService.checkIfBlack(userInfo.getUserEmail());
			System.out.println("isBlack=============>"+isBlack);
			int isNewNoti = adminMemberService.checkIfNewNoti(userInfo.getUserEmail());
			System.out.println("isNewNoti=============>"+isNewNoti);
			String profileImg = common.getProfileImg(userInfo.getUserEmail());
			session.setAttribute("profileImg", profileImg);
			session.setAttribute("isBlack", isBlack);	
			session.setAttribute("newNoti", isNewNoti);
			session.setAttribute("userEmail", userInfo.getUserEmail());
			session.setAttribute("accessToken", accessToken);
			session.setAttribute("userLoginType", 2);
			re.addFlashAttribute("msg", "환영합니다.");
			mav.setViewName("redirect:/");
			return mav;
		} 
		else {  //새로운 유저일 경우
			kakaoInput.setUserPw("kakaoMember");
			kakaoInput.setUserLoginType(2);
			
			kakaoInput.setJoinedDate(common.getJoinedDate());
			kakaoInput.setProfileImg("nan");
			
			session.setAttribute("userEmail", kakaoInput.getUserEmail());
			session.setAttribute("accessToken", accessToken);
			session.setAttribute("userLoginType", 2);
			session.setAttribute("profileImg", "nan");
			
			repo.saveMemberInfo(kakaoInput);
			repo.updateKakaoAgreement(kakaoInput.getUserEmail());
			kakaoInput.setKakaoRefreshToken((String)session.getAttribute("refreshToken"));
			repo.updateKakaoRefreshToken(kakaoInput);
			re.addFlashAttribute("msg", "환영합니다.");
			mav.setViewName("redirect:/");
		}
		return mav;
	}
	
	@PostMapping("/kakaoEmailSend")
	public String kakaoEmailSend( MemberDTO dto, Model model ) {
		String msg = service.getMsgOrSend(dto);
		
		searchService.searchModel(model);
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
		session.removeAttribute("profileImg");
		session.removeAttribute("userEmail");
		session.removeAttribute("userLoginType");
		session.removeAttribute("newNoti");
		session.removeAttribute("isBlack");
		session.removeAttribute("accessToken");
		redirectAttr.addFlashAttribute("signOutAlert", true);
		return "redirect:/";//redirect 하면 알림 안뜸.

	}
}
