package com.watch.project.controller.review;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieReviewDTO;
import com.watch.project.dto.userInfo.LikedCommentListDTO;
import com.watch.project.dto.userInfo.ReviewListDTO;
import com.watch.project.service.ReviewService;

@Controller
public class ReviewController {
	@Autowired
	private ReviewService service;
	
	@GetMapping("/testSua")
	public String test() {
		return "test";
	}

	@PostMapping("/saveComment")
	public String saveComment(MovieReviewDTO dto, RedirectAttributes redirectAttr) {//movieId, reviewComment
		String msg = service.insertComment(dto);//코멘트 저장
		String movieId=dto.getMovieId();
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/movieInfo?movieId="+movieId;
	}
	
	@GetMapping("/delete_comment")
	public String deleteComment(@RequestParam("id") String id, @RequestParam("movieId") String movieId, RedirectAttributes redirectAttr) {
		String msg = service.deleteComment(id);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/movieInfo?movieId="+movieId;
	}
	
	@PostMapping("/modifyComment")
	public String modifyComment(MovieReviewDTO dto, RedirectAttributes redirectAttr) {
		String msg = service.modifyComment(dto);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/movieInfo?movieId="+dto.getMovieId();
	}
	
	@GetMapping("/userReviewList")
	public String userReviewList(Model model, HttpSession session) {
		List<ReviewListDTO> reviewList = service.getReviewList((String)session.getAttribute("userEmail")); 
		model.addAttribute("reviewList", reviewList);
		return "member/common/review_list";
	}
	
	@GetMapping("/userLikedCommentList")
	public String userLikedCommentList(Model model, HttpSession session) {
		List<LikedCommentListDTO> likedCommentList = service.getLikedCommentList((String)session.getAttribute("userEmail"));
		model.addAttribute("likedCommentList", likedCommentList);
		return "member/common/liked_comment_list";
	}
	
	

}
