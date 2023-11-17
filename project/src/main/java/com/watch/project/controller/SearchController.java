package com.watch.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.ContentSearchingDTO;
import com.watch.project.dto.LiveSearchDTO;
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
	
	@GetMapping("/search")
	public String searching(@RequestParam("query") String query, Model model) {
		List<MovieInfoSearchViewDTO> movieInfoSearchViewList = null;
		List<PeopleInfoSearchViewDTO> peopleInfoSearchViewList = null;
		
		/*
		 * 영화명으로 검색 조건(2가지 케이스)
		 */
		try {
			movieInfoSearchViewList = new ArrayList<>();
			movieInfoSearchViewList = service.movieNmSearchingCase(query);			
		}catch(TooManyResultsException e) {
			model.addAttribute("nothing", "다른 검색어를 입력해주세요.");
			return "basic/search_info";
		}catch(MyBatisSystemException e) {
			model.addAttribute("nothing", "다른 검색어를 입력해주세요.");
			return "basic/search_info";
		}catch(StringIndexOutOfBoundsException e) {
			model.addAttribute("nothing", "다른 검색어를 입력해주세요.");
			return "basic/search_info";
		}
		
		/*
		 * 배우명으로 검색 조건(2가지 케이스)
		 */
		try {
			peopleInfoSearchViewList = new ArrayList<>();
			peopleInfoSearchViewList = service.ActorNmSearchingCase(query);			
		}catch(TooManyResultsException e){
			model.addAttribute("nothing", "다른 검색어를 입력해주세요.");
			return "basic/search_info";
		}catch(MyBatisSystemException e) {
			model.addAttribute("nothing", "다른 검색어를 입력해주세요.");
			return "basic/search_info";
		}catch(StringIndexOutOfBoundsException e) {
			model.addAttribute("nothing", "다른 검색어를 입력해주세요.");
			return "basic/search_info";
		}
		
		/*
		 * 회원 추천 영화
		 */
		List<MovieInfoDTO> memberCommendedList = service.getMemberCommendedList();
		
		/*
		 * DB때문에 ''로 변환했던 거 다시 '하나로 돌리기 위해
		 */
		query = query.replaceAll("''", "'");
		
		/*
		 * 검색 결과가 있을 경우에만 저장
		 */
		if(movieInfoSearchViewList.size() != 0 || peopleInfoSearchViewList.size() != 0) {
			/*
			 * 검색어 저장
			 */
			service.queryInsert(query);
		}
		
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = service.recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = service.popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = service.getLiveSearchList();
		
		/*
		 * 최근 검색어 + 인기 검색어 크기
		 */
		int recentSearchesSize = -1;
		
		try {
			recentSearchesSize = recentSearches.length;			
		}catch(NullPointerException e) {
			
		}
		
		/*
		 * 사람만 서칭됐을 때
		 */
		if(peopleInfoSearchViewList.size() != 0 && movieInfoSearchViewList.size() == 0) {
			int[] likeCheck = setLikeCheck(peopleInfoSearchViewList);
			return actorNmSearchCaseModelAndView(peopleInfoSearchViewList, memberCommendedList, liveSearchList, likeCheck, recentSearches, popularSearches, recentSearchesSize, query, model);
		}
		
		/*
		 * 영화만 서칭됐을 때
		 */
		if(movieInfoSearchViewList.size() != 0 && peopleInfoSearchViewList.size() == 0) {
			return movieNmSearchCaseModelAndView(movieInfoSearchViewList, memberCommendedList, liveSearchList, recentSearches, popularSearches, recentSearchesSize, query, model);
		}
		
		/*
		 * 둘 다 서칭됐을 때
		 */
		if(peopleInfoSearchViewList.size() != 0 && movieInfoSearchViewList.size() != 0) {
			int[] likeCheck = setLikeCheck(peopleInfoSearchViewList);
			return movieNmSearchAndActorNmSearchCaseModelAndView(peopleInfoSearchViewList, movieInfoSearchViewList, memberCommendedList, liveSearchList, likeCheck,recentSearches, popularSearches, recentSearchesSize, query, model);
		}
		query = query.replaceAll("''", "'");
		model.addAttribute("memberCommend", memberCommendedList);
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("recentSearchesSize", recentSearchesSize);
		model.addAttribute("query", query);
		model.addAttribute("nothing", "검색결과가 없습니다.");
		return "basic/search_info";
	}
	
	/*
	 * 유저가 검색했던 검색어
	 */
	@GetMapping("/searchHistoryView")
	public String searchHistoryView(Model model) {
		List<ContentSearchingDTO> contentSearchList = service.getContentSearchByUserEmail();
		String[] recentSearches = service.recentSearchesByUserEmail();
		String[] popularSearches = service.popularSearches();
		
		if(contentSearchList.size() == 0) {
			String msg = "검색기록이 존재하지 않습니다.";
			String location = "/memberInfo";
			String view = alertAndView(msg, location, model);
			return view;
		}
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("contentSearch", contentSearchList);
		return "member/member_info/search_history_list";
	}
	
	/*
	 * 사용자의 최근 검색어 삭제
	 */
	@GetMapping("/deleteAllSearchHistory")
	public String deleteAllSearchHistory(Model model) {
		service.deleteAllSearchHistory();
		String msg = "삭제가 완료되었습니다.";
		String location = "/memberInfo";
		model.addAttribute("msg", msg);
		model.addAttribute("location", location);
		return "alert_and_view";
	}
	
	/*
	 * 사용자의 검색어 목록 삭제
	 */
	@GetMapping("/deleteSearchHistory")
	public String deleteAllSearchHistory(@RequestParam("ids") String ids) {
		service.deleteSearchHistory(ids);
		return "redirect:searchHistoryView";
	}
	
	private String alertAndView(String msg, String location, Model model) {
		String view = "alert_and_view";
		model.addAttribute("msg", msg);
		model.addAttribute("location", location);
		return view;
	}

	/*
	 * 영화명 검색 결과
	 * Model And View
	 */
	private String movieNmSearchCaseModelAndView(List<MovieInfoSearchViewDTO> movieInfoSearchViewList, 
			List<MovieInfoDTO> memberCommendedList, List<LiveSearchDTO> liveSearchList, String[] recentSearches, 
			String[] popularSearches, int recentSearchesSize, String query, Model model) {
		
		model.addAttribute("movieNmSearchingCase", movieInfoSearchViewList);
		model.addAttribute("memberCommend", memberCommendedList);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("recentSearchesSize", recentSearchesSize);
		model.addAttribute("query", query);
		
		return "basic/search_info";
	}
	
	/*
	 * 배우명 검색 결과
	 * Model And View
	 */
	private String actorNmSearchCaseModelAndView(List<PeopleInfoSearchViewDTO> peopleInfoSearchViewList,
			List<MovieInfoDTO> memberCommendedList, List<LiveSearchDTO> liveSearchList, int[] likeCheck, String[] recentSearches, 
			String[] popularSearches, int recentSearchesSize, String query, Model model) {
		
		model.addAttribute("actorNmSearchingCase", peopleInfoSearchViewList);
		model.addAttribute("memberCommend", memberCommendedList);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("likeCheck", likeCheck);
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("recentSearchesSize", recentSearchesSize);
		model.addAttribute("query", query);
		return "basic/search_info";
	}
	
	/*
	 * 영화명과 배우명 둘다 검색결과
	 * Model And View
	 */
	private String movieNmSearchAndActorNmSearchCaseModelAndView(List<PeopleInfoSearchViewDTO> peopleInfoSearchViewList,
			List<MovieInfoSearchViewDTO> movieInfoSearchViewList, List<MovieInfoDTO> memberCommendedList, List<LiveSearchDTO> liveSearchList,
			int[] likeCheck, String[] recentSearches, String[] popularSearches, int recentSearchesSize, String query,
			Model model) {
		
		model.addAttribute("memberCommendCheck", 0);
		model.addAttribute("actorNmSearchingCase", peopleInfoSearchViewList);
		model.addAttribute("movieNmSearchingCase", movieInfoSearchViewList);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("memberCommend", memberCommendedList);
		model.addAttribute("likeCheck", likeCheck);
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("recentSearchesSize", recentSearchesSize);
		model.addAttribute("query", query);
		return "basic/search_info";
	}
	
	/*
	 * 인물 좋아요 체크
	 * 유저가 좋아요 눌렀으면 1
	 * 아니면 0
	 */
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
