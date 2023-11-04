package com.watch.project.controller.ajax;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watch.project.dto.relatedSearch.RelatedSearchResponseDTO;
import com.watch.project.service.MovieInfoService;
import com.watch.project.service.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchAjaxController {
	private final SearchService service;
	
	@GetMapping("searchAllMove")
	public void searchAllMove() {
		service.recentSearchesAllRemoveUpdateByUserEmail();
	}
	
	@GetMapping("relatedSearch")
	public List<RelatedSearchResponseDTO> getRelatedSearchByContentAndUserEmail(@RequestParam("query") String content){
		return service.getRelatedSearchByContentAndUserEmail(content);
	}
}
