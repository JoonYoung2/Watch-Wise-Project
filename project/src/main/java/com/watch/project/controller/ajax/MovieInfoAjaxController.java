package com.watch.project.controller.ajax;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watch.project.dto.MovieLikeDTO;
import com.watch.project.service.MovieInfoService;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MovieInfoAjaxController {
	private final MovieInfoService service;
	
	/*
	 * 영화 좋아요 추가
	 */
	@GetMapping("movieLikeAdd")
	public LikeUpdateResponse peopleLikeAdd(@RequestParam("movieId") String movieId, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		int likeNum = 0;
		
		if(userEmail != null) 
			service.movieLikeAdd(movieId, userEmail);			
		
		
		likeNum = service.getLikeNumById(movieId);
		
        return new LikeUpdateResponse(likeNum);
	}
	

	/*
	 * 영화 좋아요 취소
	 */
	@GetMapping("/movieLikeCancel")
	public LikeUpdateResponse movieLikeCancel(@RequestParam("movieId") String movieId, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		int likeNum = 0;
		
		if(userEmail != null) 
			service.movieLikeCancel(movieId, userEmail);			
		
		
		likeNum = service.getLikeNumById(movieId);

		return new LikeUpdateResponse(likeNum);
	}
	
	@Data
	private class LikeUpdateResponse{
		private int likeNum;
		
		public LikeUpdateResponse(int likeNum) {
			this.likeNum = likeNum;
		}
	}
}
