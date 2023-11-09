package com.watch.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.watch.api.dto.LiveSearchDTO;

@Mapper
public interface LiveSearchRepository {
	public List<LiveSearchDTO> getLiveSearchDataList();
	
	public List<LiveSearchDTO> getNewLiveSearchDataList(String date);
	
	public void deleteLiveSearchData();
	
	public void deleteLiveSearch();
	
	public void insertLiveSearchData(LiveSearchDTO dto);
	
	public void insertLiveSearch(LiveSearchDTO dto);
}
