package com.watch.project.dto;

import lombok.Data;

@Data
public class MovieInfoDTO {
	private String movieId;
	private String movieNm;
	private String movieNmEn;
	private String prdtYear;
	private String openDt;
	private String typeNm;
	private String nations;
	private String genreNm;
	private String posterUrl;
	private int showTime;
	private String actors;
	private String cast;
	private String watchGradeNm;
}
