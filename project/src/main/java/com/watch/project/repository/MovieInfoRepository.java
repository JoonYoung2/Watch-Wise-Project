package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieLikeDTO;
import com.watch.project.dto.movieInfoView.ChartMovieScoreDTO;

@Mapper
public interface MovieInfoRepository {
	//------------ 준영's ---------------------------------
	public MovieInfoDTO getMovieInfoById(String movieId);
	
	public int getPeopleIdByPeopleNmAndMovieNm(Map<String, String> map);

	public void movieLikeAdd(String movieId);

	public void movieLikeInsert(MovieLikeDTO movieLikeDto);

	public int getLikeNumById(String movieId);

	public void movieLikeCancel(String movieId);

	public void movieLikeDelete(String id);

	public MovieLikeDTO getMovieLikeById(String id);
	
	public List<ChartMovieScoreDTO> getChartMovieScoreByMovieId(String movieId);
	
	//------------ 수아's ---------------------------------
	public List<String> getLikedMovieIdByEmail(String userEmail);

	public Integer getAmountOfLikedMovies(String userEmail);
	
	public String getProfileUrlByPeopleId(int peopleId);

}
