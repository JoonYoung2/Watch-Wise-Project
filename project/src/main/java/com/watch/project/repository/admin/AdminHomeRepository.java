package com.watch.project.repository.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.admin.LiveSearchDTO;

@Mapper
public interface AdminHomeRepository {
	public List<LiveSearchDTO> getLiveSearchDataList(String date);
}
