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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MovieInfoController {
	private final MovieInfoService service;
	private final ReviewService reviewService;
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
		MovieInfoViewDTO movieInfoViewDto = service.setMMovieInfoViewDto(movieInfoDto);
		/*
		 * 영화 좋아요 & 평가 수 
		 */
		GradeInfoDTO gradeInfoDto = service.getGradeInfoDto(movieId);
		/*
		 * 사용자 영화 좋아요 체크
		 */
		movieLikedCheck = service.getMovieLikeCheck(movieId, userEmailStr);
		/*
		 * 영화 출연진 가져오기
		 */
		PeopleInfoDTO peopleInfoDto = null;
		
		if(!movieInfoViewDto.getActors()[0].equals("nan")) {	// 출연진이 있는 경우
			peopleInfoDto = service.setPeopleInfoDto(movieInfoViewDto);
		}
		
		model.addAttribute("peopleInfo", peopleInfoDto);
		model.addAttribute("movieLikedCheck", movieLikedCheck);
		model.addAttribute("comments", comments);
		model.addAttribute("ifWroteComment", ifWroteComment);
		model.addAttribute("movieInfo", movieInfoViewDto);
		model.addAttribute("gradeInfo", gradeInfoDto);
		return "basic/movie_info";
	}
}