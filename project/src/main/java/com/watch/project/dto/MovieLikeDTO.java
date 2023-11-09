package com.watch.project.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class MovieLikeDTO {
	private String id;
	private String movieId;
	private String userEmail;
	private String likeDate;
	
	@Builder
	public MovieLikeDTO(String id, String movieId, String userEmail, String likeDate) {
		this.id = id;
		this.movieId = movieId;
		this.userEmail = userEmail;
		this.likeDate = likeDate;
	}
}
