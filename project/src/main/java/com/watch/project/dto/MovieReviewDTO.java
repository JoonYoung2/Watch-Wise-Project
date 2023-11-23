package com.watch.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieReviewDTO {
	private String id;
	private String movieId;
	private String userEmail;
	private float reviewScore;
	private String reviewComment;
	private String reviewCommentDate;
	private int reviewCommentLikes;
	private int isBlack;
//--------허상----------
	private int isLiked;
	private int isReported;
}
