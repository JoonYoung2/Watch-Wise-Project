package com.watch.project.repository.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.admin.MovieInfoDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.dto.admin.PeopleInfoDTO;
import com.watch.project.dto.admin.TableInfoDTO;

@Mapper
public interface AdminMovieInfoRepository {
	
	public List<MovieInfoDTO> getMovieInfoListByStartAndEnd(Map<String, String> map);
	
}
