package com.watch.project.dto.searchView;

import java.util.ArrayList;
import java.util.List;

import com.watch.project.dto.MovieInfoDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MovieInfoSearchViewDTO {
	private String movieId;
	private String movieNm;
	private String movieNmEn;
	private String openDt;
	private String nations;
	private String genreNm;
	private String watchGradeNm;
	private String posterUrl;
	private String showTime;
	private int likeNum;
	private float gradeAvg;
	private boolean gradeCheck;
	private List<MovieActorsDTO> movieActorList;
	
	@Builder
	public MovieInfoSearchViewDTO(MovieInfoDTO movieInfoDto, boolean gradeCheck) {
		this.movieId = movieInfoDto.getMovieId();
		this.movieNm = movieInfoDto.getMovieNm();
		this.movieNmEn = movieInfoDto.getMovieNmEn();
		this.openDt = movieInfoDto.getOpenDt();
		this.nations = movieInfoDto.getNations();
		this.genreNm = movieInfoDto.getGenreNm();
		this.watchGradeNm = movieInfoDto.getWatchGradeNm();
		this.posterUrl = movieInfoDto.getPosterUrl();
		int time = movieInfoDto.getShowTime();
		String showTime = "";
		if(time % 60 == 0) {
			showTime = time / 60 + "시간";
		}else {
			showTime = time / 60 + "시간 " + time % 60 + "분";
		}
		this.showTime = showTime;
		this.gradeAvg = movieInfoDto.getGradeAvg();
		this.gradeCheck = gradeCheck;
		movieActorList = new ArrayList<>();
	}
}
