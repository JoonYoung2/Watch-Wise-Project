package com.watch.project.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.watch.project.service.admin.MemberService;

@Controller
public class MemberCoroller {
@Autowired private MemberService service;

	@GetMapping("/admin/memer_list")
	public String adminMemberList() {
		
		return "admin/member/member_list";
	}
}
