package com.watch.project.repository.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.admin.chart.ActorChartDTO;
import com.watch.project.dto.admin.chart.LiveSearchDTO;
import com.watch.project.dto.admin.chart.MemberTrendChartDTO;
import com.watch.project.dto.admin.chart.MovieChartDTO;

@Mapper
public interface AdminHomeRepository {
	
	public List<LiveSearchDTO> getLiveSearchDataList(String date);
	
	public List<LiveSearchDTO> getLiveSearchDataPeriodList(String fromDate, String toDate);
	
	public List<MovieChartDTO> getPopularMovieList(String date);
	
	public List<MovieChartDTO> getPopularMoviePeriodList(String fromDate, String toDate);
	
	public List<ActorChartDTO> getPopularActorList(String date);
	
	public List<ActorChartDTO> getPopularActorPeriodList(String fromDate, String toDate);
	
	public List<MemberTrendChartDTO> getMemberTrendList();
	
}
