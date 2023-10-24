package com.watch.project.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MovieInfoDTO;

@Mapper
public interface MovieInfoRepository {
	public MovieInfoDTO getMovieInfoById(String movieId);
	
	public int getPeopleIdByPeopleNmAndMovieNm(Map<String, String> map);
}
