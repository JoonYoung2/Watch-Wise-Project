package com.watch.project.dto.userInfo;

import java.util.List;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieReviewDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class LikedCommentListDTO {
	private List<MovieReviewDTO> movieReviewDto;
	private MovieInfoDTO movieInfoDto;
	
//	//--------------MovieReviewDTO------------------------
//	private String id;
//	private String movieId;
//	private String userEmail;
//	private float reviewScore;
//	private String reviewComment;
//	private String reviewCommentDate;
//	private int reviewCommentLikes;
//	private int isLiked;
//	//---------------MovieInfoDTO-------------------------
//	private String movieId;
//	private String movieNm;
//	private String movieNmEn;
//	private String prdtYear;
//	private String openDt;
//	private String typeNm;
//	private String nations;
//	private String genreNm;
//	private String posterUrl;
//	private int showTime;
//	private String actors;
//	private String cast;
//	private String watchGradeNm;
//	private int likeNum;


}
