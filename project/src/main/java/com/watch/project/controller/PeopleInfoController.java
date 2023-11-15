package com.watch.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.movieInfoView.MovieInfoViewDTO;
import com.watch.project.dto.movieInfoView.PeopleInfoDTO;
import com.watch.project.service.MovieInfoService;
import com.watch.project.service.PeopleInfoService;
import com.watch.project.service.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PeopleInfoController {
	private final PeopleInfoService service;
	private final SearchService searchService;
	
	@GetMapping("peopleInfo") 
	public String peopleInfoView(@RequestParam int peopleId, Model model, HttpSession session) {
		log.info("peopleId => {}", peopleId);
		/*
		 * 배우 정보
		 */
		PeopleInfoDetailDTO peopleInfoDetailDto = service.getPeopleInfoDetailById(peopleId);
				
		/*
		 * 영화 정보 리스트
		 */
		List<MovieInfoDTO> movieInfoList = service.getMovieInfoByMovieIds(getMovieIds(setMovieIdArray(peopleInfoDetailDto)));
		
		/*
		 * 유저 좋아요 체크여부
		 */
		int likeCheck = service.getPeopleLikeCheck(peopleId, (String)session.getAttribute("userEmail"));
		
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = searchService.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = searchService.popularSearches();
		
		/*
		 * 최근 검색어 + 인기 검색어 크기
		 */
		int recentSearchesSize = -1;
		try {
			recentSearchesSize = recentSearches.length;			
		}catch(NullPointerException e) {
			
		}
		
		model.addAttribute("peopleInfo", peopleInfoDetailDto);
		model.addAttribute("movieInfo", movieInfoList);
		model.addAttribute("likeCheck", likeCheck);
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("recentSearchesSize", recentSearchesSize);
		
		return "basic/people_info";
	}
	
	private String[] setMovieIdArray(PeopleInfoDetailDTO peopleInfoDetailDto) {
		return peopleInfoDetailDto.getMovieId().split(",");
	}

	private String getMovieIds(String[] movieId) {
		String movieIds = "";
		for(int i = 0; i < movieId.length; ++i) {
			if(i != movieId.length-1) {
				movieIds += "'" + movieId[i] + "',";
			}else {
				movieIds += "'" + movieId[i] + "'";
			}
		}
		return movieIds;
	}
	
}

