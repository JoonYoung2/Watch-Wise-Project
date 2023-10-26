package com.watch.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.movieInfoView.MovieInfoViewDTO;
import com.watch.project.repository.MovieInfoRepository;
import com.watch.project.repository.PeopleInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeopleInfoService {
	private final PeopleInfoRepository repo;
	
	public PeopleInfoDetailDTO getPeopleInfoDetailById(int peopleId) {
		return repo.getPeopleInfoDetailById(peopleId);
	}
	
	public List<MovieInfoDTO> getMovieInfoByMovieIds(String movieIds){
		List<MovieInfoDTO> list = repo.getMovieInfoByMovieIds(movieIds);
		for(int i = 0; i < list.size(); ++i) {
			String posterUrl = list.get(i).getPosterUrl().split("\\|")[0];
			list.get(i).setPosterUrl(posterUrl);
		}
		return list;
	}
	
	public void peopleLikeAdd(int peopleId) {
		repo.peopleLikeAdd(peopleId);
	}
	
	public void peopleLikeCancel(int peopleId) {
		repo.peopleLikeCancel(peopleId);
	}
	
}
