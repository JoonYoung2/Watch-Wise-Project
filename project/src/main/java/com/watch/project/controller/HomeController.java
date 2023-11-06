package com.watch.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieTopInfoDTO;
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

	@GetMapping("/")
	public String home(Model model) {
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
		
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 최근 검색어 크기
		 */
		int recentSearchesSize = -1;
		
		try {
			recentSearchesSize = recentSearches.length;			
		}catch(NullPointerException e) {
			
		}
		
		movieInfoMap.put("upcoming", upcomingMovies);
		movieInfoMap.put("recentlyKorea", recentlyReleasedKoreanMovies);
		movieInfoMap.put("recentlyForeign", recentlyReleasedForeignMovies);
		model.addAttribute("movieInfoMap", movieInfoMap);
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("recentSearchesSize", recentSearchesSize);
		
		return "basic/home"; 
	}
}
