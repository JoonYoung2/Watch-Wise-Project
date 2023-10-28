package com.watch.project.dto.searchView;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MovieInfoSearchViewDTO {
	private String movieId;
	private String movieNm;
	private String movieNmEn;
	private String openDt;
	private String nations;
	private String genreNm;
	private String posterUrl;
	private int showTime;
	private List<MovieActorsDTO> movieActorList;
	
	public MovieInfoSearchViewDTO(String movieId, String movieNm, String movieNmEn, String openDt, String nations, String genreNm, String posterUrl, int showTime) {
		this.movieId = movieId;
		this.movieNm = movieNm;
		this.movieNmEn = movieNmEn;
		this.openDt = openDt;
		this.nations = nations;
		this.genreNm = genreNm;
		this.posterUrl = posterUrl;
		this.showTime = showTime;
		movieActorList = new ArrayList<>();
	}
}
