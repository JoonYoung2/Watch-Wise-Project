package com.watch.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.watch.project.dto.LiveSearchDTO;
import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieTopInfoDTO;
import com.watch.project.dto.admin.chart.ActorChartDTO;
import com.watch.project.dto.admin.chart.MovieChartDTO;
import com.watch.project.service.HomeService;
import com.watch.project.service.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	private final HomeService service;
	private final SearchService searchService;
	private final com.watch.project.service.admin.HomeService adminHomeService;

	@GetMapping("/")
	public String home(Model model) {
		
		/*
		 * 인기 영화
		 */
		List<MovieChartDTO> movieChartList = adminHomeService.getPopularMovieList();
		
		/*
		 * 인기 배우
		 */
		List<ActorChartDTO> actorChartList = adminHomeService.getPopularActorList();
		
		List<MovieTopInfoDTO> topInfoList = service.getMovieTopInfo();
				
		/*
		 * Top 10 Daily~Weekly 4개
		 */
		 Map<String, List<MovieInfoDTO>> movieInfoMap = service.setMovieInfoMap(topInfoList);
		
		/*
		 * 곧 개봉하는 영화
		 */
		List<MovieInfoDTO> upcomingMovies = service.upcomingMovies();
		
		/*
		 * 최근 개봉한 한국 영화
		 */
		List<MovieInfoDTO> recentlyReleasedKoreanMovies = service.recentlyReleasedKoreanMovies();
		
		/*
		 * 최근 개봉한 외국 영화
		 */
		List<MovieInfoDTO> recentlyReleasedForeignMovies = service.recentlyReleasedForeignMovies();
		
		movieInfoMap.put("upcoming", upcomingMovies);
		movieInfoMap.put("recentlyKorea", recentlyReleasedKoreanMovies);
		movieInfoMap.put("recentlyForeign", recentlyReleasedForeignMovies);
		
		searchService.searchModel(model);
		model.addAttribute("movieChart", movieChartList);
		model.addAttribute("actorChart", actorChartList);
		model.addAttribute("movieInfoMap", movieInfoMap);
		
		return "basic/home"; 
	}
}
