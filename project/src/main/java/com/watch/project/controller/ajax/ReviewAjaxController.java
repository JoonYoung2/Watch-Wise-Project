package com.watch.project.controller.ajax;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.watch.project.dto.CommentLikedUsersDTO;
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
	
	@PostMapping("/increaseLikeCount")
	public MsgResponse increaseLikeCount(@RequestBody MovieReviewDTO dto, CommentLikedUsersDTO commentDto, HttpSession session) {//userEmail, movieId
		String id = dto.getMovieId() + dto.getUserEmail();//작성자 이메일
		dto.setId(id);
		commentDto.setId(id+(String)session.getAttribute("userEmail"));// +현재 접속한 사용자 이메일
		commentDto.setCommentId(id);
		commentDto.setLikedUserEmail((String)session.getAttribute("userEmail"));
		
		System.out.println("CommentDto .s et id = "+ commentDto.getId());
		String msg = service.increaseLikeCountForComment(dto, commentDto);
		MsgResponse response = new MsgResponse(msg);
		return response;
	}
	
	@PostMapping("/decreaseLikeCount")
	public MsgResponse decreaseLikeCount(@RequestBody MovieReviewDTO dto, CommentLikedUsersDTO commentDto, HttpSession session) {//userEmail, movieId
		String id = dto.getMovieId() + dto.getUserEmail();
		dto.setId(id);
		commentDto.setId(id+(String)session.getAttribute("userEmail"));// +현재 접속한 사용자 이메일
		commentDto.setCommentId(id);
		commentDto.setLikedUserEmail((String)session.getAttribute("userEmail"));
		
		System.out.println("decreaseLikeCount commentDto.set id = "+ commentDto.getId());
		String msg = service.decreaseLikeCountForComment(dto, commentDto);
		MsgResponse response = new MsgResponse(msg);
		return response;
	}
}
