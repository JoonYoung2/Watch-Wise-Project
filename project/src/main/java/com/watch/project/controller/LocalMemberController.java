package com.watch.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.MemberDTO;
import com.watch.project.service.LocalMemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LocalMemberController {
	private final LocalMemberService service;
	
	@GetMapping("/signUp")
	public String signUp() {
		return "member/sign_up";
	}
	
	@PostMapping("/signUpDo")
	public String SignUpDo(MemberDTO dto, @RequestParam("pwCh") String pwCh, HttpServletResponse res) throws IOException {
//		System.out.println("dkdkdkdkd"+dto.getUserId());
//		System.out.println("dkdkdkdkd"+dto.getUserPw());
//		System.out.println("dkdkdkdkd"+dto.getUserName());
//		System.out.println("dkdkdkdkd"+dto.getUserEmail());
		String msg = service.SignUpDo(dto, pwCh);
//		System.out.println("controller msg ==> "+msg);
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(msg);
		return null;
	}
	
	@GetMapping("/signIn")
	public String SignIn() {
		return "member/sign_in";
	}
	
	@PostMapping("/signInCheck")
	public String signInCheck(MemberDTO dto, HttpServletResponse res, HttpSession session) throws IOException {
		String msg = service.signInCheck(dto);
		if(!(msg==null)) {
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.print(msg);			
		}else {
			session.setAttribute("userId", dto.getUserId());
			return "redirect:/";
		}
		return null;
	}
	
	@GetMapping("/signOut")
	public String signOut(HttpSession session, HttpServletResponse res) throws IOException {
		String msg = service.signOut((String)session.getAttribute("userId"));
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
}
