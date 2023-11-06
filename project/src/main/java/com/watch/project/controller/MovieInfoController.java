package com.watch.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieReviewDTO;
import com.watch.project.dto.movieInfoView.GradeInfoDTO;
import com.watch.project.dto.movieInfoView.MovieInfoViewDTO;
import com.watch.project.dto.movieInfoView.PeopleInfoDTO;
import com.watch.project.service.MovieInfoService;
import com.watch.project.service.ReviewService;
import com.watch.project.service.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MovieInfoController {
	private final MovieInfoService service;
	private final ReviewService reviewService;
	private final SearchService searchService;
	private final HttpSession session;
	
	@GetMapping("movieInfo")
	public String movieInfo(@RequestParam String movieId, Model model) {
		
		int movieLikedCheck = 0;
		String userEmailStr = (String) session.getAttribute("userEmail");
		MovieInfoDTO movieInfoDto = service.getMovieInfoById(movieId);
		
		/*
		 * 모든 코멘트 정보
		 */
		List<MovieReviewDTO> comments = reviewService.getEveryCommentForThisMovie(movieId);
		
		/*
		 * 로그인한 사용자가 작성한 코멘트
		 */
		MovieReviewDTO ifWroteComment = reviewService.getComment(movieId);
		
		/*
		 * 영화 정보 
		 */
		MovieInfoViewDTO movieInfoViewDto = service.getMovieInfoViewDto(movieInfoDto);
		
		/*
		 * 영화 출연진 가져오기
		 */
		PeopleInfoDTO peopleInfoDto = null;
		if(!movieInfoViewDto.getActors()[0].equals("nan")) {	// 출연진이 있는 경우
			peopleInfoDto = service.getPeopleInfoDto(movieInfoViewDto);
		}
		
		/*
		 * 영화 좋아요 & 평가 수 
		 */
		GradeInfoDTO gradeInfoDto = service.getGradeInfoDto(movieId);
		
		/*
		 * 사용자 영화 좋아요 체크
		 */
		movieLikedCheck = service.getMovieLikeCheck(movieId, userEmailStr);
		
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 최근 검색어 + 인기 검색어 크기
		 */
		int recentSearchesSize = -1;
		try {
			recentSearchesSize = recentSearches.length;			
		}catch(NullPointerException e) {
			
		}
		
		model.addAttribute("peopleInfo", peopleInfoDto);
		model.addAttribute("movieLikedCheck", movieLikedCheck);
		model.addAttribute("comments", comments);
		model.addAttribute("ifWroteComment", ifWroteComment);
		model.addAttribute("movieInfo", movieInfoViewDto);
		model.addAttribute("gradeInfo", gradeInfoDto);
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("recentSearchesSize", recentSearchesSize);
		
		return "basic/movie_info";
	}
}