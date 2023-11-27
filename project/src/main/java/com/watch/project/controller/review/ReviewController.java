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

import com.watch.project.dto.LiveSearchDTO;
import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieReviewDTO;
import com.watch.project.dto.memberInfo.LikedCommentListDTO;
import com.watch.project.dto.memberInfo.MyScoredListDTO;
import com.watch.project.dto.memberInfo.ReviewListDTO;
import com.watch.project.service.ReviewService;
import com.watch.project.service.SearchService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ReviewController {
	@Autowired
	private ReviewService service;
	@Autowired
	private SearchService searchService;

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
	
	@GetMapping("/userMyCommentList")
	public String userMyCommentList(Model model, HttpSession session) {
		List<ReviewListDTO> reviewList = service.getReviewList((String)session.getAttribute("userEmail"));
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("reviewList", reviewList);
		return "member/member_info/my_comment_list";
	}

	@GetMapping("/userMyScoredMovieList")
	public String userMyScoredMovieList(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		Map<String, List<MyScoredListDTO>> lists = service.getScoredMovieListByScore(userEmail);
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("lists", lists);
		return "member/member_info/my_scored_movie_list";
	}
	
	@GetMapping("/viewMoviesInlistFive")
	public String viewMoviesInlistScore(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		List<MyScoredListDTO> listScore = service.getScoreFive(userEmail);
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("listScore", listScore);
		model.addAttribute("score", "listFive");
		return "member/member_info/my_scored_movie_lists_by_score";
	}
	@GetMapping("/viewMoviesInlistFourPointFive")
	public String viewMoviesInlistFourPointFive(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		List<MyScoredListDTO> listScore = service.getScoreFourPointFive(userEmail);
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("listScore", listScore);
		model.addAttribute("score", "listFourPointFive");
		return "member/member_info/my_scored_movie_lists_by_score";
	}
	@GetMapping("/viewMoviesInlistFour")
	public String viewMoviesInlistFour(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		List<MyScoredListDTO> listScore = service.getScoreFour(userEmail);
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("listScore", listScore);
		model.addAttribute("score", "listFour");
		return "member/member_info/my_scored_movie_lists_by_score";
	}
	@GetMapping("/viewMoviesInlistThreePointFive")
	public String viewMoviesInlistThreePointFive(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		List<MyScoredListDTO> listScore = service.getScoreThreePointFive(userEmail);
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("listScore", listScore);
		model.addAttribute("score", "listThreePointFive");
		return "member/member_info/my_scored_movie_lists_by_score";
	}
	@GetMapping("/viewMoviesInlistThree")
	public String viewMoviesInlistThree(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		List<MyScoredListDTO> listScore = service.getScoreThree(userEmail);
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("listScore", listScore);
		model.addAttribute("score", "listThree");
		return "member/member_info/my_scored_movie_lists_by_score";
	}
	@GetMapping("/viewMoviesInlistTwoPointFive")
	public String viewMoviesInlistTwoPointFive(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		List<MyScoredListDTO> listScore = service.getScoreTwoPointFive(userEmail);
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("listScore", listScore);
		model.addAttribute("score", "listTwoPointFive");
		return "member/member_info/my_scored_movie_lists_by_score";
	}
	@GetMapping("/viewMoviesInlistTwo")
	public String viewMoviesInlistTwo(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		List<MyScoredListDTO> listScore = service.getScoreTwo(userEmail);
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("listScore", listScore);
		model.addAttribute("score", "listTwo");
		return "member/member_info/my_scored_movie_lists_by_score";
	}
	@GetMapping("/viewMoviesInlistOnePointFive")
	public String viewMoviesInlistOnePointFive(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		List<MyScoredListDTO> listScore = service.getScoreOnePointFive(userEmail);
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("listScore", listScore);
		model.addAttribute("score", "listOnePointFive");
		return "member/member_info/my_scored_movie_lists_by_score";
	}
	@GetMapping("/viewMoviesInlistOne")
	public String viewMoviesInlistOne(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		List<MyScoredListDTO> listScore = service.getScoreOne(userEmail);
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("listScore", listScore);
		model.addAttribute("score", "listOne");
		return "member/member_info/my_scored_movie_lists_by_score";
	}
	@GetMapping("/viewMoviesInlistZeroPointFive")
	public String viewMoviesInlistZeroPointFive(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		List<MyScoredListDTO> listScore = service.getScoreZeroPointFive(userEmail);
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("listScore", listScore);
		model.addAttribute("score", "listZeroPointFive");
		return "member/member_info/my_scored_movie_lists_by_score";
	}

	@GetMapping("/userLikedCommentList")//////위치바꾸기
	public String userLikedCommentList(Model model, HttpSession session) {
		List<LikedCommentListDTO> likedCommentList = service.getLikedCommentList((String)session.getAttribute("userEmail"));
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = searchService.getLiveSearchList();
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("likedCommentList", likedCommentList);
		return "member/member_info/liked_comment_list";
	}
	
	@GetMapping("/viewAllCommentsForThisMovie")
	public String viewAllComments4ThisMovie(@RequestParam("movieId") String movieId, Model model) {
		List<MovieReviewDTO> comments = service.getEveryCommentForThisMovie(movieId);
		model.addAttribute("comments", comments);
		model.addAttribute("movieId", movieId);
		return "member/review/all_comments";
	}
	

}
