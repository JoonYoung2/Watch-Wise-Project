package com.watch.project.dto;

import lombok.Data;

@Data
public class WishListDTO {
	private String id;
	private String userEmail;
	private String movieId;
	private String movieNm;
	private String moviePoster;
	private String addedDate;
}