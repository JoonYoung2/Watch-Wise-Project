package com.watch.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.MemberDTO;
import com.watch.project.service.LocalMemberService;

import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequiredArgsConstructor
public class LocalMemberController {
	private final LocalMemberService service;
	

	@GetMapping("/checkEmail")
	public String checkEmail() {
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
			String script = service.getAlertLocation(msg, "/signIn");
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
	public String signInCheck(MemberDTO dto, HttpServletResponse res, HttpSession session) throws IOException {
		String msg = service.signInCheck(dto);
		if(!(msg==null)) {
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.print(msg);			
		}else {
			session.setAttribute("userEmail", dto.getUserEmail());
			session.setAttribute("userLoginType", dto.getUserLoginType());//// 이 부분 다시 고려해보기.
			return "redirect:/";
		}
		return null;
	}
	
	@GetMapping("/signOut")
	public String signOut(HttpSession session, HttpServletResponse res) throws IOException {
		String msg = service.signOut((String)session.getAttribute("userEmail"));
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(msg);
		return null;
	}
	
	
	@GetMapping("/unregister")
	public String unregister() {
		return "member/unregister_form";
	}
	
	@PostMapping("/passwordCh")
	public String passwordCh(MemberDTO dto, HttpServletResponse res) throws IOException {
		String msg = service.pwCh(dto);
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(msg);
		return null;
	}
	
//	@PostMapping("sendEmail")
//	public String sendEmail() {
//		
//	}
}
