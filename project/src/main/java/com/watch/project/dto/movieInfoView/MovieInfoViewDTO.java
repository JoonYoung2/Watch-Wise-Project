package com.watch.project.dto.movieInfoView;

import com.watch.project.dto.MovieInfoDTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class MovieInfoViewDTO {
	private String movieId;
	private String movieNm;
	private String movieNmEn;
	private String prdtYear;
	private String openDt;
	private String typeNm;
	private String nations;
	private String genreNm;
	private String watchGradeNm;
	private int likeNum;
	private String[] posterUrl;
	private String showTime;
	private String[] actors;
	private String[] cast;
	private int dDay;
	
	@Builder
	public MovieInfoViewDTO(MovieInfoDTO movieInfoDto, String showTime,String[] posterUrl, String[] actors, String[] cast, int dDay){
		this.movieId = movieInfoDto.getMovieId();
		this.movieNm = movieInfoDto.getMovieNm();
		this.movieNmEn = movieInfoDto.getMovieNmEn();
		this.prdtYear = movieInfoDto.getPrdtYear();
		this.prdtYear = movieInfoDto.getPrdtYear();
		this.openDt = movieInfoDto.getOpenDt();
		this.typeNm = movieInfoDto.getTypeNm();
		this.nations = movieInfoDto.getNations();
		this.genreNm = movieInfoDto.getGenreNm();
		this.watchGradeNm = movieInfoDto.getWatchGradeNm();
		this.likeNum = movieInfoDto.getLikeNum();
		this.showTime = showTime;
		this.posterUrl = posterUrl;
		this.actors = actors;
		this.cast = cast;
		this.dDay = dDay;
	}
}
