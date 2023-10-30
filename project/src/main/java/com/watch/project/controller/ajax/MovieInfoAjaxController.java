package com.watch.project.controller.ajax;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watch.project.service.MovieInfoService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MovieInfoAjaxController {
	private final MovieInfoService service;
	
	@GetMapping("movieLikeAdd")
	public LikeUpdateResponse peopleLikeAdd(@RequestParam("movieId") String movieId, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		int likeNum = 0;
		if(userEmail != null) {
			service.movieLikeAdd(movieId, userEmail);			
		}
		
		likeNum = service.getLikeNumById(movieId);

		LikeUpdateResponse response = new LikeUpdateResponse(likeNum);
		
        return response;
	}
	
	@GetMapping("movieLikeCancel")
	public LikeUpdateResponse movieLikeCancel(@RequestParam("movieId") String movieId, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		int likeNum = 0;
		
		service.movieLikeCancel(movieId, userEmail);
		likeNum = service.getLikeNumById(movieId);
		
		LikeUpdateResponse response = new LikeUpdateResponse(likeNum);
		return response;
	}
	
	@Data
	private class LikeUpdateResponse{
		private int likeNum;
		public LikeUpdateResponse(int likeNum) {
			this.likeNum = likeNum;
		}
	}
}
