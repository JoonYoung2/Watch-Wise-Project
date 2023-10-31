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
	private float reviewScore;//
	private String reviewComment;//
	private String reviewCommentDate;//
}
