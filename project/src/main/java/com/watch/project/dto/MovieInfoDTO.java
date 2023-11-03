package com.watch.project.dto;

import lombok.Data;

@Data
public class MovieInfoDTO {
	// DB Entity
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
	private int likeNum;
	
	// Grade Info
	private float gradeAvg;
	private boolean gradeCheck;
	
	// 개봉 전 영화 표시
	private int dDay;
}
