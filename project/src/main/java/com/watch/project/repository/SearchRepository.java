package com.watch.project.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;

@Mapper
public interface SearchRepository {
	
	public List<MovieInfoDTO> searchingStep1(String query);

	public List<MovieInfoDTO> searchingStep2(String query);
	
	public List<PeopleInfoDetailDTO> searchingStep3(String query);
	
	public List<PeopleInfoDetailDTO> searchingStep4(String query);
	
	public List<MovieInfoDTO> getMovieInfoByMovieIds(String movieIds);
	
}
