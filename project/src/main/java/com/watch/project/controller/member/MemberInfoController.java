package com.watch.project.controller.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.LiveSearchDTO;
import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.WishListDTO;
import com.watch.project.service.SearchService;
import com.watch.project.service.member.MemberInfoService;

@Controller
public class MemberInfoController {
	@Autowired
	private MemberInfoService service;
	
	@Autowired
	private SearchService searchService;
	
	@GetMapping("/userLikedMovieList")
	public String userLikedMovieList(HttpSession session, Model model) {
		List<MovieInfoDTO> likedMovieList = service.getLikedMovieList((String)session.getAttribute("userEmail"));
		
		searchService.searchModel(model);
		model.addAttribute("likedMovieList", likedMovieList);
		return "member/member_info/liked_movie_list";
	}
	
	@GetMapping("/userLikedActorList")
	public String userLikedActorList(HttpSession session, Model model) {
		List<PeopleInfoDetailDTO> likedActorList = service.getLikedActorList((String)session.getAttribute("userEmail"));

		searchService.searchModel(model);
		model.addAttribute("likedActorList", likedActorList);
		return "member/member_info/liked_actor_list";
	}
	
	@GetMapping("/addMovieIntoWishList")
	public String addMovieIntoWishList(@RequestParam("movieId") String movieId, RedirectAttributes redirectAttr) {
		String msg = service.addMovieIntoWishList(movieId);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/movieInfo?movieId="+movieId;
	}
	
	@GetMapping("/deleteMovieFromWishList")
	public String deleteMovieFromWishList(@RequestParam("movieId") String movieId, RedirectAttributes redirectAttr) {
		String msg = service.deleteMovieFromWishList(movieId);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/movieInfo?movieId="+movieId;
	}
	
	@GetMapping("/movieWishList")
	public String movieWishList(HttpSession session, Model model) {
		List<WishListDTO> dto = service.getWishList((String)session.getAttribute("userEmail"));
		model.addAttribute("wishListDto",dto);
		return "member/member_info/movie_wish_list";
	}
}
