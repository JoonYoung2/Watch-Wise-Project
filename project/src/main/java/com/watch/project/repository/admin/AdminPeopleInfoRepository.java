package com.watch.project.repository.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.admin.MovieInfoDTO;
import com.watch.project.dto.admin.PeopleInfoDTO;

@Mapper
public interface AdminPeopleInfoRepository {

	public List<PeopleInfoDTO> getPeopleInfoListByStartAndEnd(Map<String, String> map);

	public List<PeopleInfoDTO> getPeopleInfoListByStartAndEndQuery(Map<String, String> map);
	
	public void insertPeopleInfo(PeopleInfoDTO peopleInfoDto);

	public void updatePeopleInfo(PeopleInfoDTO peopleInfoDto);

	public void deletePeopleInfoByPeopleId(int peopleId);

	public void deletePeopleLikeByPeopleId(int peopleId);
	
}
