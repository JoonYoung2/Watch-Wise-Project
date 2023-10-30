package com.watch.project.dto.movieInfoView;

import lombok.Data;

@Data
public class MovieInfoViewDTO {
	private String movieId;
	private String movieNm;
	private String movieNmEn;
	private String prdtYear;
	private String openDt;
	private String typeNm;
	private String nations;
	private String genreNm;
	private String[] posterUrl;
	private String showTime;
	private String[] actors;
	private String[] cast;
	private String watchGradeNm;
	private String likeNum;
	private int dDay;
}
