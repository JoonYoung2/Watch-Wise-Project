package com.watch.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieTopInfoDTO;
import com.watch.project.service.HomeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	private final HomeService service;

	@GetMapping("/")
	public String home(Model model) {
		List<MovieTopInfoDTO> topInfoList = service.getMovieTopInfo();
		log.info("topInfoList -> {}", topInfoList);
		log.info("topInfoListSize -> {}", topInfoList.size());
		Map<String, List<MovieInfoDTO>> movieInfoMap = new HashMap<>();
		for(int i = 0; i < topInfoList.size(); ++i) {
			List<MovieInfoDTO> movieInfoList = new ArrayList<>();
			movieInfoList = service.getMovieInfoListByIds(topInfoList.get(i).getMovieIds());
			for(int j = 0; j < movieInfoList.size(); ++j) {
				String movieUrl = movieInfoList.get(j).getPosterUrl().split("\\|")[0];
				movieInfoList.get(j).setPosterUrl(movieUrl);
			}
			if(i == 0) {
				movieInfoMap.put("daily", movieInfoList);
			}else if(i == 1) {
				movieInfoMap.put("weekly0", movieInfoList);
			}else if(i == 2) {
				movieInfoMap.put("weekly1", movieInfoList);
			}else if(i == 3) {
				movieInfoMap.put("weekly2", movieInfoList);
			}
		}
		log.info("movieInfoMap - key(daily) {}", movieInfoMap.get("daily").get(0));
		
		// 곧 개봉하는 영화
		List<MovieInfoDTO> upcomingMovies = service.upcomingMovies();
		movieInfoMap.put("upcoming", upcomingMovies);
		
		List<MovieInfoDTO> recentlyReleasedKoreanMovies = service.recentlyReleasedKoreanMovies();
		movieInfoMap.put("recentlyKorea", recentlyReleasedKoreanMovies);
		
		model.addAttribute("movieInfoMap", movieInfoMap);
		return "basic/home"; 
	}
	@GetMapping("/test")
	public String test() {
		return "home";
	}
}
