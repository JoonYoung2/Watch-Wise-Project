package com.watch.project.controller.member;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.MemberDTO;
import com.watch.project.service.CommonMethods;
import com.watch.project.service.LocalMemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LocalMemberController {
	private final LocalMemberService service;
	private final CommonMethods common;
	

	@GetMapping("/checkEmail")
	public String checkEmail(Model model) {
		model.addAttribute("msg", "이메일 인증 과정이 필요합니다. 본인의 이메일 주소를 입력해주세요.");
		return "member/email_form";
	}
	@GetMapping("/signUp")
	public String signUp() {
		return "member/sign_up";
	}
	
	@PostMapping("/signUpDo")
	public String SignUpDo(MemberDTO dto, @RequestParam("pwCh") String pwCh, HttpServletResponse res, Model model) throws IOException {
		System.out.println("dkdkdkdkd"+dto.getUserEmail());
		System.out.println("dkdkdkdkd"+dto.getUserPw());
		System.out.println("dkdkdkdkd"+dto.getUserName());
		String msg = service.SignUpDo(dto, pwCh);
//		System.out.println("controller msg ==> "+msg);
		if(msg.equals("회원가입이 완료되었습니다.")) {
			String script = service.getAlertLocation(msg, "/signIn");   //여기는 나도 모르겠음 /////////////////
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.print(script); 
		}else {
			model.addAttribute("dto", dto);
			model.addAttribute("msg", msg);
			return "member/sign_up";			
		}
		return null;
	}
	
	
	
	@PostMapping("/signInCheck")
	public String signInCheck(MemberDTO dto, HttpServletResponse res, HttpSession session, RedirectAttributes attr, Model model) throws IOException {
		String msg = service.signInCheck(dto);
		if(msg !="환영합니다.") { //로그인시 입력한 정보가 올바르지 않을 경우
			model.addAttribute("msg", msg);
			model.addAttribute("dto", dto);
			return "member/sign_in";
		}else { //로그인 입력 정보가 올바른 경우
			session.setAttribute("userEmail", dto.getUserEmail());
			session.setAttribute("userLoginType", dto.getUserLoginType());//// 이 부분 다시 고려해보기.
			attr.addFlashAttribute("msg", msg);
			return "redirect:/";
		}
	}
	
	@GetMapping("/signOut")
	public String signOut(HttpSession session, RedirectAttributes redirectAttr) throws IOException {
		session.invalidate();
		redirectAttr.addFlashAttribute("signOutAlert", true);
		return "redirect:/";//redirect 하면 알림 안뜸.
	}
	
	
	@GetMapping("/unregister")
	public String unregister() {
		return "member/unregister_form";
	}
	
	@PostMapping("/passwordCh")
	public String passwordCh(MemberDTO dto, Model model, HttpSession session, RedirectAttributes redirectAttr) throws IOException {
		String msg = service.pwCh(dto);
		if(msg != "회원탈퇴가 완료되었습니다.") {//탈퇴과정에 문제가 있으면
			model.addAttribute("msg", msg);
			return "member/unregister_form";
		}else { //탈퇴 성공시
			session.invalidate();
			redirectAttr.addFlashAttribute("msg", msg);
			return "redirect:/";
		}
	}
	
	@GetMapping("/localMemberInfo")
	public String localMemberInfo(HttpSession session, Model model) {
		MemberDTO memberInfo = common.getMemberInfoByEmail((String)session.getAttribute("userEmail"));
		model.addAttribute("dto", memberInfo);
		return "member/member_info";
	}
	
	@GetMapping("/pwCheck")
	public String pwCheck() {
		return "member/pw_check";
	}
	
	@PostMapping("/verifyPw")
	public String verifyPw(MemberDTO dto, Model model, RedirectAttributes redirectAttr) {
		String msg = service.getMsgAndCompare(dto);
		if(msg != "인증되었습니다.") {
			model.addAttribute("msg", msg);
			return "member/pw_check";
		}else {
			redirectAttr.addFlashAttribute("msg", msg);
			return "redirect:/localMemberInfoModify";
		}
	}
	
	@GetMapping("/localMemberInfoModify")
	public String localMemberInfoModify(HttpSession session, Model model) {
		MemberDTO memberInfo = common.getMemberInfoByEmail((String)session.getAttribute("userEmail"));
		model.addAttribute("dto", memberInfo);
		return "member/member_info_modify";
	}
	
	@PostMapping("/localModifyInfoDo")
	public String localModifyInfoDo(MemberDTO dto, @RequestParam("pwCheck") String pwCh, Model model, RedirectAttributes redirectAttr) {
		if(!(dto.getUserPw()==null||dto.getUserPw().equals(""))) {//비밀번호도 수정란에 입력한 경우
			String msg = service.getMsgAndUpdate(dto, pwCh);
			if(msg != "정보 수정이 완료되었습니다.") {
				model.addAttribute("msg", msg);
				model.addAttribute("dto", dto);
				return "member/member_info_modify"; 
			}else {
				return "redirect:/localMemberInfo";
			}
		}else {//비밀번호는 수정하지 않을 경우
			String msg = common.updateMemberName(dto);
			if(msg == "정보 수정이 완료되었습니다.") {
				redirectAttr.addFlashAttribute("msg", msg);
				return "redirect:/localMemberInfo";			
			} else {
				model.addAttribute("msg", msg);
				return "member/member_info_modify";
			}
		}
	}

}
