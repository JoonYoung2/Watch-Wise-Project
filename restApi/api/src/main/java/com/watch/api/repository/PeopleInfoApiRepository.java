package com.watch.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.watch.api.dto.PeopleInfoDTO;
import com.watch.api.dto.PeopleInfoDetailDTO;

@Mapper
public interface PeopleInfoApiRepository {
	
	public PeopleInfoDTO getPeopleInfoById(int peopleId);
	
	public void savePeopleInfo(PeopleInfoDTO dto);
	
	public List<PeopleInfoDTO> getPeopleInfoAll();

	public void savePeopleInfoDetail(PeopleInfoDetailDTO dto);
	
	public void updateProfileUrlByPeopleId(PeopleInfoDetailDTO dto);
	
	public List<PeopleInfoDetailDTO> getAllPeopleInfoDetail();

	public int checkMovieExsist(String query);
}
