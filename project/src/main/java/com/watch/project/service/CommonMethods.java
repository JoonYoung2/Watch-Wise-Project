package com.watch.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MemberDTO;
import com.watch.project.repository.MemberRepository;

@Service
public class CommonMethods {
	@Autowired MemberRepository repo;
	
	public String getAlertLocation(String msg, String url) {
		String message = "<script>alert('" + msg + "');";
		message += "location.href='" +url + "';</script>";
		return message;
	}

	public String getAlertHistoryBack(String msg) {
		String message = "<script>alert('" + msg + "');";
		message += "window.history.back();</script>";
		return message;
	}
	
	public String getAlertOnly(String msg) {
		String message = "<script>alert('" + msg + "');</script>";
		return message;
	}
	
	public MemberDTO checkExistingMemberByEmail(String userEmail) { //네이버와 겹침
		MemberDTO result = repo.getUserInfoByEmail(userEmail);
		return result;
	}
	
	public int saveMemberInfo(MemberDTO user) { //네이버와 겹침
		int result = repo.saveMemberInfo(user);
		return result;
	}
}
