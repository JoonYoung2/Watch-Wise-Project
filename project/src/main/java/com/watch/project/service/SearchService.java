package com.watch.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.searchView.PeopleInfoSearchViewDTO;
import com.watch.project.repository.SearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
	private final SearchRepository repo;
	
	public List<MovieInfoDTO> searchingStep1(String query){
		return repo.searchingStep1(query);
	}

	public List<MovieInfoDTO> searchingStep2(String query) {
		return repo.searchingStep2(query);
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
