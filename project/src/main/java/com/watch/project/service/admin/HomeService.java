package com.watch.project.service.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.watch.project.dto.admin.chart.ActorChartDTO;
import com.watch.project.dto.admin.chart.LiveSearchDTO;
import com.watch.project.dto.admin.chart.MovieChartDTO;
import com.watch.project.repository.admin.AdminHomeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("adminHomeSerivce")
@RequiredArgsConstructor
@Slf4j
public class HomeService {
	
	private final AdminHomeRepository repo;
	
	public List<LiveSearchDTO> getLiveSearchDataList(){
		String date = getDate().substring(0, 10);
		return repo.getLiveSearchDataList(date);
	}
	
	public List<LiveSearchDTO> getLiveSearchDataPeriodList(String fromDate, String toDate){
		fromDate = getFromDate(fromDate);
		toDate = getToDate(toDate);
		return repo.getLiveSearchDataPeriodList(fromDate, toDate);
	}
	
	public List<MovieChartDTO> getPopularMovieList(){
		List<MovieChartDTO> movieChartList = repo.getPopularMovieList(getDate());
		movieChartList = setPosterUrl(movieChartList);
		return movieChartList;
	}

	public List<MovieChartDTO> getPopularMoviePeriodList(String fromDate, String toDate){
		fromDate = getFromDate(fromDate);
		toDate = getToDate(toDate);
		List<MovieChartDTO> movieChartList = repo.getPopularMoviePeriodList(fromDate, toDate);
		movieChartList = setPosterUrl(movieChartList);
		return movieChartList;
	}
	
	public List<ActorChartDTO> getPopularActorList(){
		return repo.getPopularActorList(getDate());
	}
	
	public List<ActorChartDTO> getPopularActorPeriodList(String fromDate, String toDate){
		fromDate = getFromDate(fromDate);
		toDate = getToDate(toDate);
		return repo.getPopularActorPeriodList(fromDate, toDate);
	}
	
	private List<MovieChartDTO> setPosterUrl(List<MovieChartDTO> movieChartList) {
		for(int i = 0; i < movieChartList.size(); ++i) {
			String posterUrl = movieChartList.get(i).getPosterUrl().split("\\|")[0];
			movieChartList.get(i).setPosterUrl(posterUrl);
		}
		return movieChartList;
	}
	
	private String getToDate(String toDate) {
		String year = toDate.substring(0, 4);
		String month = toDate.substring(4, 6);
		String day = toDate.substring(6, 8);
		toDate = year + "/" + month + "/" + day + " 23:59:59";
		return toDate;
	}

	private String getFromDate(String fromDate) {
		String year = fromDate.substring(0, 4);
		String month = fromDate.substring(4, 6);
		String day = fromDate.substring(6, 8);
		fromDate = year + "/" + month + "/" + day;
		return fromDate;
	}

	private String getDate() {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(currentDate);
	}
}
