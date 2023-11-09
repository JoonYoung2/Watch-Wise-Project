package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.ContentSearchingDTO;
import com.watch.project.dto.MemberConfigDTO;
import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.relatedSearch.RelatedSearchRequestDTO;
import com.watch.project.dto.relatedSearch.RelatedSearchResponseDTO;

@Mapper
public interface SearchRepository {
	
	public List<MovieInfoDTO> searchingStep1(String query);
	
	public List<MovieInfoDTO> searchingStep2(Map<String, String> map);
	
	public List<PeopleInfoDetailDTO> searchingStep3(String query);
	
	public List<PeopleInfoDetailDTO> searchingStep4(Map<String, String> map);
	
	public List<MovieInfoDTO> getMovieInfoByMovieIds(String movieIds);
	
	public int getPeopleIdByPeopleNmAndMovieNm(Map<String, String> map);

	public int getSearchingCheckByUserEmailAndContent(Map<String, String> searchMap);

	public int contentInsert(ContentSearchingDTO contentSearchingDto);
	
	public String[] recentSearchesByUserEmail(String userEmail);
	
	public String[] popularSearches(String sixMonthAgo);
	
	public void recentSearchesAllRemoveUpdateByUserEmail(String userEmail);
	
	public void recentSearchesAddUpdateByUserEmailAndContent(Map<String, String> map);
	
	public void recentSearchesAddUpdateByUserEmailAndContent2(Map<String, String> map);
	
	public List<RelatedSearchResponseDTO> getRelatedSearchByContentAndUserEmail(RelatedSearchRequestDTO dto);
	
	public List<MovieInfoDTO> getMovieReguxSearching(String content);
	
	public void deleteAllSearchHistory(String userEmail);
	
	public void deleteSearchHistory(String ids);

	public int getSearchHistoryByUserEmail(String userEmail);
	
	public void searchOff(String userEmail);
	
	public void searchOn(String userEmail);
	
	public void insertMemberConfig(String userEmail);
	
	public List<ContentSearchingDTO> getContentSearchByUserEmail(String userEmail);
	
}
