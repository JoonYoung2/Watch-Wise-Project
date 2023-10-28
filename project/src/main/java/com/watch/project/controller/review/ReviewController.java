package com.watch.project.controller.review;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.MovieReviewDTO;
import com.watch.project.service.ReviewService;

import lombok.Data;
import oracle.jdbc.proxy.annotation.Post;

@RestController
public class ReviewController {
	@Autowired
	private ReviewService service;
	
	@GetMapping("/testSua")
	public String test() {
		return "test";
	}
	
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
	
	@PostMapping("/saveComment")
	public String saveComment(MovieReviewDTO dto, RedirectAttributes redirectAttr) {//movieId, reviewComment
		String msg = service.insertComment(dto);//코멘트 저장
		List<MovieReviewDTO> comments = service.getEveryCommentForThisMovie(dto.getMovieId()); //해당 영화에 대한 다른 사용자들의 코멘트들 수집
		String movieId=dto.getMovieId();
		redirectAttr.addFlashAttribute("msg", msg);
		redirectAttr.addFlashAttribute("comments", comments);		
		return "redirect:/movieInfo?movieId="+movieId;
	}
}
