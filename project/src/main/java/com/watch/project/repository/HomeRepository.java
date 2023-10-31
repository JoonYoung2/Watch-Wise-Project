package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.HomeDTO;
import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieTopInfoDTO;

@Mapper
public interface HomeRepository {
	
	public HomeDTO getId();
	
	public List<MovieTopInfoDTO> getMovieTopInfo(int id);

	public List<MovieInfoDTO> getMovieInfoListByIds(Map<String, String> map);
	
	public List<MovieInfoDTO> upcomingMovies(String openDt);
	
	public List<MovieInfoDTO> recentlyReleasedKoreanMovies(String date);
	
	public List<MovieInfoDTO> recentlyReleasedForeignMovies(String date);
	
	public float getMovieGradeAvgByMovieId(String movieId);
	
	public int getGradeCheckById(String id);
	
}
