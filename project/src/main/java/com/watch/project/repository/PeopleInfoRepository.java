package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;

@Mapper
public interface PeopleInfoRepository {
	
	public PeopleInfoDetailDTO getPeopleInfoDetailById(int peopleId);
	
	public List<MovieInfoDTO> getMovieInfoByMovieIds(String movieIds);
	
	public void peopleLikeAdd(int peopleId);
	
	public void peopleLikeCancel(int peopleId);
	
}
