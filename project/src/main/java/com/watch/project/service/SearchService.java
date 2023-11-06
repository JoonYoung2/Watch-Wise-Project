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
import com.watch.project.dto.relatedSearch.RelatedSearchRequestDTO;
import com.watch.project.dto.relatedSearch.RelatedSearchResponseDTO;
import com.watch.project.dto.searchView.MovieActorsDTO;
import com.watch.project.dto.searchView.MovieInfoSearchViewDTO;
import com.watch.project.dto.searchView.PeopleInfoSearchViewDTO;
import com.watch.project.repository.HomeRepository;
import com.watch.project.repository.SearchRepository;
import com.watch.project.repository.recommended.RecommendedRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
	private final SearchRepository repo;
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
	
	/*
	 * 연관 검색어 가져오기
	 */
	public List<RelatedSearchResponseDTO> getRelatedSearchByContentAndUserEmail(String content) {
		content = consonantRegux(content);
		List<RelatedSearchResponseDTO> relatedSearchResponseList1 = 
				repo.getRelatedSearchByContentAndUserEmail(
				RelatedSearchRequestDTO
				.builder()
				.content(content)
				.userEmail((String)session.getAttribute("userEmail"))
				.build());
		content = consonantAndVowelRegux(content);
		List<RelatedSearchResponseDTO> relatedSearchResponseList2 = 
				repo.getRelatedSearchByContentAndUserEmail(
				RelatedSearchRequestDTO
				.builder()
				.content(content)
				.userEmail((String)session.getAttribute("userEmail"))
				.build());
		for(int i = 0; i < relatedSearchResponseList2.size(); ++i) {
			relatedSearchResponseList1.add(relatedSearchResponseList2.get(i));
		}
		
		return removeDuplicationContent(relatedSearchResponseList1);
	}
	
	/*
	 * 중복 검색 내용 제거
	 */
	private List<RelatedSearchResponseDTO> removeDuplicationContent(List<RelatedSearchResponseDTO> relatedSearchResponseList) {
		for(int i = 0; i < relatedSearchResponseList.size()-1; ++i) {
			String content = relatedSearchResponseList.get(i).getContent();
			for(int j = i+1; j < relatedSearchResponseList.size(); ++j) {
				if(relatedSearchResponseList.get(j).getContent().equals(content)) {
					relatedSearchResponseList.remove(j);
				}
			}
		}
		return relatedSearchResponseList;
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
	
	/*
	 * 자음 + 모음 합친 정규식
	 * ex) 흐 -> [흐-힣]
	 */
	private String consonantAndVowelRegux(String content) {
		char c = content.charAt(content.length()-1);
		
		/*
		 * ㄱ
		 */
		if(c == '교' || c=='꾜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '겨' || c=='껴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '갸' || c=='꺄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '개' || c=='깨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '걔' || c=='꺠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '게' || c=='께') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '계' || c=='꼐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '고' || c=='꼬') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '거' || c=='꺼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '가' || c=='까') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '기' || c=='끼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '규' || c=='뀨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '구' || c=='꾸') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '그' || c=='끄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '괘' || c=='꽤') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '괴' || c=='꾀') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '과' || c=='꽈') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '궈' || c=='꿔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '귀' || c=='뀌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '궤' || c=='꿰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '긔' || c=='끠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}
		
		/*
		 * ㄴ
		 */
		else if(c == '뇨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '녀') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '냐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '내') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '냬') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '네') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '녜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '노') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '너') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '나') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '니') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '뉴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '누') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '느') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '놰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '뇌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '놔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '눠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '뉘') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '눼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}else if(c == '늬') {
			content = content.substring(0, content.length()-1) + "[" + c + "-닣]";
		}
		
		/*
		 * ㄷ
		 */
		else if(c == '됴' || c=='뚀') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '뎌' || c=='뗘') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '댜' || c=='땨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '대' || c=='때') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '댸' || c=='떄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '데' || c=='떼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '뎨' || c=='뗴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '도' || c=='또') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '더' || c=='떠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '다' || c=='따') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '디' || c=='띠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '듀' || c=='뜌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '두' || c=='뚜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '드' || c=='뜨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '돼' || c=='뙈') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '되' || c=='뙤') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '돠' || c=='똬') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '둬' || c=='뚸') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '뒤' || c=='뛰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '뒈' || c=='뛔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '듸' || c=='띄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}
		
		/*
		 * ㄹ
		 */
		else if(c == '료') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '려') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '랴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '래') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '럐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '레') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '례') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '로') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '러') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '라') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '리') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '류') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '류') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '르') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '뢔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '뢰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '롸') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '뤄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '뤼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '뤠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}else if(c == '릐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-맇]";
		}
		
		/*
		 * ㅁ
		 */
		else if(c == '묘') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '며') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '먀') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '매') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '먜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '메') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '몌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '모') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '머') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '마') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '미') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '뮤') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '무') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '므') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '뫠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '뫼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '뫄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '뭐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '뮈') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '뭬') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}else if(c == '믜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-밓]";
		}
		
		/*
		 * ㅂ
		 */
		else if(c == '뵤' || c=='뾰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '벼' || c=='뼈') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '뱌' || c=='뺘') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '배' || c=='빼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '뱨' || c=='뺴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '베' || c=='뻬') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '볘' || c=='뼤') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '보' || c=='뽀') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '버' || c=='뻐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '바' || c=='빠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '비' || c=='삐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '뷰' || c=='쀼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '부' || c=='뿌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '브' || c=='쁘') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '봬' || c=='뽸') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '뵈' || c=='뾔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '봐' || c=='뽜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '붜' || c=='뿨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '뷔' || c=='쀠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '붸' || c=='쀄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '븨' || c=='쁴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}
		
		/*
		 * ㅅ
		 */
		else if(c == '쇼' || c=='쑈') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '셔' || c=='쎠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '샤' || c=='쌰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '새' || c=='쌔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '섀' || c=='썌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '세' || c=='쎄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '셰' || c=='쎼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '소' || c=='쏘') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '서' || c=='써') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '사' || c=='싸') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '시' || c=='씨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '슈' || c=='쓔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '수' || c=='쑤') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '스' || c=='쓰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '쇄' || c=='쐐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '쇠' || c=='쐬') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '솨' || c=='쏴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '숴' || c=='쒀') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '쉬' || c=='쒸') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '쉐' || c=='쒜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '싀' || c=='씌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}
		
		/*
		 * ㅇ
		 */
		else if(c == '요') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '여') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '야') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '애') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '얘') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '에') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '예') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '오') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '어') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '아') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '이') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '유') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '우') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '으') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '왜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '외') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '와') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '워') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '위') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '웨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}else if(c == '의') {
			content = content.substring(0, content.length()-1) + "[" + c + "-잏]";
		}
		
		/*
		 * ㅈ
		 */
		else if(c == '죠' || c=='쬬') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '져' || c=='쪄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '쟈' || c=='쨔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '재' || c=='째') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '쟤' || c=='쨰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '제' || c=='쩨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '졔' || c=='쪠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '조' || c=='쪼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '저' || c=='쩌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '자' || c=='짜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '지' || c=='찌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '쥬' || c=='쮸') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '주' || c=='쭈') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '즈' || c=='쯔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '좨' || c=='쫴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '죄' || c=='쬐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '좌' || c=='쫘') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '줘' || c=='쭤') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '쥐' || c=='쮜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '줴' || c=='쮀') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '즤' || c=='쯰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}
		
		/*
		 * ㅊ
		 */
		else if(c == '쵸') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '쳐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '챠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '채') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '챼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '체') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '쳬') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '초') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '처') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '차') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '치') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '츄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '추') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '츠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '쵀') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '최') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '촤') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '춰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '취') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '췌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}else if(c == '츼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-칳]";
		}
		
		/*
		 * ㅋ
		 */
		else if(c == '쿄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '켜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '캬') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '캐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '컈') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '케') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '켸') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '코') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '커') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '카') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '키') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '큐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '쿠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '크') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '쾌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '쾨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '콰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '쿼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '퀴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '퀘') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}else if(c == '킈') {
			content = content.substring(0, content.length()-1) + "[" + c + "-킿]";
		}
		
		/*
		 * ㅌ
		 */
		else if(c == '툐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '텨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '탸') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '태') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '턔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '테') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '톄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '토') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '터') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '타') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '티') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '튜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '투') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '트') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '퇘') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '퇴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '톼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '퉈') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '튀') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '퉤') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}else if(c == '틔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-팋]";
		}
		
		/*
		 * ㅍ
		 */
		else if(c == '표') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '펴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '퍄') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '패') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '퍠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '페') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '폐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '포') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '퍼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '파') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '피') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '퓨') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '푸') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '프') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '퐤') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '푀') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '퐈') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '풔') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '퓌') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '풰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}else if(c == '픠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-핗]";
		}
		
		/*
		 * ㅎ
		 */
		else if(c == '효') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '혀') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '햐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '해') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '햬') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '헤') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '혜') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '호') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '허') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '하') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '히') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '휴') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '후') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '흐') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '홰') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '회') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '화') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '훠') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '휘') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '훼') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}else if(c == '희') {
			content = content.substring(0, content.length()-1) + "[" + c + "-힣]";
		}

		return content;
	}

	/*
	 * 자음 정규식
	 * ex) ㅎ -> [하-힣]
	 */
	private String consonantRegux(String content) {
		for(char c : content.toCharArray()) {
			if(c == 'ㄱ' || c == 'ㄲ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㄴ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㄷ' || c == 'ㄸ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㄹ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅁ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅂ' || c == 'ㅃ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅅ' || c == 'ㅆ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅇ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅈ' || c == 'ㅉ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅊ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅋ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅌ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅍ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅎ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}
		}
		return content;
	}

	private String regularConversion(char c) {
		String conversion = "";
		if(c == 'ㄱ' || c == 'ㄲ') {
			conversion = "\\[가-낗\\]";
		}else if(c == 'ㄴ') {
			conversion = "\\[나-닣\\]";
		}else if(c == 'ㄷ' || c == 'ㄸ') {
			conversion = "\\[다-띻\\]";
		}else if(c == 'ㄹ') {
			conversion = "\\[라-맇\\]";
		}else if(c == 'ㅁ') {
			conversion = "\\[마-밓\\]";
		}else if(c == 'ㅂ' || c == 'ㅃ') {
			conversion = "\\[바-삫\\]";
		}else if(c == 'ㅅ' || c == 'ㅆ') {
			conversion = "\\[사-앃\\]";
		}else if(c == 'ㅇ') {
			conversion = "\\[아-잏\\]";
		}else if(c == 'ㅈ' || c == 'ㅉ') {
			conversion = "\\[자-찧\\]";
		}else if(c == 'ㅊ') {
			conversion = "\\[차-칳\\]";
		}else if(c == 'ㅋ') {
			conversion = "\\[카-킿\\]";
		}else if(c == 'ㅌ') {
			conversion = "\\[타-팋\\]";
		}else if(c == 'ㅍ') {
			conversion = "\\[파-핗\\]";
		}else if(c == 'ㅎ') {
			conversion = "\\[하-힣\\]";
		}
		
		return conversion;
	}
}
