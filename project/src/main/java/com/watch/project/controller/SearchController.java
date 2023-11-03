package com.watch.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.searchView.MovieInfoSearchViewDTO;
import com.watch.project.dto.searchView.PeopleInfoSearchViewDTO;
import com.watch.project.service.PeopleInfoService;
import com.watch.project.service.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SearchController {
	private final SearchService service;
	private final HttpSession session;
	private final PeopleInfoService peopleInfoService;
	
	@GetMapping("search")
	public String searching(@RequestParam("query") String query, Model model) {
		/*
		 * 영화명으로 검색 조건(2가지 케이스)
		 */
		List<MovieInfoSearchViewDTO> movieInfoSearchViewList = service.movieNmSearchingCase(query);
		/*
		 * 회원 추천 영화
		 */
		List<MovieInfoDTO> memberCommendedList = service.getMemberCommendedList();
		
		/*
		 * 검색어 저장
		 */
		service.queryInsert(query);
		
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = service.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = service.popularSearches();
		
		if(movieInfoSearchViewList.size() != 0) {
			return movieNmSearchCaseModelAndView(movieInfoSearchViewList, memberCommendedList, recentSearches, popularSearches, query, model);
		}
		
		/*
		 * 배우명으로 검색 조건(2가지 케이스)
		 */
		List<PeopleInfoSearchViewDTO> peopleInfoSearchViewList = service.ActorNmSearchingCase(query);
		
		if(peopleInfoSearchViewList.size() != 0) {
			int[] likeCheck = setLikeCheck(peopleInfoSearchViewList);
			return actorNmSearchCaseModelAndView(peopleInfoSearchViewList, memberCommendedList, likeCheck, recentSearches, popularSearches, query, model);
		}
		model.addAttribute("query", query);
		return "basic/search_info";
	}
	
	/*
	 * Model And View
	 */
	private String movieNmSearchCaseModelAndView(List<MovieInfoSearchViewDTO> movieInfoSearchViewList, 
			List<MovieInfoDTO> memberCommendedList, String[] recentSearches, 
			String[] popularSearches, String query, Model model) {
		
		model.addAttribute("movieNmSearchingCase", movieInfoSearchViewList);
		model.addAttribute("memberCommend", memberCommendedList);
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("query", query);
		
		return "basic/search_info";
	}
	
	/*
	 * Model And View
	 */
	private String actorNmSearchCaseModelAndView(List<PeopleInfoSearchViewDTO> peopleInfoSearchViewList,
			List<MovieInfoDTO> memberCommendedList, int[] likeCheck, String[] recentSearches, 
			String[] popularSearches, String query, Model model) {
		
		model.addAttribute("actorNmSearchingCase", peopleInfoSearchViewList);
		model.addAttribute("memberCommend", memberCommendedList);
		model.addAttribute("likeCheck", likeCheck);
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("query", query);
		return "basic/search_info";
	}

	private int[] setLikeCheck(List<PeopleInfoSearchViewDTO> peopleInfoSearchViewList) {
		int[] likeCheck = new int[peopleInfoSearchViewList.size()];
		String userEmail = (String)session.getAttribute("userEmail");
		
		for(int i = 0; i < peopleInfoSearchViewList.size(); ++i) {
			int peopleId = peopleInfoSearchViewList.get(i).getPeopleId();
			likeCheck[i] = peopleInfoService.getPeopleLikeCheck(peopleId, userEmail);
		}
		return likeCheck;
	}
}
