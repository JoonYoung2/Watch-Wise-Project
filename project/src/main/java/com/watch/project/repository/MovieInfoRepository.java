package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieLikeDTO;

@Mapper
public interface MovieInfoRepository {
	public MovieInfoDTO getMovieInfoById(String movieId);
	
	public int getPeopleIdByPeopleNmAndMovieNm(Map<String, String> map);

	public void movieLikeAdd(String movieId);

	public void movieLikeInsert(MovieLikeDTO movieLikeDto);

	public int getLikeNumById(String movieId);

	public void movieLikeCancel(String movieId);

	public void movieLikeDelete(String id);

	public MovieLikeDTO getMovieLikeById(String id);
	
	//---------아래 getLikedMovieIdByEmail는 수아가 건드림? 추가함------------------------

	public List<String> getLikedMovieIdByEmail(String userEmail);

	public String getProfileUrlByPeopleId(int peopleId);
}
