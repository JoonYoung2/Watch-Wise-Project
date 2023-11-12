package com.watch.project.dto.admin;

import lombok.Data;

@Data
public class MovieInfoDTO {
	// DB Entity
	private int num;
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
	private String docid;
	private int likeNum;
}
