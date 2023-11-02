package com.watch.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.movieInfoView.GradeInfoDTO;
import com.watch.project.dto.movieInfoView.MovieInfoViewDTO;
import com.watch.project.dto.searchView.MovieActorsDTO;
import com.watch.project.dto.searchView.MovieInfoSearchViewDTO;
import com.watch.project.dto.searchView.PeopleInfoSearchViewDTO;
import com.watch.project.repository.HomeRepository;
import com.watch.project.repository.PeopleInfoRepository;
import com.watch.project.repository.SearchRepository;
import com.watch.project.repository.recommended.RecommendedRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
	private final SearchRepository repo;
	private final PeopleInfoRepository peopleInfoRepository;
	private final RecommendedRepository recommendedRepository;
	private final HomeRepository homeRepository;
	private final HttpSession session;
	
	public List<MovieInfoSearchViewDTO> movieNmSearchingCase(String query){
		List<MovieInfoDTO> movieInfoList = repo.searchingStep1(query);
		List<MovieInfoSearchViewDTO> movieInfoSearchList = new ArrayList<>();
		
		if(movieInfoList.size() == 0) {
			movieInfoList = repo.searchingStep2(query);
			if(movieInfoList.size() == 0) {
				return movieInfoSearchList;
			}
		}
		
		for(int i = 0; i < movieInfoList.size(); ++i) {
			String movieId = movieInfoList.get(i).getMovieId();
			String movieNm = movieInfoList.get(i).getMovieNm();
			String id = movieId + (String) session.getAttribute("userEmail");
			GradeInfoDTO gradeInfoDto = getMovieGradeAvgByMovieId(movieId);
			boolean gradeCheck = getMovieGradeCheckById(id);
			String posterUrl = "nan";
			if(!movieInfoList.get(i).getPosterUrl().split("\\|")[0].equals("nan")) {
				posterUrl = movieInfoList.get(i).getPosterUrl().split("\\|")[0];				
			}
			movieInfoList.get(i).setPosterUrl(posterUrl);
			movieInfoList.get(i).setGradeAvg(gradeInfoDto.getGradeAvg());
			movieInfoList.get(i).setGradeCheck(gradeCheck);

			MovieInfoSearchViewDTO movieInfoSearchDto = MovieInfoSearchViewDTO
														.builder()
														.movieInfoDto(movieInfoList.get(i))
														.gradeCheck(gradeCheck)
														.build();
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

					int peopleId = 0;
					try {
						peopleId = repo.getPeopleIdByPeopleNmAndMovieNm(map);					
					}catch(BindingException e) {
						
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

	public List<PeopleInfoSearchViewDTO> ActorNmSearchingCase(String query) {
		List<PeopleInfoDetailDTO> peopleInfoDetailList = repo.searchingStep3(query);
		List<PeopleInfoSearchViewDTO> peopleInfoSearchViewList = new ArrayList<>();
		if(peopleInfoDetailList.size() == 0) {
			peopleInfoDetailList = repo.searchingStep4(query);
			if(peopleInfoDetailList.size() == 0) {
				return peopleInfoSearchViewList;
			}
		}
		 
		for(int i = 0; i < peopleInfoDetailList.size(); ++i) {
			PeopleInfoSearchViewDTO peopleInfoSearchViewDto = 
					PeopleInfoSearchViewDTO
					.builder()
					.peopleInfoDetailDto(peopleInfoDetailList.get(i))
					.build();
			String movieIds = "";
			String[] movie = peopleInfoDetailList.get(i).getMovieId().split(",");
			
			for(int j = 0; j < movie.length; ++j) {
				if(j != movie.length-1) 
					movieIds += "'" + movie[j] + "',";
				else
					movieIds += "'" + movie[j] + "'";
			}
			
			peopleInfoSearchViewDto.setMovieInfoList(repo.getMovieInfoByMovieIds(movieIds));
			
			for(int j = 0; j < peopleInfoSearchViewDto.getMovieInfoList().size(); ++j) {
				String posterUrl = peopleInfoSearchViewDto.getMovieInfoList().get(j).getPosterUrl().split("\\|")[0];
				String movieId = peopleInfoSearchViewDto.getMovieInfoList().get(j).getMovieId();
				String id = movieId + (String) session.getAttribute("userEmail");
				GradeInfoDTO gradeInfoDto = getMovieGradeAvgByMovieId(movieId);
				boolean gradeCheck = getMovieGradeCheckById(id);
				if(!posterUrl.equals("nan")) {
					peopleInfoSearchViewDto.getMovieInfoList().get(j).setPosterUrl(posterUrl);					
				}
				peopleInfoSearchViewDto.getMovieInfoList().get(j).setGradeAvg(gradeInfoDto.getGradeAvg());
				peopleInfoSearchViewDto.getMovieInfoList().get(j).setGradeCheck(gradeCheck);
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
			
			PeopleInfoSearchViewDTO peopleInfoSearchViewDto = 
					PeopleInfoSearchViewDTO.builder()
					.peopleInfoDetailDto(peopleInfoDetailList.get(i))
					.build();
			String movieIds = "";
			String[] movie = peopleInfoDetailList.get(i).getMovieId().split(",");
			
			for(int j = 0; j < movie.length; ++j) {
				if(j != movie.length-1) 
					movieIds += "'" + movie[j] + "',";
				else
					movieIds += "'" + movie[j] + "'";
			}
			
			peopleInfoSearchViewDto.setMovieInfoList(repo.getMovieInfoByMovieIds(movieIds));
			
			for(int j = 0; j < peopleInfoSearchViewDto.getMovieInfoList().size(); ++j) {
				String posterUrl = peopleInfoSearchViewDto.getMovieInfoList().get(j).getPosterUrl().split("\\|")[0];
				String movieId = peopleInfoSearchViewDto.getMovieInfoList().get(j).getMovieId();
				String id = movieId + (String) session.getAttribute("userEmail");
				GradeInfoDTO gradeInfoDto = getMovieGradeAvgByMovieId(movieId);
				boolean gradeCheck = getMovieGradeCheckById(id);
				if(!posterUrl.equals("nan")) {
					peopleInfoSearchViewDto.getMovieInfoList().get(j).setPosterUrl(posterUrl);					
				}
				peopleInfoSearchViewDto.getMovieInfoList().get(j).setGradeAvg(gradeInfoDto.getGradeAvg());
				peopleInfoSearchViewDto.getMovieInfoList().get(j).setGradeCheck(gradeCheck);
			}
			
			peopleInfoSearchViewList.add(peopleInfoSearchViewDto);
		}
		return peopleInfoSearchViewList;
	}

	public List<MovieInfoDTO> getMemberCommendedList() {
		List<MovieInfoDTO> movieInfoList = new ArrayList<>();
		String userEmail = (String) session.getAttribute("userEmail");
		String movieIds = "";
		String genreNm = "";
		if(userEmail != null) {
			String[] movie = recommendedRepository.getMovieIdByUserEmail(userEmail);
			if(movie.length > 0) {
				for(int i = 0; i < movie.length; ++i) {
					if(i != movie.length-1) 
						movieIds += "'" + movie[i] + "',";
					else
						movieIds += "'" + movie[i] + "'";
				}
				try {
					genreNm = recommendedRepository.getGenreNmByMovieIds(movieIds);					
				}catch(NullPointerException e) {
					
				}catch(BindingException e) {
					
				}
				if(!genreNm.equals("")) {
					movieInfoList = recommendedRepository.getMovieInfoByGenreNm(genreNm);
					for(int i = 0; i < movieInfoList.size(); ++i) {
						String posterUrl = movieInfoList.get(i).getPosterUrl().split("\\|")[0];
						String movieId = movieInfoList.get(i).getMovieId();
						String id = movieId + userEmail;
						GradeInfoDTO gradeInfoDto = getMovieGradeAvgByMovieId(movieId);
						boolean gradeCheck = getMovieGradeCheckById(id);
						
						movieInfoList.get(i).setPosterUrl(posterUrl);
						movieInfoList.get(i).setGradeAvg(gradeInfoDto.getGradeAvg());
						movieInfoList.get(i).setGradeCheck(gradeCheck);
					}
				}
			}
		}
		return movieInfoList;
	}
	
	private GradeInfoDTO getMovieGradeAvgByMovieId(String movieId) {
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
	
	private boolean getMovieGradeCheckById(String id) {
		boolean gradeCheck = false;
		int check = homeRepository.getGradeCheckById(id);
		if(check == 1)
			gradeCheck = true;
		
		return gradeCheck;
	}
}
