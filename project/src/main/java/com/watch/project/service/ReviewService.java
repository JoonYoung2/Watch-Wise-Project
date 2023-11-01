package com.watch.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.project.controller.HomeController;
import com.watch.project.dto.CommentLikedUsersDTO;
import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieReviewDTO;
import com.watch.project.dto.userInfo.ReviewListDTO;
import com.watch.project.repository.MovieInfoRepository;
import com.watch.project.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ReviewService {
	@Autowired
	private ReviewRepository repo;
	@Autowired
	private HttpSession session;
	@Autowired
	private MovieInfoRepository movieInfoRepo;

	public String insertOrUpdateScore(String movieId, float rating) {
		String msg = "";
		if(rating == 0.5){
			msg = "최악이에요";
		}else if(rating == 1.0){
			msg = "싫어요";
		}else if(rating == 1.5){
			msg = "재미없어요";
		}else if(rating == 2.0){
			msg = "별로예요";
		}else if(rating == 2.5){
			msg = "부족해요";
		}else if(rating == 3.0){
			msg = "보통이에요";
		}else if(rating == 3.5){
			msg = "볼만해요";
		}else if(rating == 4.0){
			msg = "재미있어요";
		}else if(rating == 4.5){
			msg = "훌륭해요";
		}else if(rating == 5.0){
			msg = "최고예요";
		}
		String pk = movieId+(String)session.getAttribute("userEmail");
		MovieReviewDTO result = repo.getInfoByPk(pk);
		if(result != null) {//이미 평가를 했던 사람
			result.setReviewScore(rating);
			int updateResult = repo.updateScore(result);
			if(updateResult != 1) {
				msg = "오류가 발생했습니다. 다시 시도해주세요.";
			}
		}else { //처음으로 평가하는 사람
			MovieReviewDTO rookie = new MovieReviewDTO();
			rookie.setId(pk);
			rookie.setMovieId(movieId);
			rookie.setUserEmail((String)session.getAttribute("userEmail"));
			rookie.setReviewScore(rating);
			rookie.setReviewComment("nan");
//			rookie.setReviewCommentDate("nan");

			int insertResult = repo.insertScore(rookie);
			if(insertResult != 1) {
				msg = "오류가 발생했습니다. 다시 시도해주세요.";
			}
		}
		return msg;
	}

	public String insertComment(MovieReviewDTO dto) {//movieId, reviewComment
		String msg = "저장되었습니다.";
		if(dto.getReviewComment() == null || dto.getReviewComment().equals("")) {
			msg ="코멘트를 입력해주세요.";
			return msg;
		}
		String pk = dto.getMovieId()+(String)session.getAttribute("userEmail");
		float existance = checkScore(pk);
				
		String dateStr = getDate();
		
		dto.setId(pk);
		dto.setUserEmail((String)session.getAttribute("userEmail"));
		dto.setReviewScore(existance);
		dto.setReviewCommentDate(dateStr);
		
		if(existance > 0) {//평점을 먼저한 사람 => 이미 데이터 행이 있음. update
			int updateResult = repo.updateForComment(dto);
			if(updateResult != 1) {
				msg = "오류가 발생했습니다. 다시 시도해주세요.";
			}
		} else {//코멘트를 먼저하는 사람 => 데이터 행 없음. insert
			int storageResult = repo.insertForComment(dto);
			if(storageResult != 1) {
				msg = "오류가 발생했습니다. 다시 시도해주세요.";
			}
		}
		return msg;
	}

	private String getDate() {
		Date currentDate = new Date(); //Fri Oct 27 21:35:40 KST 2023
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// Date를 ISO 8601 형식의 문자열로 변환
		String dateStr = dateFormat.format(currentDate);//2023-10-27T21:35:40Z
		return dateStr;
	}

	private float checkScore(String id) {
		float reviewScore = 0;
		try {
			reviewScore = repo.getStoredScore(id);	
			System.out.println("try문 내부의 reviewScore : "+reviewScore);
		}catch(Exception e){
			System.out.println("에러 발생");
		}
			return reviewScore;
	}

	public List<MovieReviewDTO> getEveryCommentForThisMovie(String movieId) {
		List<MovieReviewDTO> comments = repo.selectComments(movieId);
		for(MovieReviewDTO comment : comments) {
			String CommentLikedUsersDtoId = comment.getId() + (String)session.getAttribute("userEmail");
			
			CommentLikedUsersDTO likedComment = repo.selectLikedComment(CommentLikedUsersDtoId);
		
			if(likedComment != null) {
				comment.setIsLiked(1);
			}else {
				comment.setIsLiked(0);
			}
		}
		return comments;
	}

	public MovieReviewDTO getComment(String movieId) {
		String pkId = movieId + (String)session.getAttribute("userEmail");
		MovieReviewDTO reviewComment = repo.getComment(pkId);
		return reviewComment;
	}

	public String deleteComment(String id) {
		String msg = "해당 코멘트가 삭제되었습니다.";
		int deleteResult = repo.deleteComment(id);
		if(deleteResult != 1) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return msg;
	}

	public String modifyComment(MovieReviewDTO dto) {
		String msg = "코멘트 수정이 완료되었습니다.";
		String id = dto.getMovieId()+(String)session.getAttribute("userEmail");
		String dateStr = getDate();
		
		dto.setId(id);
		dto.setUserEmail((String)session.getAttribute("userEmail"));
		dto.setReviewCommentDate(dateStr);

		int updateResult = repo.updateForComment(dto);
		if(updateResult != 1) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return msg;
	}

	public int increaseLikeCountForComment(MovieReviewDTO dto, CommentLikedUsersDTO commentDto) {//id, movieId, userEmail
		String msg="";
		int updateResult = repo.increaseLikeCountForComment(dto);
		int insertResult = repo.insertLikedUserInfo(commentDto);
		int commentLikeCount = repo.selectCommentLikes(dto);
		if(updateResult != 1||insertResult !=1) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return commentLikeCount;
	}

	public int decreaseLikeCountForComment(MovieReviewDTO dto, CommentLikedUsersDTO commentDto) {//id, movieId, userEmail
		String msg="";
		int updateResult = repo.decreaseLikeCountForComment(dto);
		int deleteResult = repo.deleteLikedUserInfo(commentDto);
		int commentLikeCount = repo.selectCommentLikes(dto);
		if(updateResult != 1 || deleteResult != 1) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return commentLikeCount;
	}

	public float getAverageRating(String movieId) {
		float averageRating = 0.0f;
		try {
			 averageRating = repo.getAverageRating(movieId);			
		}catch(NullPointerException e) {
			
		}catch(BindingException e) {
			
		}
		return averageRating;
	}

	public List<ReviewListDTO> getReviewList(String userEmail) {
		List<MovieReviewDTO> reviewInfoList = repo.selectReviewListByEmail(userEmail);//해당 유저의 모든 리뷰 조회
		List<ReviewListDTO> reviewList = new ArrayList<>();
		for(MovieReviewDTO movie : reviewInfoList) {
			MovieInfoDTO movieInfo = movieInfoRepo.getMovieInfoById(movie.getMovieId());
			ReviewListDTO tempDto = new ReviewListDTO();
			tempDto.setId(movie.getId());
			tempDto.setMovieId(movie.getMovieId());
			tempDto.setUserEmail(movie.getUserEmail());
			tempDto.setReviewScore(movie.getReviewScore());
			tempDto.setReviewComment(movie.getReviewComment());
			tempDto.setReviewCommentDate(movie.getReviewCommentDate());
			tempDto.setMovieNm(movieInfo.getMovieNm());
			tempDto.setMovieNmEn(movieInfo.getMovieNmEn());
			tempDto.setPosterUrl(movieInfo.getPosterUrl());
			reviewList.add(tempDto);
		}
		return reviewList;
	}

}
