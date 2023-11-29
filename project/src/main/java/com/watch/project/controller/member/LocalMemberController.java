package com.watch.project.controller.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.LiveSearchDTO;
import com.watch.project.dto.MemberDTO;
import com.watch.project.service.SearchService;
import com.watch.project.service.admin.MemberService;
import com.watch.project.service.member.CommonMemberService;
import com.watch.project.service.member.LocalMemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LocalMemberController {
	private final LocalMemberService service;
	private final CommonMemberService common;
	private final SearchService searchService;
	private final MemberService adminMemberService;
	

	@GetMapping("/checkEmail")
	public String checkEmail(Model model) {
		searchService.searchModel(model);
		model.addAttribute("msg", "이메일 인증 과정이 필요합니다. 본인의 이메일 주소를 입력해주세요.");
		return "member/local_member/email_form";
	}
	@GetMapping("/signUp")
	public String signUp(Model model) {
		
		searchService.searchModel(model);
		return "member/local_member/sign_up";
	}
	
	@PostMapping("/signUpDo")
	public String SignUpDo(MemberDTO dto, @RequestParam("pwCh") String pwCh, HttpServletResponse res, Model model) throws IOException {
		String msg = service.SignUpDo(dto, pwCh);
		
		if(msg.equals("회원가입이 완료되었습니다.")) {
			String script = service.getAlertLocation(msg, "/signIn");  
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.print(script); 
		}else {
			searchService.searchModel(model);
			model.addAttribute("dto", dto);
			model.addAttribute("msg", msg);
			return "member/local_member/sign_up";			
		}
		return null;
	}
	
	
	
	@PostMapping("/signInCheck")
	public String signInCheck(MemberDTO dto, HttpServletResponse res, HttpSession session, RedirectAttributes attr, Model model) throws IOException {
		String msg = service.signInCheck(dto);
		if(!msg.equals("환영합니다.")) { //로그인시 입력한 정보가 올바르지 않을 경우
			searchService.searchModel(model);
			model.addAttribute("msg", msg);
			model.addAttribute("dto", dto);
			return "member/sign_in";
		}else { //로그인 입력 정보가 올바른 경우
			int isBlack = adminMemberService.checkIfBlack(dto.getUserEmail());
			int isNewNoti = adminMemberService.checkIfNewNoti(dto.getUserEmail());
			String profileImg = common.getProfileImg(dto.getUserEmail());
			session.setAttribute("profileImg", profileImg);
			session.setAttribute("isBlack", isBlack);			
			session.setAttribute("userEmail", dto.getUserEmail());
			session.setAttribute("userLoginType", dto.getUserLoginType());//// 이 부분 다시 고려해보기.
			session.setAttribute("newNoti", isNewNoti);
			attr.addFlashAttribute("msg", msg);
			System.out.println("isBlack=============>"+isBlack);
			System.out.println("isNewNoti=============>"+isNewNoti);
			return "redirect:/";
		}
	}
	
	@GetMapping("/signOut")
	public String signOut(HttpSession session, RedirectAttributes redirectAttr) throws IOException {
		session.removeAttribute("userEmail");
		session.removeAttribute("userLoginType");
		session.removeAttribute("newNoti");
		session.removeAttribute("isBlack");
		session.removeAttribute("profileImg");
		redirectAttr.addFlashAttribute("signOutAlert", true);
		return "redirect:/";
	}
	
	
	@GetMapping("/unregister")
	public String unregister(Model model) {
		searchService.searchModel(model);
		return "member/local_member/unregister_form";
	}
	
	@PostMapping("/passwordCh")
	public String passwordCh(MemberDTO dto, Model model, HttpSession session, RedirectAttributes redirectAttr) throws IOException {
		String msg = service.pwCh(dto);
		if(!msg.equals ("회원탈퇴가 완료되었습니다.")) {//탈퇴과정에 문제가 있으면
			searchService.searchModel(model);
			model.addAttribute("msg", msg);
			return "member/local_member/unregister_form";
		}else { //탈퇴 성공시
			session.invalidate();
			redirectAttr.addFlashAttribute("msg", msg);
			return "redirect:/";
		}
	}
	
	@GetMapping("/pwCheck")
	public String pwCheck(Model model) {
		searchService.searchModel(model);
		return "member/local_member/pw_check";
	}
	
	@PostMapping("/verifyPw")
	public String verifyPw(MemberDTO dto, Model model, RedirectAttributes redirectAttr) {
		String msg = service.getMsgAndCompare(dto);
		if(!msg.equals("인증되었습니다.")) {
			searchService.searchModel(model);
			model.addAttribute("msg", msg);
			return "member/local_member/pw_check";
		}else {
			redirectAttr.addFlashAttribute("msg", msg);
			return "redirect:/localMemberInfoModify";
		}
	}
	
	@GetMapping("/localMemberInfoModify")
	public String localMemberInfoModify(HttpSession session, Model model) {
		MemberDTO memberInfo = common.getMemberInfoByEmail((String)session.getAttribute("userEmail"));
		
		searchService.searchModel(model);
		model.addAttribute("dto", memberInfo);
		return "member/local_member/member_info_modify";
	}
	
	@PostMapping("/localModifyInfoDo")
	public String localModifyInfoDo(MemberDTO dto, @RequestParam("pwCheck") String pwCh, Model model, RedirectAttributes redirectAttr) {
		if(!(dto.getUserPw()==null||dto.getUserPw().equals(""))) {//비밀번호도 수정란에 입력한 경우
			String msg = service.getMsgAndUpdate(dto, pwCh);
			if(!msg.equals("정보 수정이 완료되었습니다.")) {
				searchService.searchModel(model);
				model.addAttribute("msg", msg);
				model.addAttribute("dto", dto);
				return "member/local_member/member_info_modify"; 
			}else {
				return "redirect:/memberInfo";
			}
		}else {//비밀번호는 수정하지 않을 경우
			String msg = common.updateMemberName(dto);
			if(msg.equals("정보 수정이 완료되었습니다.")) {
				redirectAttr.addFlashAttribute("msg", msg);
				return "redirect:/memberInfo";			
			} else {
				searchService.searchModel(model);
				model.addAttribute("msg", msg);
				return "member/local_member/member_info_modify";
			}
		}
	}

}
