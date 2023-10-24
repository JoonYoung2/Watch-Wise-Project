package com.watch.project.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.watch.project.controller.MovieInfoController;
import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.movieInfoView.MovieInfoViewDTO;
import com.watch.project.repository.MovieInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieInfoService {
	private final MovieInfoRepository repo;
	
	public MovieInfoDTO getMovieInfoById(String movieId) {
		return repo.getMovieInfoById(movieId);
	}

	public MovieInfoViewDTO setMMovieInfoViewDto(MovieInfoDTO movieInfoDto) {
		String showTime = "";
		MovieInfoViewDTO dto = new MovieInfoViewDTO();
		dto.setMovieId(movieInfoDto.getMovieId());
		dto.setMovieNm(movieInfoDto.getMovieNm());
		dto.setMovieNmEn(movieInfoDto.getMovieNmEn());
		dto.setPrdtYear(movieInfoDto.getPrdtYear());
		dto.setOpenDt(movieInfoDto.getOpenDt());
		dto.setTypeNm(movieInfoDto.getTypeNm());
		dto.setNations(movieInfoDto.getNations());
		dto.setGenreNm(movieInfoDto.getGenreNm());
		dto.setWatchGradeNm(movieInfoDto.getWatchGradeNm());
		if(movieInfoDto.getShowTime() % 60 == 0) {
			int time = movieInfoDto.getShowTime();
			int hour = time / 60;
			showTime += hour + "시간";
			dto.setShowTime(showTime);
		}else {
			int time = movieInfoDto.getShowTime();
			int hour = time / 60;
			int min = time % 60;
			showTime += hour + "시간 " + min + "분";
			dto.setShowTime(showTime);
		}
		if(movieInfoDto.getPosterUrl() != "nan") {
			String[] posterUrl = movieInfoDto.getPosterUrl().split("\\|");
			dto.setPosterUrl(posterUrl);
		}
		if(movieInfoDto.getActors() != "nan") {
			String[] actors = movieInfoDto.getActors().split(",");
			dto.setActors(actors);
		}
		if(movieInfoDto.getCast() != "nan") {
			String[] cast = movieInfoDto.getCast().split(",");
			dto.setCast(cast);
		}
		return dto;
	}
	
	public int getPeopleIdByPeopleNmAndMovieNm(String peopleNm, String movieNm) {
		Map<String, String> map = new HashMap<>();
		map.put("peopleNm", peopleNm);
		map.put("movieNm", movieNm);
		int peopleId = repo.getPeopleIdByPeopleNmAndMovieNm(map);
		return peopleId;
	}
}
