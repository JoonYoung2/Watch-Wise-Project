package com.watch.project.controller.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.service.member.MemberInfoService;

@Controller
public class MemberInfoController {
	@Autowired
	private MemberInfoService service;
	
	@GetMapping("/userLikedMovieList")
	public String userLikedMovieList(HttpSession session, Model model) {
		List<MovieInfoDTO> likedMovieList = service.getLikedMovieList((String)session.getAttribute("userEmail"));
		model.addAttribute("likedMovieList", likedMovieList);
		return "member/member_info/liked_movie_list";
	}
	
	@GetMapping("/userLikedActorList")
	public String userLikedActorList(HttpSession session, Model model) {
		List<PeopleInfoDetailDTO> likedActorList = service.getLikedActorList((String)session.getAttribute("userEmail"));
		model.addAttribute("likedActorList", likedActorList);
		return "member/member_info/liked_actor_list";
	}
}
