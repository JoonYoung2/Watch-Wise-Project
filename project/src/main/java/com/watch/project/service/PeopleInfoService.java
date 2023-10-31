package com.watch.project.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.PeopleLikeDTO;
import com.watch.project.repository.HomeRepository;
import com.watch.project.repository.PeopleInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeopleInfoService {
	private final PeopleInfoRepository repo;
	private final HomeRepository homeRepository;
	private final HttpSession session;
	
	public PeopleInfoDetailDTO getPeopleInfoDetailById(int peopleId) {
		return repo.getPeopleInfoDetailById(peopleId);
	}
	
	public List<MovieInfoDTO> getMovieInfoByMovieIds(String movieIds){
		List<MovieInfoDTO> list = repo.getMovieInfoByMovieIds(movieIds);
		for(int i = 0; i < list.size(); ++i) {
			String posterUrl = list.get(i).getPosterUrl().split("\\|")[0];
			list.get(i).setPosterUrl(posterUrl);
			
			float gradeAvg = getMovieGradeAvgByMovieId(list.get(i).getMovieId());
			list.get(i).setGradeAvg(gradeAvg);
			
			String id = list.get(i).getMovieId() + session.getAttribute("userEmail");
			boolean gradeCheck = getMovieGradeCheckById(id);
			list.get(i).setGradeCheck(gradeCheck);
		}
		return list;
	}
	
	private boolean getMovieGradeCheckById(String id) {
		boolean gradeCheck = false;
		int check = homeRepository.getGradeCheckById(id);
		if(check == 1)
			gradeCheck = true;
		
		return gradeCheck;
	}
	
	private float getMovieGradeAvgByMovieId(String movieId) {
		float gradeAvg = 0.0f;
		try {
			gradeAvg = homeRepository.getMovieGradeAvgByMovieId(movieId);
		}catch(NullPointerException e) {
			
		}catch(BindingException e) {
			
		}
		
		return gradeAvg;
	}

	public void peopleLikeAdd(int peopleId, String userEmail) {
		String id = peopleId + userEmail;
		PeopleLikeDTO peopleLikeDto = new PeopleLikeDTO();
		peopleLikeDto.setId(id);
		peopleLikeDto.setPeopleId(peopleId);
		peopleLikeDto.setUserEmail(userEmail);
		
		repo.peopleLikeAdd(peopleId);
		repo.peopleLikeInsert(peopleLikeDto);
	}
	
	public void peopleLikeCancel(int peopleId, String userEmail) {
		String id = peopleId + userEmail;
		repo.peopleLikeCancel(peopleId);
		repo.peopleLikeDelete(id);
	}
	
	public int getLikeNumById(int peopleId) {
		return repo.getLikeNumById(peopleId);
	}

	public int getPeopleLikeCheck(int peopleId, String userEmail) {
		String id = peopleId + userEmail;
		PeopleLikeDTO peopleLikeDto = repo.getPeopleLikeById(id);
		if(peopleLikeDto == null) {
			return 0;			
		}else
			return 1;
	}
	
}
