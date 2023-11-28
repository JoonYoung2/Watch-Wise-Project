package com.watch.project.controller.member;

import java.security.Provider.Service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.LiveSearchDTO;
import com.watch.project.dto.MemberDTO;
import com.watch.project.dto.WishListDTO;
import com.watch.project.dto.admin.UserNotificationDTO;
import com.watch.project.repository.SearchRepository;
import com.watch.project.service.SearchService;
import com.watch.project.service.member.CommonMemberService;
import com.watch.project.service.member.GoogleMemberService;
import com.watch.project.service.member.KakaoMemberService;
import com.watch.project.service.member.MemberInfoService;
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
	private SearchService searchService;
	@Autowired
	private CommonMemberService common;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private SearchRepository searchRepository;
	
	@GetMapping("/selectSignUpType")
	public String selectSignUpType(Model model, HttpSession session) {
        String naverUrl = naverService.getAuthorizationUrl(session);
        String googleUrl = googleService.getAuthorizationUrl(session);
        
		
        searchService.searchModel(model);
        model.addAttribute("naverLoginUrl", naverUrl);
        model.addAttribute("googleLoginUrl", googleUrl);
		return "member/select_sign_up_type";
	}
	
	@GetMapping("/signIn")
	public String SignIn(Model model, HttpSession session) {
        String naverUrl = naverService.getAuthorizationUrl(session);
        String googleUrl = googleService.getAuthorizationUrl(session);
		
        searchService.searchModel(model);
        model.addAttribute("naverLoginUrl", naverUrl);
        model.addAttribute("googleLoginUrl", googleUrl);
		return "member/sign_in";
	}
	
	@GetMapping("/memberInfo")
	public String socialmemberInfo(HttpSession session, Model model) {
		String userEmail = (String)session.getAttribute("userEmail");
		MemberDTO memberInfo = common.getMemberInfoByEmail(userEmail);
//		WishListDTO wishList = memberInfoService.getWishList(userEmail);
		Map<String, Integer> numbers = common.getNumbersOfDatasForMemberInfo(userEmail);
		
		int searchHistory = 0;
		try {
			searchHistory = searchRepository.getSearchHistoryByUserEmail(userEmail);
		}catch(NullPointerException e) {
		}catch(BindingException e) {
		}
		
		searchService.searchModel(model);
		model.addAttribute("dto", memberInfo); //select *
//		model.addAttribute("wishList", wishList);
		///////////model.addAttribute("profileImg", profileImg);////////
		model.addAttribute("map", numbers);
		model.addAttribute("searchHistory", searchHistory);
		
		return "member/member_info/member_info";
	}
	
	@GetMapping("/socialMemberInfoModify")
	public String MemberInfoModify(HttpSession session, Model model) {
		MemberDTO memberInfo = common.getMemberInfoByEmail((String)session.getAttribute("userEmail"));
		
		searchService.searchModel(model);
		model.addAttribute("dto", memberInfo);
		return "member/social_member/member_info_modify";
	}
	
	@PostMapping("/socialModifyInfoDo")
	public String modifyMemberInfo(MemberDTO dto, RedirectAttributes redirectAttr, Model model) {
		String msg = common.updateMemberName(dto);
		if(msg.equals("정보 수정이 완료되었습니다.")) {
			redirectAttr.addFlashAttribute("msg", msg);
			return "redirect:/memberInfo";			
		} else {
			searchService.searchModel(model);
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
		if(msg.equals("회원탈퇴가 완료되었습니다.")) {
		session.invalidate();
		}
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/";
		
	}
	
	@GetMapping("/showNotifyList")
	public String showNotifyList(HttpSession session, Model model) {
		session.removeAttribute("newNoti");
		List<UserNotificationDTO> notificationList = common.getNotificationList();
		model.addAttribute("notiList", notificationList);
		return "member/notification_list";
	}
}
