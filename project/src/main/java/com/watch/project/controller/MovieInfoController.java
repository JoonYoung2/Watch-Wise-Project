package com.watch.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieReviewDTO;
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
		
		MovieInfoDTO movieInfoDto = service.getMovieInfoById(movieId);
		MovieInfoViewDTO movieInfoViewDto = service.setMMovieInfoViewDto(movieInfoDto);
		PeopleInfoDTO peopleInfoDto = new PeopleInfoDTO();
		int likeCheck = 0;
		
		if(!movieInfoViewDto.getActors()[0].equals("nan")) {	// 출연진이 있는 경우
			if(movieInfoViewDto.getActors().length >= 12) {		// 출연진이 12명이상인 경우
				int castSize = movieInfoViewDto.getCast().length;
				peopleInfoDto.setPeopleId(new int[12]);
				peopleInfoDto.setPeopleNm(new String[12]);
				peopleInfoDto.setPeopleCast(new String[castSize]);
				for(int i = 0; i < 12; ++i) {
					String peopleNm = movieInfoViewDto.getActors()[i];
					String movieNm = movieInfoViewDto.getMovieNm();
					String peopleCast = "nan";
					if(!movieInfoViewDto.getCast()[0].equals("nan")) {
						if(i <= castSize-1) {
							peopleCast = movieInfoViewDto.getCast()[i];						
						}
					}
					peopleInfoDto.getPeopleId()[i] = service.getPeopleIdByPeopleNmAndMovieNm(peopleNm, movieNm);
					peopleInfoDto.getPeopleNm()[i] = peopleNm;
					if(i <= castSize-1) {
						peopleInfoDto.getPeopleCast()[i] = peopleCast;						
					}
				}
			}else {
				int peopleSize = movieInfoViewDto.getActors().length;
				int castSize = movieInfoViewDto.getCast().length;
				peopleInfoDto.setPeopleId(new int[peopleSize]);
				peopleInfoDto.setPeopleNm(new String[peopleSize]);
				peopleInfoDto.setPeopleCast(new String[castSize]);
				for(int i = 0; i < peopleSize; ++i) {
					String peopleNm = movieInfoViewDto.getActors()[i];
					String movieNm = movieInfoViewDto.getMovieNm();
					String peopleCast = "nan";
					if(!movieInfoViewDto.getCast()[0].equals("nan")) {
						if(i <= castSize-1) {
							peopleCast = movieInfoViewDto.getCast()[i];						
						}
					}
					peopleInfoDto.getPeopleId()[i] = service.getPeopleIdByPeopleNmAndMovieNm(peopleNm, movieNm);
					peopleInfoDto.getPeopleNm()[i] = peopleNm;
					if(i <= castSize-1) {
						peopleInfoDto.getPeopleCast()[i] = peopleCast;						
					}
				}
			}
			peopleInfoDto.setEnd(peopleInfoDto.getPeopleId().length-1);
			model.addAttribute("peopleInfo", peopleInfoDto);
		}
		/*
		 영화 좋아요 체크 
		*/
		String userEmail = (String) session.getAttribute("userEmail");
		likeCheck = service.getMovieLikeCheck(movieId, userEmail);
		model.addAttribute("likeCheck", likeCheck);
		List<MovieReviewDTO> comments = reviewService.getEveryCommentForThisMovie(movieId);//이 영화에 대한 모든 사용자의 코멘트들 가져오는 메서드
		MovieReviewDTO ifWroteComment = reviewService.getComment(movieId);//로그인한 사용자가 작성한 코멘트가 있다면 가져옴
		//float score = reviewService.getScore(movieId); //로그인한 사용자가 평가한 점수를 가져옴.
		float averageRating = reviewService.getAverageRating(movieId);//영화의 평점의 평균을 가져옴
		model.addAttribute("comments", comments);
		model.addAttribute("ifWroteComment", ifWroteComment);
		model.addAttribute("movieInfo", movieInfoViewDto);
		model.addAttribute("averageRating", averageRating);
		return "basic/movie_info";
	}
}