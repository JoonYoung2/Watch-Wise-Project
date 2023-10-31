package com.watch.project.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieLikeDTO;
import com.watch.project.dto.PeopleLikeDTO;
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
		dto.setLikeNum(movieInfoDto.getLikeNum());
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
		int peopleId = 0;
		Map<String, String> map = new HashMap<>();
		map.put("peopleNm", peopleNm);
		map.put("movieNm", movieNm);
		try {
			peopleId = repo.getPeopleIdByPeopleNmAndMovieNm(map);
		}catch(BindingException e) {
			
		}
		
		return peopleId;
	}
	/*
	 *영화 좋아요
	 */
	public void movieLikeAdd(String movieId, String userEmail) {
		String id = movieId + userEmail;
		MovieLikeDTO movieLikeDto = new MovieLikeDTO(id, movieId, userEmail);
		repo.movieLikeAdd(movieId);
		repo.movieLikeInsert(movieLikeDto);
	}
	/*
	 *영화 좋아요 토탈 수 
	 */
	public int getLikeNumById(String movieId) {
		return repo.getLikeNumById(movieId);
	}
	/*
	 *영화 좋아요 취소
	 */
	public void movieLikeCancel(String movieId, String userEmail) {
		String id = movieId + userEmail;
		repo.movieLikeCancel(movieId);
		repo.movieLikeDelete(id);
	}
	
	public int getMovieLikeCheck(String movieId, String userEmail) {
		String id = movieId + userEmail;
		try {
			MovieLikeDTO movieLikeDto = repo.getMovieLikeById(id);	
			if(movieLikeDto == null) {
				return 0;			
			}else
				return 1;
		}catch(NullPointerException e) {
			return 0;
		}
	}
}
