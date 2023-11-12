package com.watch.project.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.watch.project.service.admin.MovieInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller("adminPeopleInfoController")
@RequiredArgsConstructor
@Slf4j
public class PeopleInfoController {
	private final HttpSession session;
	
	private boolean adminCertification() {
		boolean check = false;
		if(session.getAttribute("admin") == null) {
			check = true;
		}
		return check;
	}
}
