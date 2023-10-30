package com.watch.project.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MovieLikeDTO {
	private String id;
	private String movieId;
	private String userEmail;
	
	@Builder
	public MovieLikeDTO(String id, String movieId, String userEmail) {
		this.id = id;
		this.movieId = movieId;
		this.userEmail = userEmail;
	}
}
