package com.watch.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.movieInfoView.MovieInfoViewDTO;
import com.watch.project.dto.movieInfoView.PeopleInfoDTO;
import com.watch.project.service.MovieInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MovieInfoController {
	private final MovieInfoService service;
	
	@GetMapping("movieInfo")
	public String movieInfo(@RequestParam String movieId, Model model) {
		MovieInfoDTO movieInfoDto = service.getMovieInfoById(movieId);
		MovieInfoViewDTO movieInfoViewDto = service.setMMovieInfoViewDto(movieInfoDto);
		
		PeopleInfoDTO peopleInfoDto = new PeopleInfoDTO();
		
		if(!movieInfoViewDto.getActors()[0].equals("nan")) {	// 출연진이 있는 경우
			if(movieInfoViewDto.getActors().length >= 12) {		// 출연진이 12명이상인 경우
				int castSize = movieInfoViewDto.getCast().length;
				peopleInfoDto.setPeopleId(new int[12]);
				peopleInfoDto.setPeopleNm(new String[12]);
				peopleInfoDto.setPeopleCast(new String[castSize]);
				for(int i = 0; i < 12; ++i) {
					String peopleNm = movieInfoViewDto.getActors()[i];
					String movieNm = movieInfoViewDto.getMovieNm();
					String peopleCast = "nan";
					if(!movieInfoViewDto.getCast()[0].equals("nan")) {
						if(i <= castSize-1) {
							peopleCast = movieInfoViewDto.getCast()[i];						
						}
					}
					peopleInfoDto.getPeopleId()[i] = service.getPeopleIdByPeopleNmAndMovieNm(peopleNm, movieNm);
					peopleInfoDto.getPeopleNm()[i] = peopleNm;
					if(i <= castSize-1) {
						peopleInfoDto.getPeopleCast()[i] = peopleCast;						
					}
				}
			}else {
				int peopleSize = movieInfoViewDto.getActors().length;
				int castSize = movieInfoViewDto.getCast().length;
				peopleInfoDto.setPeopleId(new int[peopleSize]);
				peopleInfoDto.setPeopleNm(new String[peopleSize]);
				peopleInfoDto.setPeopleCast(new String[castSize]);
				for(int i = 0; i < peopleSize; ++i) {
					String peopleNm = movieInfoViewDto.getActors()[i];
					String movieNm = movieInfoViewDto.getMovieNm();
					String peopleCast = "nan";
					if(!movieInfoViewDto.getCast()[0].equals("nan")) {
						if(i <= castSize-1) {
							peopleCast = movieInfoViewDto.getCast()[i];						
						}
					}
					peopleInfoDto.getPeopleId()[i] = service.getPeopleIdByPeopleNmAndMovieNm(peopleNm, movieNm);
					peopleInfoDto.getPeopleNm()[i] = peopleNm;
					if(i <= castSize-1) {
						peopleInfoDto.getPeopleCast()[i] = peopleCast;						
					}
				}
			}
			peopleInfoDto.setEnd(peopleInfoDto.getPeopleId().length-1);
			model.addAttribute("peopleInfo", peopleInfoDto);
		}
		model.addAttribute("movieInfo", movieInfoViewDto);
		return "basic/movie_info";
	}
}

