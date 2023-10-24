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
		
		//여기서부터 int가 안구해짐
		
//		PeopleInfoDTO peopleInfoDto = new PeopleInfoDTO();
//		if(!movieInfoViewDto.getActors()[0].equals("nan")) {
//			if(movieInfoViewDto.getActors().length >= 12) {
//				peopleInfoDto.setPeopleId(new int[12]);
//				peopleInfoDto.setPeopleNm(new String[12]);
//				peopleInfoDto.setPeopleCast(new String[12]);
//				for(int i = 0; i < 12; ++i) {
//					String peopleNm = movieInfoViewDto.getActors()[i];
//					String movieNm = movieInfoViewDto.getMovieNm();
//					String peopleCast = "nan";
//					if(!movieInfoViewDto.getCast()[0].equals("nan")) {
//						peopleCast = movieInfoViewDto.getCast()[i];
//					}
//					peopleInfoDto.getPeopleId()[i] = service.getPeopleIdByPeopleNmAndMovieNm(peopleNm, movieNm);
//					peopleInfoDto.getPeopleNm()[i] = peopleNm;
//					peopleInfoDto.getPeopleCast()[i] = peopleCast;
//				}
//			}else {
//				int peopleSize = movieInfoViewDto.getActors().length;
//				peopleInfoDto.setPeopleId(new int[peopleSize]);
//				peopleInfoDto.setPeopleNm(new String[peopleSize]);
//				peopleInfoDto.setPeopleCast(new String[peopleSize]);
//				for(int i = 0; i < peopleSize; ++i) {
//					String peopleNm = movieInfoViewDto.getActors()[i];
//					String movieNm = movieInfoViewDto.getMovieNm();
//					String peopleCast = "nan";
//					if(!movieInfoViewDto.getCast()[0].equals("nan")) {
//						peopleCast = movieInfoViewDto.getCast()[i];
//					}
//					peopleInfoDto.getPeopleId()[i] = service.getPeopleIdByPeopleNmAndMovieNm(peopleNm, movieNm);
//					peopleInfoDto.getPeopleNm()[i] = peopleNm;
//					peopleInfoDto.getPeopleCast()[i] = peopleCast;
//				}
//			}
//			model.addAttribute("peopleInfo", peopleInfoDto);
//		}
		model.addAttribute("movieInfo", movieInfoViewDto);
		return "basic/movie_info";
	}
}

