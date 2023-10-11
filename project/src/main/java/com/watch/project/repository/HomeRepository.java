package com.watch.project.repository;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.HomeDTO;

@Mapper
public interface HomeRepository {
	public HomeDTO getId(); 
}
