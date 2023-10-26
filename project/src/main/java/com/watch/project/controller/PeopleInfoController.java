package com.watch.project.controller;

import java.util.List;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PeopleInfoController {
	private final PeopleInfoService service;
	
	@GetMapping("peopleInfo")
	public String peopleInfoView(@RequestParam int peopleId, Model model) {
		PeopleInfoDetailDTO peopleInfoDetailDto = service.getPeopleInfoDetailById(peopleId);
		String[] movieId = peopleInfoDetailDto.getMovieId().split(",");
		String movieIds = "";
		for(int i = 0; i < movieId.length; ++i) {
			if(i != movieId.length-1) {
				movieIds += "'" + movieId[i] + "',";
			}else {
				movieIds += "'" + movieId[i] + "'";
			}
		}
		log.info("movieIds => {}", movieIds);
		List<MovieInfoDTO> movieInfoList = service.getMovieInfoByMovieIds(movieIds);
		log.info("movieInfoListSize => {}", movieInfoList.size());
		
		model.addAttribute("peopleInfo", peopleInfoDetailDto);
		model.addAttribute("movieInfo", movieInfoList);
		return "basic/people_info";
	}
	
}

