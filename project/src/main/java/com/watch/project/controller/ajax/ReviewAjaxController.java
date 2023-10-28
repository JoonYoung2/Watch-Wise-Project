package com.watch.project.controller.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.watch.project.dto.MovieReviewDTO;
import com.watch.project.service.ReviewService;

import lombok.Data;

@RestController
public class ReviewAjaxController {
	@Autowired
	private ReviewService service;
	
	@PostMapping("getReivewScore")
	public MsgResponse reviewWithScore(@RequestBody MovieReviewDTO dto) {
	    float score = dto.getReviewScore();
	    String movieId = dto.getMovieId();
	    
	    String msg = service.insertOrUpdateScore(movieId, score);
	    MsgResponse response = new MsgResponse(msg);
	    return response;
	}
	
	@Data
	public class MsgResponse{
		private String msg;
		public MsgResponse(String msg){
			this.msg = msg;
		}
	}
}
