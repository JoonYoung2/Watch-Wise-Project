package com.watch.project.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.PeopleLikeDTO;
import com.watch.project.dto.movieInfoView.GradeInfoDTO;
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
	
	/*
	 * 배우 정보
	 */
	public PeopleInfoDetailDTO getPeopleInfoDetailById(int peopleId) {
		return repo.getPeopleInfoDetailById(peopleId);
	}
	
	/*
	 * 영화 정보 리스트
	 */
	public List<MovieInfoDTO> getMovieInfoByMovieIds(String movieIds){
		List<MovieInfoDTO> list = setMovieInfoDtoList(movieIds);
		
		return list;
	}
	
	/*
	 * 배우 좋아요 추가
	 */
	public void peopleLikeAdd(int peopleId, String userEmail) {
		String id = peopleId + userEmail;
		PeopleLikeDTO peopleLikeDto = 
				PeopleLikeDTO
				.builder()
				.id(id)
				.peopleId(peopleId)
				.userEmail(userEmail)
				.build();
		
		repo.peopleLikeAdd(peopleId);
		repo.peopleLikeInsert(peopleLikeDto);
	}
	
	/*
	 * 배우 좋아요 삭제
	 */
	public void peopleLikeCancel(int peopleId, String userEmail) {
		String id = peopleId + userEmail;
		repo.peopleLikeCancel(peopleId);
		repo.peopleLikeDelete(id);
	}
	
	/*
	 * 총 좋아요 수
	 */
	public int getLikeNumById(int peopleId) {
		return repo.getLikeNumById(peopleId);
	}
	
	/*
	 * 사용자 좋아요 체크여부
	 */
	public int getPeopleLikeCheck(int peopleId, String userEmail) {
		String id = peopleId + userEmail;
		PeopleLikeDTO peopleLikeDto = repo.getPeopleLikeById(id);
		if(peopleLikeDto == null)
			return 0;			
		else
			return 1;
	}
	
	private List<MovieInfoDTO> setMovieInfoDtoList(String movieIds) {
		List<MovieInfoDTO> list = repo.getMovieInfoByMovieIds(movieIds);
		return setGradeInfoAndPosterUrl(list);
	}

	private List<MovieInfoDTO> setGradeInfoAndPosterUrl(List<MovieInfoDTO> list) {
		for(int i = 0; i < list.size(); ++i) {
			GradeInfoDTO gradeInfoDto = setMovieGradeAvgByMovieId(list.get(i).getMovieId());
			String posterUrl = list.get(i).getPosterUrl().split("\\|")[0];
			float gradeAvg = gradeInfoDto.getGradeAvg();
			String id = list.get(i).getMovieId() + session.getAttribute("userEmail");
			boolean gradeCheck = setMovieGradeCheckById(id);
			
			list.get(i).setPosterUrl(posterUrl);
			list.get(i).setGradeAvg(gradeAvg);			
			list.get(i).setGradeCheck(gradeCheck);
		}
		return list;
	}

	private boolean setMovieGradeCheckById(String id) {
		boolean gradeCheck = false;
		int check = homeRepository.getGradeCheckById(id);
		if(check == 1)
			gradeCheck = true;
		
		return gradeCheck;
	}
	
	private GradeInfoDTO setMovieGradeAvgByMovieId(String movieId) {
		GradeInfoDTO gradeInfoDto = GradeInfoDTO.builder().build();
		try {
			gradeInfoDto = homeRepository.getMovieGradeAvgByMovieId(movieId);
		}catch(NullPointerException e) {
			
		}catch(BindingException e) {
			  
		}
		if(gradeInfoDto == null) {
			gradeInfoDto = GradeInfoDTO
					.builder()
					.gradeAvg(0.0f)
					.gradeCnt(0)
					.build();
		}
		
		return gradeInfoDto;
	}

	
	
}
