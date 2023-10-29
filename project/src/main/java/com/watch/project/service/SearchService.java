package com.watch.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.searchView.MovieActorsDTO;
import com.watch.project.dto.searchView.MovieInfoSearchViewDTO;
import com.watch.project.dto.searchView.PeopleInfoSearchViewDTO;
import com.watch.project.repository.SearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
	private final SearchRepository repo;
	
	public List<MovieInfoSearchViewDTO> searchingStep1(String query){
		List<MovieInfoDTO> movieInfoList = repo.searchingStep1(query);
		List<MovieInfoSearchViewDTO> movieInfoSearchList = new ArrayList<>();
		
		if(movieInfoList.size() == 0) {
			return movieInfoSearchList;
		}
		
		for(int i = 0; i < movieInfoList.size(); ++i) {
			String movieId = movieInfoList.get(i).getMovieId();
			String movieNm = movieInfoList.get(i).getMovieNm();
			String movieNmEn = movieInfoList.get(i).getMovieNmEn();
			String openDt = movieInfoList.get(i).getOpenDt();
			String nations = movieInfoList.get(i).getNations();
			String genreNm = movieInfoList.get(i).getGenreNm();
			String watchGradeNm = movieInfoList.get(i).getWatchGradeNm();
			log.info("watchGradeNm => {}", watchGradeNm);
			String posterUrl = "nan";
			if(!movieInfoList.get(i).getPosterUrl().split("\\|")[0].equals("nan")) {
				posterUrl = movieInfoList.get(i).getPosterUrl().split("\\|")[0];				
			}
			String showTime = getShowTime(movieInfoList.get(i).getShowTime());

			MovieInfoSearchViewDTO movieInfoSearchDto = new MovieInfoSearchViewDTO(movieId, movieNm, movieNmEn, openDt, nations, genreNm, watchGradeNm, posterUrl, showTime);
			
			String[] actors = movieInfoList.get(i).getActors().split(",");
			String[] casts = movieInfoList.get(i).getCast().split(",");
			if(!actors[0].equals("nan")) {
				for(int j = 0; j < actors.length; ++j) {
					String peopleNm = actors[j];
					String cast = "nan";
					if( j <= casts.length-1 ) {
						cast = casts[j];
					}
					Map<String, String> map = new HashMap<>();
					map.put("peopleNm", peopleNm);
					map.put("movieNm", movieNm);
					log.info("peopleNm => {}", peopleNm);
					log.info("movieNm => {}", movieNm);
					int peopleId = 0;
					try {
						peopleId = repo.getPeopleIdByPeopleNmAndMovieNm(map);					
					}catch(BindingException e) {
						log.error("Error searchingStep1 BindingException => {}", e);
						peopleId = 0;
					}
					MovieActorsDTO movieActorsDto = new MovieActorsDTO(peopleId, peopleNm, cast);
					movieInfoSearchDto.getMovieActorList().add(movieActorsDto);
					if(j == 3) 
						break;
				}				
			}
			movieInfoSearchList.add(movieInfoSearchDto);
		}
		
		return movieInfoSearchList;
	}

	public List<MovieInfoSearchViewDTO> searchingStep2(String query) {
		List<MovieInfoDTO> movieInfoList = repo.searchingStep2(query);
		List<MovieInfoSearchViewDTO> movieInfoSearchList = new ArrayList<>();
		
		if(movieInfoList.size() == 0) {
			return movieInfoSearchList;
		}
		
		for(int i = 0; i < movieInfoList.size(); ++i) {
			String movieId = movieInfoList.get(i).getMovieId();
			String movieNm = movieInfoList.get(i).getMovieNm();
			String movieNmEn = movieInfoList.get(i).getMovieNmEn();
			String openDt = movieInfoList.get(i).getOpenDt();
			String nations = movieInfoList.get(i).getNations();
			String genreNm = movieInfoList.get(i).getGenreNm();
			String watchGradeNm = movieInfoList.get(i).getWatchGradeNm();
			String posterUrl = "nan";
			if(!movieInfoList.get(i).getPosterUrl().split("\\|")[0].equals("nan")) {
				posterUrl = movieInfoList.get(i).getPosterUrl().split("\\|")[0];				
			}
			String showTime = getShowTime(movieInfoList.get(i).getShowTime());

			MovieInfoSearchViewDTO movieInfoSearchDto = new MovieInfoSearchViewDTO(movieId, movieNm, movieNmEn, openDt, nations, genreNm, watchGradeNm, posterUrl, showTime);
			
			String[] actors = movieInfoList.get(i).getActors().split(",");
			String[] casts = movieInfoList.get(i).getCast().split(",");
			if(!actors[0].equals("nan")) {
				for(int j = 0; j < actors.length; ++j) {
					String peopleNm = actors[j];
					String cast = "nan";
					if( j <= casts.length-1 ) {
						cast = casts[j];
					}
					Map<String, String> map = new HashMap<>();
					map.put("peopleNm", peopleNm);
					map.put("movieNm", movieNm);
					log.info("peopleNm => {}", peopleNm);
					log.info("movieNm => {}", movieNm);
					int peopleId = 0;
					try {
						peopleId = repo.getPeopleIdByPeopleNmAndMovieNm(map);					
					}catch(BindingException e) {
						log.error("Error searchingStep1 BindingException => {}", e);
						peopleId = 0;
					}
					MovieActorsDTO movieActorsDto = new MovieActorsDTO(peopleId, peopleNm, cast);
					movieInfoSearchDto.getMovieActorList().add(movieActorsDto);
					if(j == 3) 
						break;
				}				
			}
			movieInfoSearchList.add(movieInfoSearchDto);
		}
		
		return movieInfoSearchList;
	}
	
	private String getShowTime(int time) {
		String showTime = "";
		if(time % 60 == 0) {
			showTime = time / 60 + "시간";
		}else {
			showTime = time / 60 + "시간 " + time % 60 + "분";
		}
		return showTime;
	}

	public List<PeopleInfoSearchViewDTO> searchingStep3(String query) {
		List<PeopleInfoDetailDTO> peopleInfoDetailList = repo.searchingStep3(query);
		List<PeopleInfoSearchViewDTO> peopleInfoSearchViewList = new ArrayList<>();
		if(peopleInfoDetailList.size() == 0) {
			return peopleInfoSearchViewList;
		}
		 
		for(int i = 0; i < peopleInfoDetailList.size(); ++i) {
			int peopleId = peopleInfoDetailList.get(i).getPeopleId();
			String peopleNm = peopleInfoDetailList.get(i).getPeopleNm();
			String peopleNmEn = peopleInfoDetailList.get(i).getPeopleNmEn();
			String sex = peopleInfoDetailList.get(i).getSex();
			int likeNum = peopleInfoDetailList.get(i).getLikeNum();
			
			PeopleInfoSearchViewDTO peopleInfoSearchViewDto = new PeopleInfoSearchViewDTO(peopleId, peopleNm, peopleNmEn, sex, likeNum);
			String movieIds = "";
			String[] movieId = peopleInfoDetailList.get(i).getMovieId().split(",");
			
			for(int j = 0; j < movieId.length; ++j) {
				if(j != movieId.length-1) 
					movieIds += "'" + movieId[j] + "',";
				else
					movieIds += "'" + movieId[j] + "'";
			}
			
			peopleInfoSearchViewDto.setMovieInfoList(repo.getMovieInfoByMovieIds(movieIds));
			
			for(int j = 0; j < peopleInfoSearchViewDto.getMovieInfoList().size(); ++j) {
				String posterUrl = peopleInfoSearchViewDto.getMovieInfoList().get(j).getPosterUrl().split("\\|")[0];
				if(!posterUrl.equals("nan")) {
					peopleInfoSearchViewDto.getMovieInfoList().get(j).setPosterUrl(posterUrl);					
				}
			}
			
			peopleInfoSearchViewList.add(peopleInfoSearchViewDto);
		}
		return peopleInfoSearchViewList;
	}
	
	public List<PeopleInfoSearchViewDTO> searchingStep4(String query) {
		List<PeopleInfoDetailDTO> peopleInfoDetailList = repo.searchingStep4(query);
		List<PeopleInfoSearchViewDTO> peopleInfoSearchViewList = new ArrayList<>();
		if(peopleInfoDetailList.size() == 0) {
			return peopleInfoSearchViewList;
		}
		
		for(int i = 0; i < peopleInfoDetailList.size(); ++i) {
			int peopleId = peopleInfoDetailList.get(i).getPeopleId();
			String peopleNm = peopleInfoDetailList.get(i).getPeopleNm();
			String peopleNmEn = peopleInfoDetailList.get(i).getPeopleNmEn();
			String sex = peopleInfoDetailList.get(i).getSex();
			int likeNum = peopleInfoDetailList.get(i).getLikeNum();
			
			PeopleInfoSearchViewDTO peopleInfoSearchViewDto = new PeopleInfoSearchViewDTO(peopleId, peopleNm, peopleNmEn, sex, likeNum);
			String movieIds = "";
			String[] movieId = peopleInfoDetailList.get(i).getMovieId().split(",");
			
			for(int j = 0; j < movieId.length; ++j) {
				if(j != movieId.length-1) 
					movieIds += "'" + movieId[j] + "',";
				else
					movieIds += "'" + movieId[j] + "'";
			}
			
			peopleInfoSearchViewDto.setMovieInfoList(repo.getMovieInfoByMovieIds(movieIds));
			
			for(int j = 0; j < peopleInfoSearchViewDto.getMovieInfoList().size(); ++j) {
				String posterUrl = peopleInfoSearchViewDto.getMovieInfoList().get(j).getPosterUrl().split("\\|")[0];
				if(!posterUrl.equals("nan")) {
					peopleInfoSearchViewDto.getMovieInfoList().get(j).setPosterUrl(posterUrl);					
				}
			}
			
			peopleInfoSearchViewList.add(peopleInfoSearchViewDto);
		}
		return peopleInfoSearchViewList;
	}
}
