package com.watch.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.service.GoogleMemberService;

@Controller
public class GoogleMemberController {

	@Autowired 
	private GoogleMemberService service;
	
	@GetMapping("/google/login")
	public String redirected(@RequestParam("code") String authCode) {
		service.loginWithGoogle(authCode);
		return null;
	}
}
