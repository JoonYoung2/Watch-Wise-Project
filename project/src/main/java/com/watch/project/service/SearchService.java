package com.watch.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Service;

import com.watch.project.dto.ContentSearchingDTO;
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
		
		return getMovieInfoSearchList(movieInfoList, movieInfoSearchList);
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
	
	public String[] recentSearchesByUserEmail() {
		String userEmail = (String)session.getAttribute("userEmail");
		if(userEmail == null) {
			return null;
		}
		return repo.recentSearchesByUserEmail((String)session.getAttribute("userEmail"));
	}
	
	public String[] popularSearches() {
		return repo.popularSearches(getSixMonthAgoDate());
	}
	
	public void queryInsert(String query) {
		Map<String, String> searchMap = new HashMap<>();
		String userEmail = (String)session.getAttribute("userEmail");
		if(userEmail == null) {
			return;
		}
		searchMap.put("userEmail", userEmail);
		searchMap.put("content", query);
		int searchingCheck = repo.getSearchingCheckByUserEmailAndContent(searchMap);
		if(searchingCheck == 0) {
			ContentSearchingDTO contentSearchingDto =
					ContentSearchingDTO
					.builder()
					.userEmail(userEmail)
					.content(query)
					.searchDate(getDate())
					.searchUse(1)
					.build();
			repo.contentInsert(contentSearchingDto);
		}else {
			Map<String, String> map = new HashMap<>();
			map.put("userEmail", userEmail);
			map.put("content", query);
			repo.recentSearchesAddUpdateByUserEmailAndContent(map);
		}
	}
	
	public void recentSearchesAllRemoveUpdateByUserEmail() {
		repo.recentSearchesAllRemoveUpdateByUserEmail((String)session.getAttribute("userEmail"));
	}
	
	private String getSixMonthAgoDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -6);
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dtFormat.format(cal.getTime());
	}

	private GradeInfoDTO getMovieGradeAvgByMovieId(String movieId) {
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
	
	private boolean getMovieGradeCheckById(String id) {
		boolean gradeCheck = false;
		int check = homeRepository.getGradeCheckById(id);
		if(check == 1)
			gradeCheck = true;
		
		return gradeCheck;
	}
	
	private List<MovieInfoSearchViewDTO> getMovieInfoSearchList(List<MovieInfoDTO> movieInfoList, List<MovieInfoSearchViewDTO> movieInfoSearchList) {
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

	
	
	private String getDate() {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(currentDate);
	}
}
