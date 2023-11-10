package com.watch.project.controller.review;

import java.util.List;
import java.util.Map;

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
import com.watch.project.dto.memberInfo.LikedCommentListDTO;
import com.watch.project.dto.memberInfo.MyScoredListDTO;
import com.watch.project.dto.memberInfo.ReviewListDTO;
import com.watch.project.service.ReviewService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ReviewController {
	@Autowired
	private ReviewService service;

	@PostMapping("/saveComment")
	public String saveComment(MovieReviewDTO dto, RedirectAttributes redirectAttr) {//movieId, reviewComment
		String msg = service.insertComment(dto);//코멘트 저장
		String movieId=dto.getMovieId();
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/movieInfo?movieId="+movieId;
	}
	
	@GetMapping("/deleteComment")
	public String deleteComment(@RequestParam("id") String id, @RequestParam("movieId") String movieId, RedirectAttributes redirectAttr) {
		String msg = service.deleteComment(id);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/movieInfo?movieId="+movieId;
	}
	
	@GetMapping("/deleteCommentFromMyCommentList")
	public String deleteCommentFromMyCommentList(@RequestParam("id") String id, @RequestParam("movieId") String movieId, RedirectAttributes redirectAttr) {
		String msg = service.deleteComment(id);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/userMyCommentList";
	}
	@PostMapping("/modifyComment")
	public String modifyComment(MovieReviewDTO dto, RedirectAttributes redirectAttr) {
		String msg = service.modifyComment(dto);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/movieInfo?movieId="+dto.getMovieId();
	}
	
//	@GetMapping("/userReviewList")
//	public String userReviewList(Model model, HttpSession session) {
//		List<ReviewListDTO> reviewList = service.getReviewList((String)session.getAttribute("userEmail")); 
//		model.addAttribute("reviewList", reviewList);
//		return "member/member_info/review_list";
//	}
	
	@GetMapping("/userMyCommentList")
	public String userMyCommentList(Model model, HttpSession session) {
		List<ReviewListDTO> reviewList = service.getReviewList((String)session.getAttribute("userEmail"));
		log.info("reviewList => {}", reviewList);
		model.addAttribute("reviewList", reviewList);
		return "member/member_info/my_comment_list";
	}
	
//	@GetMapping("/userMyScoredMovieList")
//	public String userMyScoredMovieList(Model model, HttpSession session) {
//		List<ReviewListDTO> reviewList = service.getReviewList((String)session.getAttribute("userEmail"));
//		model.addAttribute("reviewList", reviewList);
//		return "member/member_info/my_scored_movie_list";
//	}
	@GetMapping("/userMyScoredMovieList")
	public String userMyScoredMovieList(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		Map<String, List<MyScoredListDTO>> lists = service.getScoredMovieListByScore(userEmail);
//		List<MyScoredListDTO> listFive = service.getScoreFive(userEmail); //스코어 5.0인 애들
//		List<MyScoredListDTO> listFourPointFive = service.getScoreFourPointFive(userEmail); //스코어 4.5인 애들
//		List<MyScoredListDTO> listFour = service.getScoreFour(userEmail); //스코어 4.0인 애들
//		List<MyScoredListDTO> listThreePointFive = service.getScoreThreePointFive(userEmail); //스코어 3.5인 애들
//		List<MyScoredListDTO> listThree = service.getScoreThree(userEmail); //스코어 3.0인 애들
//		List<MyScoredListDTO> listTwoPointFive = service.getScoreTwoPointFive(userEmail); //스코어 2.5인 애들
//		List<MyScoredListDTO> listTwo = service.getScoreTwo(userEmail); //스코어 2.0인 애들
//		List<MyScoredListDTO> listOnePointFive = service.getScoreOnePointFive(userEmail); //스코어 1.5인 애들
//		List<MyScoredListDTO> listOne = service.getScoreOne(userEmail); //스코어 1.0인 애들
//		List<MyScoredListDTO> listZeroPointFive = service.getScoreZeroPointFive(userEmail); //스코어 0.5인 애들
		model.addAttribute("lists", lists);
		return "member/member_info/my_scored_movie_list";
	}
	
	
	
	
	@GetMapping("/userLikedCommentList")
	public String userLikedCommentList(Model model, HttpSession session) {
		List<LikedCommentListDTO> likedCommentList = service.getLikedCommentList((String)session.getAttribute("userEmail"));
		model.addAttribute("likedCommentList", likedCommentList);
		return "member/member_info/liked_comment_list";
	}
	
	

}
