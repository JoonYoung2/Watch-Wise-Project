package com.watch.project.controller.member;

import java.security.Provider.Service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.common.CommonMethods;
import com.watch.project.dto.MemberDTO;
import com.watch.project.service.member.GoogleMemberService;
import com.watch.project.service.member.KakaoMemberService;
import com.watch.project.service.member.NaverMemberService;

@Controller
public class CommonMemberController {
	@Autowired
	private KakaoMemberService kakaoService;
	@Autowired 
	private NaverMemberService naverService;
	@Autowired
	private GoogleMemberService googleService;
	@Autowired
	private CommonMethods common;
	
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
	
	@GetMapping("/socialMemberInfo")
	public String socialmemberInfo(HttpSession session, Model model) {
		MemberDTO memberInfo = common.getMemberInfoByEmail((String)session.getAttribute("userEmail"));
		model.addAttribute("dto", memberInfo);
		return "member/social_member/member_info";
	}
	
	@GetMapping("/socialMemberInfoModify")
	public String MemberInfoModify(HttpSession session, Model model) {
		MemberDTO memberInfo = common.getMemberInfoByEmail((String)session.getAttribute("userEmail"));
		model.addAttribute("dto", memberInfo);
		return "member/social_member/member_info_modify";
	}
	
	@PostMapping("/socialModifyInfoDo")
	public String modifyMemberInfo(MemberDTO dto, RedirectAttributes redirectAttr, Model model) {
		String msg = common.updateMemberName(dto);
		if(msg == "정보 수정이 완료되었습니다.") {
			redirectAttr.addFlashAttribute("msg", msg);
			return "redirect:/socialMemberInfo";			
		} else {
			model.addAttribute("msg", msg);
			return "member/social_member/member_info_modify";
		}
	}
	
	@GetMapping("/selectAgreedSocial")
	public String selectAgreedSocial(HttpSession session, RedirectAttributes redirectAttr) {
		String email = (String)session.getAttribute("userEmail");
		MemberDTO dto = common.getMemberInfoByEmail(email);
		if(dto.getKakaoAgreement() == 1) {
			kakaoService.unregisterProcess(dto);
		}
		if(dto.getNaverAgreement() == 1) {
			naverService.unregisterProcess(dto);
		}

		String msg = common.unregister(email);
		if(msg=="회원탈퇴가 완료되었습니다.") {
		session.invalidate();
		}
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/";
		
	}
}
