package com.watch.project.controller.ajax;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.CommentLikedUsersDTO;
import com.watch.project.dto.MovieReviewDTO;
import com.watch.project.service.ReviewService;
import com.watch.project.service.admin.MemberService;

import lombok.Data;

@RestController
public class ReviewAjaxController {
	@Autowired
	private ReviewService service;
	@Autowired
	private MemberService adminMemberService;
	
	@PostMapping("getReivewScore")
	public MsgResponse reviewWithScore(@RequestBody MovieReviewDTO dto) {
	    float score = dto.getReviewScore();
	    String movieId = dto.getMovieId();
	    String msg = service.insertOrUpdateScore(movieId, score);
	    float gradeAvg = service.getAverageRating(movieId);
	    MsgResponse response = new MsgResponse(msg, gradeAvg);
	    return response;
	}
	@Data
	public class MsgResponse{
		private String msg;
		private float gradeAvg;
		public MsgResponse(String msg, float gradeAvg){
			this.msg = msg;
			this.gradeAvg = gradeAvg;
		}
	}
	
	@PostMapping("/increaseLikeCount")
	public ResultResponse increaseLikeCount(@RequestBody MovieReviewDTO dto, CommentLikedUsersDTO commentDto, HttpSession session) {//userEmail, movieId
		String id = dto.getMovieId() + dto.getUserEmail();//작성자 이메일
		dto.setId(id);
		commentDto.setId(id+(String)session.getAttribute("userEmail"));// +현재 접속한 사용자 이메일
		commentDto.setCommentId(id);
		commentDto.setLikedUserEmail((String)session.getAttribute("userEmail"));
		
		MovieReviewDTO dtoForComment = service.getComment2(dto.getMovieId(),dto.getUserEmail());
		String comment = dtoForComment.getReviewComment();
		adminMemberService.giveNotificationToUserForComment(dto.getUserEmail(), comment);

		
		System.out.println("CommentDto .s et id = "+ commentDto.getId());
		int commentLikeCounts = service.increaseLikeCountForComment(dto, commentDto);
		ResultResponse response = new ResultResponse(commentLikeCounts);
		return response;
	}
	
	@PostMapping("/decreaseLikeCount")
	public ResultResponse decreaseLikeCount(@RequestBody MovieReviewDTO dto, CommentLikedUsersDTO commentDto, HttpSession session) {//userEmail, movieId
		String id = dto.getMovieId() + dto.getUserEmail();
		dto.setId(id);
		commentDto.setId(id+(String)session.getAttribute("userEmail"));// +현재 접속한 사용자 이메일
		commentDto.setCommentId(id);
		commentDto.setLikedUserEmail((String)session.getAttribute("userEmail"));
		
		System.out.println("decreaseLikeCount commentDto.set id = "+ commentDto.getId());
		int commentLikeCounts = service.decreaseLikeCountForComment(dto, commentDto);
		ResultResponse response = new ResultResponse(commentLikeCounts);
		return response;
	}
	@Data
	public class ResultResponse{
		private int result;
		public ResultResponse(int result){
			this.result = result;
		}
	}
	
	@PostMapping("/saveMovieCommentFromMovieInfo")
	public MsgOnlyResponse saveMovieCommentFormMovieInfo(@RequestParam("movieId") String movieId, @RequestParam("reviewComment") String comment) {
		MovieReviewDTO dto = new MovieReviewDTO();
		dto.setMovieId(movieId);
		dto.setReviewComment(comment);
		String msg = service.insertComment(dto);//코멘트 저장
		MsgOnlyResponse response = new MsgOnlyResponse(msg);
		return response;
	}
	
	@PostMapping("/updateMovieCommentFromMyCommentList")
	public MsgOnlyResponse updateMovieCommentFromMyCommentList(@RequestParam("movieId") String movieId, @RequestParam("reviewComment") String reviewComment, HttpSession session, RedirectAttributes redirectAttr) {
		System.out.println(movieId);
		System.out.println(reviewComment);
		MovieReviewDTO dto = new MovieReviewDTO();
		String id = movieId + (String)session.getAttribute("userEmail");
		dto.setId(id);
		dto.setMovieId(movieId);
		dto.setReviewComment(reviewComment);
		String msg = service.updateNewComment(dto);
		MsgOnlyResponse response = new MsgOnlyResponse(msg);
		return response;
	}
	@Data
	public class MsgOnlyResponse{
		private String msg;
		public MsgOnlyResponse(String msg) {
			this.msg = msg;
		}
	}
}
