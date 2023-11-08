package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.PeopleLikeDTO;

@Mapper
public interface PeopleInfoRepository {
	
	//------------ 준영's ---------------------------------

	public PeopleInfoDetailDTO getPeopleInfoDetailById(int peopleId);
	
	public List<MovieInfoDTO> getMovieInfoByMovieIds(String movieIds);
	
	public void peopleLikeAdd(int peopleId);
	
	public void peopleLikeCancel(int peopleId);
	
	public PeopleLikeDTO getPeopleLikeById(String id);
	
	public void peopleLikeInsert(PeopleLikeDTO dto);
	
	public void peopleLikeDelete(String id);
	
	public int getLikeNumById(int peopleId);

	
	//------------ 수아's ---------------------------------
	
	public List<Integer> getLikedActorIdByEmail(String userEmail);

	public Integer getAountOfLikedActors(String userEmail);
	
}
