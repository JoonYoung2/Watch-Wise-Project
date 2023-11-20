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
import org.springframework.ui.Model;

import com.watch.project.dto.ContentSearchingDTO;
import com.watch.project.dto.LiveSearchDTO;
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
	
	public int searchConfig() {
		int searchHistory = 0;
		String userEmail = (String) session.getAttribute("userEmail");
		try {
			searchHistory = repo.getSearchHistoryByUserEmail(userEmail);
			if(searchHistory == 1) {
				repo.searchOn(userEmail);
				searchHistory = 0;
			}else {
				repo.searchOff(userEmail);
				searchHistory = 1;
			}
		}catch(NullPointerException e) {
			repo.insertMemberConfig(userEmail);
			searchHistory = 1;
		}catch(BindingException e) {
			repo.insertMemberConfig(userEmail);
			searchHistory = 1;
		}
		
		return searchHistory;
	}
	
	public List<MovieInfoSearchViewDTO> movieNmSearchingCase(String query){
		List<MovieInfoDTO> movieInfoList = repo.searchingStep1(query);
		List<MovieInfoSearchViewDTO> movieInfoSearchList = new ArrayList<>();
		
		if(movieInfoList.size() == 0) {
			Map<String, String> queryMap = new HashMap<>();
			
			String content = consonantRegux(query);
			String content2 = consonantAndVowelRegux(content);
			queryMap.put("query", query);
			queryMap.put("content", content);
			queryMap.put("content2", content2);
			
			movieInfoList = repo.searchingStep2(queryMap);
			
			for(int i = 0; i < movieInfoList.size(); ++i) {
				String movieId = movieInfoList.get(i).getMovieId();
				for(int j = i+1; j < movieInfoList.size(); ++j) {
					if(movieId.equals(movieInfoList.get(j).getMovieId())) {
						movieInfoList.remove(j);
						j--;
					}
				}
			}
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
			Map<String, String> queryMap = new HashMap<>();
			String content = consonantRegux(query);
			String content2 = consonantAndVowelRegux(content);
			queryMap.put("query", query);
			queryMap.put("content", content);
			queryMap.put("content2", content2);
			peopleInfoDetailList = repo.searchingStep4(queryMap);
			
			for(int i = 0; i < peopleInfoDetailList.size(); ++i) {
				int peopleId = peopleInfoDetailList.get(i).getPeopleId();
				for(int j = i+1; j < peopleInfoDetailList.size(); ++j) {
					if(peopleId == peopleInfoDetailList.get(j).getPeopleId()) {
						peopleInfoDetailList.remove(j);
						j--;
					}
				}
			}
			
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
		
		
//		if(peopleInfoSearchViewList.size() > 50) {
//			for(int i = 50; i < peopleInfoSearchViewList.size(); ++i) {
//				peopleInfoSearchViewList.remove(i);
//			}
//		}
		
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
		String userEmail = (String)session.getAttribute("userEmail");
		if(userEmail == null) {
			return;
		}
		int searchHistory = 0;
		try {
			searchHistory = repo.getSearchHistoryByUserEmail(userEmail);			
		}catch(NullPointerException e) {
		}catch(BindingException e) {
		}
		
		Map<String, String> searchMap = new HashMap<>();
		searchMap.put("userEmail", userEmail);
		searchMap.put("content", query);
		int searchingCheck = repo.getSearchingCheckByUserEmailAndContent(searchMap);
		
		if(searchingCheck == 0 && searchHistory == 0) {
			ContentSearchingDTO contentSearchingDto =
					ContentSearchingDTO
					.builder()
					.userEmail(userEmail)
					.content(query)
					.searchDate(getDate())
					.searchUse(1)
					.build();
			repo.contentInsert(contentSearchingDto);
		}else if(searchingCheck == 0 && searchHistory == 1){
			ContentSearchingDTO contentSearchingDto =
					ContentSearchingDTO
					.builder()
					.userEmail(userEmail)
					.content(query)
					.searchDate(getDate())
					.searchUse(2)
					.build();
			repo.contentInsert(contentSearchingDto);
		}else {
			Map<String, String> map = new HashMap<>();
			map.put("userEmail", userEmail);
			map.put("searchDate", getDate());
			map.put("content", query);
			if(searchHistory == 0) {
				repo.recentSearchesAddUpdateByUserEmailAndContent(map);				
			}else {
				repo.recentSearchesAddUpdateByUserEmailAndContent2(map);
			}
		}
	}
	
	public List<ContentSearchingDTO> getContentSearchByUserEmail(){
		String userEmail = (String) session.getAttribute("userEmail");
		return repo.getContentSearchByUserEmail(userEmail);
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
	 * 모든 검색 내용 삭제
	 */
	public void deleteAllSearchHistory() {
		String userEmail = (String) session.getAttribute("userEmail");
		repo.deleteAllSearchHistory(userEmail);
	}
	
	/*
	 * 선택한 검색 내용 삭제
	 */
	public void deleteSearchHistory(String ids) {
		repo.deleteSearchHistory(ids);
	}
	
	/*
	 * 검색어 관련 Model
	 */
	public void searchModel(Model model) {
		/*
		 * 최근 검색어
		 */
		String[] recentSearches = recentSearchesByUserEmail();
		
		/*
		 * 최근 6개월 간 인기 검색어
		 */
		String[] popularSearches = popularSearches();
		
		/*
		 * 실시간 검색어
		 */
		List<LiveSearchDTO> liveSearchList = getLiveSearchList();
		
		/*
		 * 최근 검색어 + 인기 검색어 크기
		 */
		int recentSearchesSize = -1;
		
		try {
			recentSearchesSize = recentSearches.length;			
		}catch(NullPointerException e) {
			
		}
		
		model.addAttribute("recentSearches", recentSearches);
		model.addAttribute("popularSearches", popularSearches);
		model.addAttribute("liveSearch", liveSearchList);
		model.addAttribute("recentSearchesSize", recentSearchesSize);
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
					j--;
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
			c = '교';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '겨' || c=='껴') {
			c = '겨';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '갸' || c=='꺄') {
			c = '갸';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '개' || c=='깨') {
			c = '개';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '걔' || c=='꺠') {
			c = '걔';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '게' || c=='께') {
			c = '게';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '계' || c=='꼐') {
			c = '계';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '고' || c=='꼬') {
			c = '고';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '거' || c=='꺼') {
			c = '거';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '가' || c=='까') {
			c = '가';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '기' || c=='끼') {
			c = '기';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '규' || c=='뀨') {
			c = '규';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '구' || c=='꾸') {
			c = '구';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '그' || c=='끄') {
			c = '그';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '괘' || c=='꽤') {
			c = '괘';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '괴' || c=='꾀') {
			c = '괴';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '과' || c=='꽈') {
			c = '과';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '궈' || c=='꿔') {
			c = '궈';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '귀' || c=='뀌') {
			c = '귀';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '궤' || c=='꿰') {
			c = '궤';
			content = content.substring(0, content.length()-1) + "[" + c + "-깋]";
		}else if(c == '긔' || c=='끠') {
			c = '긔';
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
			c = '됴';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '뎌' || c=='뗘') {
			c = '뎌';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '댜' || c=='땨') {
			c = '댜';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '대' || c=='때') {
			c = '대';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '댸' || c=='떄') {
			c = '댸';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '데' || c=='떼') {
			c = '데';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '뎨' || c=='뗴') {
			c = '뎨';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '도' || c=='또') {
			c = '도';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '더' || c=='떠') {
			c = '더';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '다' || c=='따') {
			c = '다';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '디' || c=='띠') {
			c = '디';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '듀' || c=='뜌') {
			c = '듀';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '두' || c=='뚜') {
			c = '두';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '드' || c=='뜨') {
			c = '드';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '돼' || c=='뙈') {
			c = '돼';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '되' || c=='뙤') {
			c = '되';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '돠' || c=='똬') {
			c = '돠';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '둬' || c=='뚸') {
			c = '둬';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '뒤' || c=='뛰') {
			c = '뒤';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '뒈' || c=='뛔') {
			c = '뒈';
			content = content.substring(0, content.length()-1) + "[" + c + "-딯]";
		}else if(c == '듸' || c=='띄') {
			c = '듸';
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
			c = '뵤';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '벼' || c=='뼈') {
			c = '벼';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '뱌' || c=='뺘') {
			c = '뱌';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '배' || c=='빼') {
			c = '배';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '뱨' || c=='뺴') {
			c = '뱨';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '베' || c=='뻬') {
			c = '베';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '볘' || c=='뼤') {
			c = '볘';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '보' || c=='뽀') {
			c = '보';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '버' || c=='뻐') {
			c = '버';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '바' || c=='빠') {
			c = '바';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '비' || c=='삐') {
			c = '비';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '뷰' || c=='쀼') {
			c = '뷰';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '부' || c=='뿌') {
			c = '부';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '브' || c=='쁘') {
			c = '브';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '봬' || c=='뽸') {
			c = '봬';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '뵈' || c=='뾔') {
			c = '뵈';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '봐' || c=='뽜') {
			c = '봐';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '붜' || c=='뿨') {
			c = '붜';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '뷔' || c=='쀠') {
			c = '뷔';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '붸' || c=='쀄') {
			c = '붸';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}else if(c == '븨' || c=='쁴') {
			c = '븨';
			content = content.substring(0, content.length()-1) + "[" + c + "-빟]";
		}
		
		/*
		 * ㅅ
		 */
		else if(c == '쇼' || c=='쑈') {
			c = '쇼';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '셔' || c=='쎠') {
			c = '셔';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '샤' || c=='쌰') {
			c = '샤';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '새' || c=='쌔') {
			c = '새';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '섀' || c=='썌') {
			c = '섀';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '세' || c=='쎄') {
			c = '세';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '셰' || c=='쎼') {
			c = '셰';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '소' || c=='쏘') {
			c = '소';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '서' || c=='써') {
			c = '서';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '사' || c=='싸') {
			c = '사';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '시' || c=='씨') {
			c = '시';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '슈' || c=='쓔') {
			c = '슈';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '수' || c=='쑤') {
			c = '수';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '스' || c=='쓰') {
			c = '스';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '쇄' || c=='쐐') {
			c = '쇄';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '쇠' || c=='쐬') {
			c = '쇠';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '솨' || c=='쏴') {
			c = '솨';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '숴' || c=='쒀') {
			c = '숴';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '쉬' || c=='쒸') {
			c = '쉬';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '쉐' || c=='쒜') {
			c = '쉐';
			content = content.substring(0, content.length()-1) + "[" + c + "-싷]";
		}else if(c == '싀' || c=='씌') {
			c = '싀';
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
			c = '죠';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '져' || c=='쪄') {
			c = '져';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '쟈' || c=='쨔') {
			c = '쟈';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '재' || c=='째') {
			c = '재';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '쟤' || c=='쨰') {
			c = '쟤';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '제' || c=='쩨') {
			c = '제';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '졔' || c=='쪠') {
			c = '졔';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '조' || c=='쪼') {
			c = '조';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '저' || c=='쩌') {
			c = '저';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '자' || c=='짜') {
			c = '자';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '지' || c=='찌') {
			c = '지';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '쥬' || c=='쮸') {
			c = '쥬';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '주' || c=='쭈') {
			c = '주';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '즈' || c=='쯔') {
			c = '즈';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '좨' || c=='쫴') {
			c = '좨';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '죄' || c=='쬐') {
			c = '죄';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '좌' || c=='쫘') {
			c = '좌';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '줘' || c=='쭤') {
			c = '줘';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '쥐' || c=='쮜') {
			c = '쥐';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '줴' || c=='쮀') {
			c = '줴';
			content = content.substring(0, content.length()-1) + "[" + c + "-짛]";
		}else if(c == '즤' || c=='쯰') {
			c = '즤';
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
			}else if(c == 'ㄳ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㄵ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㄶ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㄺ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㄻ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㄼ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㄽ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㄾ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㄿ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅀ') {
				String regular = regularConversion(c);
				content = content.replaceAll(c+"", regular);
			}else if(c == 'ㅄ') {
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
		}else if(c == 'ㄳ') {
			conversion = "\\[가-깋\\]\\[사-싷\\]";
		}else if(c == 'ㄵ') {
			conversion = "\\[나-닣\\]\\[자-짛\\]";
		}else if(c == 'ㄶ') {
			conversion = "\\[나-닣\\]\\[하-힣\\]";
		}else if(c == 'ㄺ') {
			conversion = "\\[라-맇\\]\\[가-깋\\]";
		}else if(c == 'ㄻ') {
			conversion = "\\[라-맇\\]\\[마-밓\\]";
		}else if(c == 'ㄼ') {
			conversion = "\\[라-맇\\]\\[바-빟\\]";
		}else if(c == 'ㄽ') {
			conversion = "\\[라-맇\\]\\[사-싷\\]";
		}else if(c == 'ㄾ') {
			conversion = "\\[라-맇\\]\\[타-팋\\]";
		}else if(c == 'ㄿ') {
			conversion = "\\[라-맇\\]\\[파-핗\\]";
		}else if(c == 'ㅀ') {
			conversion = "\\[라-맇\\]\\[라-맇\\]";
		}else if(c == 'ㅄ') {
			conversion = "\\[바-빟\\]\\[사-싷\\]";
		}
		
//		else if(c == '!' || c == '@' || c == '#' || c == '$' || c == '%' || c == '^' || c == '&' || c == '&' || c == '*' || c == '(' || c == ')') {
//			conversion = "\\[!@#$%^&*()\\]";
//		}
		
		return conversion;
	}

	public List<LiveSearchDTO> getLiveSearchList() {
		
		return repo.getLiveSearchList();
	}
}
