package com.watch.project.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieLikeDTO;
import com.watch.project.dto.PeopleLikeDTO;
import com.watch.project.dto.movieInfoView.GradeInfoDTO;
import com.watch.project.dto.movieInfoView.MovieInfoViewDTO;
import com.watch.project.dto.movieInfoView.PeopleInfoDTO;
import com.watch.project.repository.HomeRepository;
import com.watch.project.repository.MovieInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieInfoService {
	private final MovieInfoRepository repo;
	private final HomeRepository homeRepository;
	
	public MovieInfoDTO getMovieInfoById(String movieId) {
		return repo.getMovieInfoById(movieId);
	}

	public MovieInfoViewDTO setMMovieInfoViewDto(MovieInfoDTO movieInfoDto) {
		String showTime = "";
		String[] posterUrl = null;
		String[] actors = null;
		String[] cast = null;
		if(movieInfoDto.getShowTime() % 60 == 0) {
			int time = movieInfoDto.getShowTime();
			int hour = time / 60;
			showTime += hour + "시간";
		}else {
			int time = movieInfoDto.getShowTime();
			int hour = time / 60;
			int min = time % 60;
			showTime += hour + "시간 " + min + "분";
		}
		if(movieInfoDto.getPosterUrl() != "nan") {
			posterUrl = movieInfoDto.getPosterUrl().split("\\|");
		}
		if(movieInfoDto.getActors() != "nan") {
			actors = movieInfoDto.getActors().split(",");
		}
		if(movieInfoDto.getCast() != "nan") {
			cast = movieInfoDto.getCast().split(",");
		}
		
		return MovieInfoViewDTO
				.builder()
				.actors(actors)
				.cast(cast)
				.movieInfoDto(movieInfoDto)
				.posterUrl(posterUrl)
				.showTime(showTime)
				.build();
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

	public GradeInfoDTO getGradeInfoDto(String movieId) {
		GradeInfoDTO gradeInfoDto = new GradeInfoDTO();
		try {
			gradeInfoDto = homeRepository.getMovieGradeAvgByMovieId(movieId);
		}catch(NullPointerException e) {
			
		}catch(BindingException e) {
			  
		}
		if(gradeInfoDto == null) {
			gradeInfoDto = new GradeInfoDTO();
			gradeInfoDto.setGradeAvg(0.0f);
			gradeInfoDto.setGradeCnt(0);
		}
		return gradeInfoDto;
	}

	public PeopleInfoDTO setPeopleInfoDto(MovieInfoViewDTO movieInfoViewDto) {
		int peopleLength = 12;
		int castLength = 0;
		String peopleNmStr = "";
		String movieNmStr = "";
		String peopleCastStr = "nan";
		
		// PeopleInfoDTO
		int[] peopleId = null;
		String[] peopleNm = null;
		String[] peopleCast = null;
		int end = 0;
		
		if(movieInfoViewDto.getActors().length >= 12) {		// 출연진이 12명이상인 경우
			castLength = movieInfoViewDto.getCast().length;
			
			peopleId = new int[peopleLength];
			peopleNm = new String[peopleLength];
			peopleCast = new String[castLength];
			for(int i = 0; i < 12; ++i) {
				peopleNmStr = movieInfoViewDto.getActors()[i];
				movieNmStr = movieInfoViewDto.getMovieNm();
				peopleCastStr = "nan";
				if(!movieInfoViewDto.getCast()[0].equals("nan")) {
					if(i <= castLength-1) {
						peopleCastStr = movieInfoViewDto.getCast()[i];						
					}
				}
				peopleId[i] = getPeopleIdByPeopleNmAndMovieNm(peopleNmStr, movieNmStr);
				peopleNm[i] = peopleNmStr;
				if(i <= castLength-1) {
					peopleCast[i] = peopleCastStr;						
				}
			}
		}else { // 12명 미만인 경우
			peopleLength = movieInfoViewDto.getActors().length;
			castLength = movieInfoViewDto.getCast().length;
			
			peopleId = new int[peopleLength];
			peopleNm = new String[peopleLength];
			peopleCast = new String[castLength];
			for(int i = 0; i < peopleLength; ++i) {
				peopleNmStr = movieInfoViewDto.getActors()[i];
				movieNmStr = movieInfoViewDto.getMovieNm();
				peopleCastStr = "nan";
				if(!movieInfoViewDto.getCast()[0].equals("nan")) {
					if(i <= castLength-1) {
						peopleCastStr = movieInfoViewDto.getCast()[i];						
					}
				}
				peopleId[i] = getPeopleIdByPeopleNmAndMovieNm(peopleNmStr, movieNmStr);
				peopleNm[i] = peopleNmStr;
				if(i <= castLength-1) {
					peopleCast[i] = peopleCastStr;						
				}
			}
		}
		end = peopleId.length-1;
		
		return PeopleInfoDTO
				.builder()
				.end(end)
				.peopleCast(peopleCast)
				.peopleId(peopleId)
				.peopleNm(peopleNm)
				.build();
	}
}
