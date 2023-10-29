package com.watch.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.searchView.MovieInfoSearchViewDTO;
import com.watch.project.dto.searchView.PeopleInfoSearchViewDTO;
import com.watch.project.service.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SearchController {
	private final SearchService service;
	
	@GetMapping("search")
	public String searching(@RequestParam("query") String query, Model model) {
		List<MovieInfoSearchViewDTO> searchList1 = service.searchingStep1(query);
		List<MovieInfoSearchViewDTO> searchList2 = new ArrayList<>();
		List<PeopleInfoSearchViewDTO> searchList3 = new ArrayList<>();
		List<PeopleInfoSearchViewDTO> searchList4 = new ArrayList<>();
		log.info("searchingStep1 Size => {}", searchList1.size());
		model.addAttribute("query", query);
		if(searchList1.size() != 0) {
			model.addAttribute("searchList1", searchList1);
			return "basic/search_info";
		}else {
			searchList2 = service.searchingStep2(query);
			log.info("searchingStep2 Size => {}", searchList2.size());
		}
		
		if(searchList2.size() != 0) {
			model.addAttribute("searchList2", searchList2);
			return "basic/search_info";
		}else {
			searchList3 = service.searchingStep3(query);
			log.info("searchingStep3 Size => {}", searchList3.size());
		}
		
		if(searchList3.size() != 0) {
			model.addAttribute("searchList3", searchList3);
			return "basic/search_info";
		}else {
			searchList4 = service.searchingStep4(query);
			log.info("searchingStep4 Size => {}", searchList4.size());
		}
		
		if(searchList4.size() != 0) {
			model.addAttribute("searchList4", searchList4);
			return "basic/search_info";
		}
		
		return "basic/search_info";
	}
}
