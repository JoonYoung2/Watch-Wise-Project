package com.watch.project.repository.recommended;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MovieInfoDTO;

@Mapper
public interface RecommendedRepository {
	
	/*
	 * 회원 개별 추천
	 * 평가한 영화 중 가장 많은 장르를 골라
	 * 좋아요가 많은 영화 순으로 추천해준다.
	 */
	public String[] getMovieIdByUserEmail(String userEmail);
	
	public String getGenreNmByMovieIds(String movieIds);
	
	public List<MovieInfoDTO> getMovieInfoByGenreNm(String genreNm);
}
