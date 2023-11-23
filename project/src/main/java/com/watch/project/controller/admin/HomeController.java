package com.watch.project.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.admin.chart.ActorChartDTO;
import com.watch.project.dto.admin.chart.LiveSearchDTO;
import com.watch.project.dto.admin.chart.MovieChartDTO;
import com.watch.project.service.admin.HomeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller("adminHomeController")
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	private final HttpSession session;
	private final HomeService service;
	
	@GetMapping("/admin")
	public String index(Model model) {
		if(adminCertification()) {
			return "admin/login";
		}
		
		/*
		 * 실시간 인기 검색어
		 */
		List<LiveSearchDTO> liveSearchList = service.getLiveSearchDataList();
		
		/*
		 * 회원 동향
		 */
		
		/*
		 * 인기 영화
		 */
		List<MovieChartDTO> movieChartList = service.getPopularMovieList();
		
		/*
		 * 인기 배우
		 */
		List<ActorChartDTO> actorChartList = service.getPopularActorList();
		
		log.info("{}", liveSearchList.size());
		log.info("{}", movieChartList.size());
		log.info("{}", actorChartList.size());
		
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("movieChart", movieChartList);
		model.addAttribute("actorChart", actorChartList);
		
		return "admin/index";
	}
	
	@GetMapping("/admin/login")
	public String login() {
		if(!adminCertification()) {
			return "redirect:/admin";
		}
		return "admin/login";
	}
	
	@PostMapping("/admin/login")
	public String login(@RequestParam String id, @RequestParam String pw, Model model) {
		log.info("id => {}",id);
		log.info("pw => {}",pw);
		if(id.equals("admin")) {
			if(pw.equals("1234")) {
				session.setAttribute("admin", "admin");
				return "redirect:/admin";
			}else {
				model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
				return "admin/login";
			}	
		}else {
			model.addAttribute("msg", "없는 아이디입니다.");
			return "admin/login";
		}
	}
	
	@GetMapping("/admin/logout")
	public String logout() {
		session.removeAttribute("admin");
		return "redirect:/admin";
	}
	
	private boolean adminCertification() {
		boolean check = false;
		if(session.getAttribute("admin") == null) {
			check = true;
		}
		return check;
	}
}
