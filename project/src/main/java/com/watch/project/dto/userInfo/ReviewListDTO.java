package com.watch.project.dto.userInfo;

import lombok.Data;

@Data
public class ReviewListDTO {
	private String id;//
	private String movieId;// //
	private String userEmail;//
	private String movieNm;
	private String movieNmEn;
	private String posterUrl;
	private String genreNm;
	private float reviewScore;//
	private String reviewComment;//
	private String reviewCommentDate;//
	private int reviewCommentLikes;//
	private int liked;//1이면 좋아요한 코멘트, 0이면 좋아요하지 않은 코멘트
}
