package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.ContentSearchingDTO;
import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;

@Mapper
public interface SearchRepository {
	
	public List<MovieInfoDTO> searchingStep1(String query);
	
	public List<MovieInfoDTO> searchingStep2(String query);
	
	public List<PeopleInfoDetailDTO> searchingStep3(String query);
	
	public List<PeopleInfoDetailDTO> searchingStep4(String query);
	
	public List<MovieInfoDTO> getMovieInfoByMovieIds(String movieIds);
	
	public int getPeopleIdByPeopleNmAndMovieNm(Map<String, String> map);

	public int getSearchingCheckByUserEmailAndContent(Map<String, String> searchMap);

	public int contentInsert(ContentSearchingDTO contentSearchingDto);
	
	public String[] recentSearchesByUserEmail(String userEmail);
	
	public String[] popularSearches(String sixMonthAgo);
	
	public void recentSearchesAllRemoveUpdateByUserEmail(String userEmail);
	
	public void recentSearchesAddUpdateByUserEmailAndContent(Map<String, String> map);
	
}
