package com.watch.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.watch.api.dto.MovieInfoDTO;
import com.watch.api.dto.MovieTopInfoDTO;

@Mapper
public interface MovieInfoApiRepository {
	
	public MovieInfoDTO getMovieInfoByMovieId(String movieId);
	
	public void movieInfoSave(MovieInfoDTO dto);
	
	public List<MovieInfoDTO> getMovieIdByUrlNan();
	
	public void moviePosterUrlUpdate(String movieId, String posterUrl);
	
	public List<MovieInfoDTO> getAllMovieNotNan();

	public void saveDailyTopInfo(MovieTopInfoDTO dto);
	
	public void saveWeeklyTopInfo(MovieTopInfoDTO dto);
	
	public MovieTopInfoDTO getDailyTopInfoById(int id);
	
	public MovieTopInfoDTO getWeeklyTopInfoById(int id);
	
}
