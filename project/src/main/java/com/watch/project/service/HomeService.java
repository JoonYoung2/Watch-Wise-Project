package com.watch.project.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Service;

import com.watch.project.dto.HomeDTO;
import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieTopInfoDTO;
import com.watch.project.repository.HomeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeService {
	private final HomeRepository repo;
	private final HttpSession session;
	public HomeDTO getId() {
		
		return repo.getId();
	}
	
	public List<MovieTopInfoDTO> getMovieTopInfo(){
		int id = getDateId();
		return repo.getMovieTopInfo(id);
	}
	
	public List<MovieInfoDTO> getMovieInfoListByIds(String ids){
		String[] movieId = ids.replaceAll("'", "").split(",");
		String ids2 = "'" + ids.replaceAll("'", "") + "'";
		Map<String, String> map = new HashMap<>();
		map.put("ids", ids);
		map.put("ids2", ids2);
		List<MovieInfoDTO> movieInfoList = repo.getMovieInfoListByIds(map);
		
		for(int i = 0; i < movieInfoList.size(); ++i) {
			float gradeAvg = getMovieGradeAvgByMovieId(movieId[i]);
			movieInfoList.get(i).setGradeAvg(gradeAvg);
			String id = movieId[i] + (String)session.getAttribute("userEmail");
			boolean gradeCheck = false;
			if(id != null) {
				 gradeCheck = getMovieGradeCheckById(id);				
			}
			movieInfoList.get(i).setGradeCheck(gradeCheck);
		}
		
		return movieInfoList;
	}
	
	private boolean getMovieGradeCheckById(String id) {
		boolean gradeCheck = false;
		int check = repo.getGradeCheckById(id);
		if(check == 1)
			gradeCheck = true;
		
		return gradeCheck;
	}

	public List<MovieInfoDTO> upcomingMovies(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		String openDt = dtFormat.format(cal.getTime());
		
		List<MovieInfoDTO> upcoming = repo.upcomingMovies(openDt);
		
		for(int i = 0; i < upcoming.size(); ++i) {
			String posterUrl = upcoming.get(i).getPosterUrl().split("\\|")[0];
			upcoming.get(i).setPosterUrl(posterUrl);
			
			int dDay = getDDay(upcoming.get(i).getOpenDt());
			upcoming.get(i).setDDay(dDay);
		}
		return upcoming;
	}
	
	public List<MovieInfoDTO> recentlyReleasedKoreanMovies(){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		String date = dtFormat.format(cal.getTime());
		
		List<MovieInfoDTO> movieInfoDto = repo.recentlyReleasedKoreanMovies(date);
		
		for(int i = 0; i < movieInfoDto.size(); ++i) {
			String posterUrl = movieInfoDto.get(i).getPosterUrl().split("\\|")[0];
			movieInfoDto.get(i).setPosterUrl(posterUrl);
			float gradeScore = getMovieGradeAvgByMovieId(movieInfoDto.get(i).getMovieId());
			movieInfoDto.get(i).setGradeAvg(gradeScore);
		}
		
		return movieInfoDto;
	}
	
	public List<MovieInfoDTO> recentlyReleasedForeignMovies(){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		String date = dtFormat.format(cal.getTime());
		
		List<MovieInfoDTO> movieInfoDto = repo.recentlyReleasedForeignMovies(date);
		
		for(int i = 0; i < movieInfoDto.size(); ++i) {
			String posterUrl = movieInfoDto.get(i).getPosterUrl().split("\\|")[0];
			movieInfoDto.get(i).setPosterUrl(posterUrl);
			
			float gradeScore = getMovieGradeAvgByMovieId(movieInfoDto.get(i).getMovieId());
			movieInfoDto.get(i).setGradeAvg(gradeScore);
		}
		
		return movieInfoDto;
	}

	private int getDDay(String openDt) {
		int cnt = 1;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, +1);
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		String date = dtFormat.format(cal.getTime());
		while(!date.equals(openDt)) {
			cal.add(Calendar.DATE, +1);
			date = dtFormat.format(cal.getTime());
			cnt++;
		}
		return cnt;
	}

	private int getDateId() {
		int date = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		String hour = hourFormat.format(cal.getTime());
		if(Integer.parseInt(hour) < 7) {	// 7시 이전이면 8일전
			cal.add(Calendar.DATE, -1);
			String dateStr = dtFormat.format(cal.getTime());
			date = Integer.parseInt(dateStr) * 10;
		}else { // 7시 이후면 7일 전
			String dateStr = dtFormat.format(cal.getTime());
			date = Integer.parseInt(dateStr) * 10;
		}
		return date;
	}
	
	private float getMovieGradeAvgByMovieId(String movieId) {
		float gradeAvg = 0.0f;
		try {
			gradeAvg = repo.getMovieGradeAvgByMovieId(movieId);
		}catch(NullPointerException e) {
			
		}catch(BindingException e) {
			
		}
		
		return gradeAvg;
	}
	
}
