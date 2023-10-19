package com.watch.api.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.api.dto.MovieInfoDTO;

@Mapper
public interface DocidResearchApiRepository {
	
	public List<MovieInfoDTO> getPosterNan();
	
	public void posterUpdate(Map<String, String> map);

	public List<MovieInfoDTO> getDocidNan();

	public void docidUpdate(Map<String, String> map);
	
}
