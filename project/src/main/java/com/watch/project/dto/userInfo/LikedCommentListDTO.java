package com.watch.project.dto.userInfo;

import com.watch.project.dto.MovieReviewDTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikedCommentListDTO {
	private MovieReviewDTO movieReviewDto;
//	//--------------movie_review------------------------
//	private String id; //movieId+userEmail
//	private String userEmail;
//	private float reviewScore;
//	private String reviewComment;
//	private String reviewCommentDate;
//	private String reviewCommentLikes;
	//---------------movie_info-------------------------
	private String movieId;
	private String movieNm;
	private String movieNmEn;
	private String prdtYear;
	private String posterUrl;

}
