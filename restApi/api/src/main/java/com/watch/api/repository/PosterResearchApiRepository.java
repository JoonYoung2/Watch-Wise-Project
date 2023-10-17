package com.watch.api.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.api.dto.MovieInfoDTO;

@Mapper
public interface PosterResearchApiRepository {
	
	public List<MovieInfoDTO> getPosterNan();
	
	public void posterUpdate(Map<String, String> map);
	
}
