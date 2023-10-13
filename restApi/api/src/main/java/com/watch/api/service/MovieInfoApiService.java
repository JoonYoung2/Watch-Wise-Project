package com.watch.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.watch.api.dto.MovieInfoDTO;
import com.watch.api.repository.MovieInfoApiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieInfoApiService {
	private final MovieInfoApiRepository repo;
	// 영화 목록 API호출 -> 상세 영화정보 API호출 -> poster구하기 위한 API호출
	public void saveMovieInfoByMovieName(String movieNm) throws IOException {
		System.out.println("=================== START ===================");
		System.out.println("MovieInfoApiService - getMovieInfoByMovieName()");
		String movieCd = "";
		// 영화 목록 api 호츌(영화 제목으로)
		String reqUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888&movieNm=1947 보스톤";
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888");
		urlBuilder.append("&movieNm=" + URLEncoder.encode(movieNm, "UTF-8"));
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode =" + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = "";
			String result = "";
			
			while((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body ="+result);
			conn.disconnect();
			br.close();
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			JsonObject movieListResult = element.getAsJsonObject().get("movieListResult").getAsJsonObject();
			int totCnt = movieListResult.getAsJsonObject().get("totCnt").getAsInt();
			if(totCnt == 0) {
				System.out.println("영화 명에 맞는 정보가 없습니다.");
				System.out.println("MovieInfoApiService - getMovieInfoByMovieName()");
				System.out.println("=================== END ===================");
				return;
			}
			JsonArray movieList = movieListResult.getAsJsonObject().get("movieList").getAsJsonArray();
			for(int i = 0; i < movieList.size(); ++i) {
				if(movieList.get(i).getAsJsonObject().get("movieNm").toString().replaceAll("\"", "").equals(movieNm)) {
					movieCd = movieList.get(i).getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
					break;
				}
			}
			
			
			// 저장하고자 하는 영화가 이미 DB에 있는지 checking 여부
			MovieInfoDTO dto = repo.getMovieInfoByMovieId(movieCd);
			
			if(dto != null) {
				System.out.println("이미 영화가 등록되어있습니다.");
				System.out.println("MovieInfoApiService - getMovieInfoByMovieName()");
				System.out.println("=================== END ===================");
				return;
			}
			JsonObject movieInfo = getMovieDetailInfo(movieCd);
			if(movieInfo != null) {
				MovieInfoDTO movieInfoDto = setMovieInfoDto(movieInfo);
				repo.movieInfoSave(movieInfoDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 가져온 api데이터를 통해 movie_id값으로 상세정보 api호출
		System.out.println("MovieInfoApiService - getMovieInfoByMovieName()");
		System.out.println("=================== END ===================");
	}
	
	public void saveMovieInfoByMovieId(String movieId) {
		System.out.println("=================== START ===================");
		System.out.println("MovieInfoApiService - getMovieInfoByMovieId()");
		try {
			// 저장하고자 하는 영화가 이미 DB에 있는지 checking 여부
			MovieInfoDTO dto = repo.getMovieInfoByMovieId(movieId);
			
			if(dto != null) {
				System.out.println("이미 영화가 등록되어있습니다.");
				System.out.println("MovieInfoApiService - getMovieInfoByMovieId()");
				System.out.println("=================== END ===================");
				return;
			}
			JsonObject movieInfo = getMovieDetailInfo(movieId);
			if(movieInfo != null) {
				MovieInfoDTO movieInfoDto = setMovieInfoDto(movieInfo);
				repo.movieInfoSave(movieInfoDto);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	// 가져온 api데이터를 통해 movie_id값으로 상세정보 api호출
	System.out.println("MovieInfoApiService - getMovieInfoByMovieId()");
	System.out.println("=================== END ===================");
	}
	
	private MovieInfoDTO setMovieInfoDto(JsonObject movieInfo) {
		System.out.println("=================== START ===================");
		System.out.println("MovieInfoApiService - setMovieInfoDto()");
		String movieId = movieInfo.getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
		System.out.println("service - setMovieInfoDto - movieId ==> " + movieId);
		String movieNm = movieInfo.getAsJsonObject().get("movieNm").toString().replaceAll("\"", "");
		System.out.println("service - setMovieInfoDto - movieNm ==> " + movieNm);
		String movieNmEn = movieInfo.getAsJsonObject().get("movieNmEn").toString().replaceAll("\"", "");
		System.out.println("service - setMovieInfoDto - movieNmEn ==> " + movieNmEn);
		String prdtYear = movieInfo.getAsJsonObject().get("prdtYear").toString().replaceAll("\"", "");
		System.out.println("service - setMovieInfoDto - prdtYear ==> " + prdtYear);
		String openDt = movieInfo.getAsJsonObject().get("openDt").toString().replaceAll("\"", "");
		System.out.println("service - setMovieInfoDto - openDt ==> " + openDt);
		String typeNm = movieInfo.getAsJsonObject().get("typeNm").toString().replaceAll("\"", "");
		System.out.println("service - setMovieInfoDto - typeNm ==> " + typeNm);
		int showTime = 0;
		try {
			showTime = Integer.parseInt(movieInfo.getAsJsonObject().get("showTm").toString().replaceAll("\"", ""));
			System.out.println("service - setMovieInfoDto - showTime ==> " + showTime);
		}catch(NumberFormatException e) {
			showTime = 0;
		}
		
		
		String nations = "";
		String genreNm = "";
		String actors = "";
		String cast = "";
		String watchGradeNm = "";
		String posterUrl = "";
		try {
			JsonArray nationsList = movieInfo.getAsJsonObject().get("nations").getAsJsonArray();
			for(int i = 0; i < nationsList.size(); ++i) {
				if(i != nationsList.size()-1) {
					nations += nationsList.get(i).getAsJsonObject().get("nationNm").toString().replaceAll("\"", "") + ",";				
				}else
					nations += nationsList.get(i).getAsJsonObject().get("nationNm").toString().replaceAll("\"", "");
				
				System.out.println("service - setMovieInfoDto - nations ==> " + nations);
			}
		}catch(NumberFormatException e) {
			nations = "nan";
		}
		
		try {
			JsonArray genresList = movieInfo.getAsJsonObject().get("genres").getAsJsonArray();
			for(int i = 0; i < genresList.size(); ++i) {
				if(i != genresList.size()-1) {
					genreNm += genresList.get(i).getAsJsonObject().get("genreNm").toString().replaceAll("\"", "") + ",";
				}else
					genreNm += genresList.get(i).getAsJsonObject().get("genreNm").toString().replaceAll("\"", "");
			}
			System.out.println("service - setMovieInfoDto - genreNm ==> " + genreNm);
		}catch(NumberFormatException e) {
			genreNm = "nan";
		}
		
		try {
			JsonArray actorsList = movieInfo.getAsJsonObject().get("actors").getAsJsonArray();
			for(int i = 0; i < actorsList.size(); ++i) {
				if(i != actorsList.size()-1) {
					if(!actorsList.get(i).getAsJsonObject().get("peopleNm").toString().replaceAll("\"", "").equals("")) {
						actors += actorsList.get(i).getAsJsonObject().get("peopleNm").toString().replaceAll("\"", "") + ",";
					}
					if(!actorsList.get(i).getAsJsonObject().get("cast").toString().replaceAll("\"", "").equals("")) {
						cast += actorsList.get(i).getAsJsonObject().get("cast").toString().replaceAll("\"", "") + ",";						
					}
				}else {
					if(!actorsList.get(i).getAsJsonObject().get("peopleNm").toString().replaceAll("\"", "").equals("")) {
						actors += actorsList.get(i).getAsJsonObject().get("peopleNm").toString().replaceAll("\"", "");
					}
					if(!actorsList.get(i).getAsJsonObject().get("cast").toString().replaceAll("\"", "").equals("")) {
						cast += actorsList.get(i).getAsJsonObject().get("cast").toString().replaceAll("\"", "");
					}
				}
			}
			System.out.println("service - setMovieInfoDto - actors ==> " + actors);
			System.out.println("service - setMovieInfoDto - cast ==> " + cast);
		}catch(NumberFormatException e) {
			actors = "nan";
			cast = "nan";
		}
		
		try {
			JsonArray auditsList = movieInfo.getAsJsonObject().get("audits").getAsJsonArray();
			for(int i = 0; i < auditsList.size(); ++i) {
				if(i != auditsList.size()-1) 
					watchGradeNm += auditsList.get(i).getAsJsonObject().get("watchGradeNm").toString().replaceAll("\"", "") + ",";
				else
					watchGradeNm += auditsList.get(i).getAsJsonObject().get("watchGradeNm").toString().replaceAll("\"", "");
				System.out.println("service - setMovieInfoDto - watchGradeNm ==> " + watchGradeNm);
			}
		}catch(NumberFormatException e) {
			watchGradeNm = "nan";
		}
		
		try {
			posterUrl = getPosterUrl(movieNm, openDt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("service - setMovieInfoDto - posterUrl ==> " + posterUrl);
		MovieInfoDTO dto = new MovieInfoDTO();
		dto.setMovieId(movieId);
		
		if(movieNm != null) {
			dto.setMovieNm(movieNm);			
		}else {
			dto.setMovieNm("nan");
		}
		
		if(movieNmEn != null) {
			dto.setMovieNmEn(movieNmEn);			
		}else {
			dto.setMovieNmEn("nan");
		}
		
		if(prdtYear != null) {
			dto.setPrdtYear(prdtYear);			
		}else {
			dto.setPrdtYear("nan");
		}
		
		if(openDt != null) {
			dto.setOpenDt(openDt);			
		}else {
			dto.setOpenDt("nan");
		}
		
		if(typeNm != null) {
			dto.setTypeNm(typeNm);			
		}else {
			dto.setTypeNm("nan");	
		}
		
		if(nations != null) {
			dto.setNations(nations);
		}else {
			dto.setNations("nan");	
		}
		
		if(genreNm != null) {
			dto.setGenreNm(genreNm);			
		}else {
			dto.setGenreNm("nan");
		}
		
		dto.setShowTime(showTime);
		System.out.println("actors ?? "+actors);
		System.out.println("cast ?? "+cast);
		if(actors != null) {
			if(!actors.equals("")) {
				dto.setActors(actors);							
			}else {
				dto.setActors("nan");
			}
		}else {
			dto.setActors("nan");
		}
		
		if(cast != null) {
			if(!cast.equals("")) {
				dto.setCast(cast);							
			}else {
				dto.setCast("nan");
			}
		}else {
			dto.setCast("nan");
		}
		
		if(watchGradeNm != null) {
			dto.setWatchGradeNm(watchGradeNm);			
		}else {
			dto.setWatchGradeNm("nan");
		}
		
		if(posterUrl.equals("") || posterUrl.equals("nan")) {
			dto.setPosterUrl("nan");
		}else {
			dto.setPosterUrl(posterUrl);
		}
		
		System.out.println("movieId => " + dto.getMovieId());
		System.out.println("movieNm => " + dto.getMovieNm());
		System.out.println("moviewNmEn => " + dto.getMovieNmEn());
		System.out.println("prdtYear => " + dto.getPrdtYear());
		System.out.println("openDt => "+dto.getOpenDt());
		System.out.println("typeNm => " + dto.getTypeNm());
		System.out.println("nations => " + dto.getNations());
		System.out.println("genreNm => " + dto.getGenreNm());
		System.out.println("showTime => " + dto.getShowTime());
		System.out.println("actors => " + dto.getActors());
		System.out.println("cast => " + dto.getCast());
		System.out.println("watchGradeNm => " + dto.getWatchGradeNm());
		System.out.println("posterUrl => " + dto.getPosterUrl());
		
		System.out.println("MovieInfoApiService - setMovieInfoDto()");
		System.out.println("=================== END ===================");
		return dto;
	}
	
	private String getPosterUrl(String movieNm, String openDt) throws IOException { 
		System.out.println("=================== START ===================");
		System.out.println("MovieInfoApiService - getPosterUrl()");
		
		String posterUrl = "";
//		String reqUrl = "https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&title=마음";
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
		urlBuilder.append("&ServiceKey=" + URLEncoder.encode("0TN0PQIFW51T18N3L053", "UTF-8"));
		urlBuilder.append("&listCount=500");
		urlBuilder.append("&createDts=" + URLEncoder.encode(openDt.substring(0, 4), "UTF-8"));
		urlBuilder.append("&title=" + URLEncoder.encode(movieNm, "UTF-8"));
		System.out.println(urlBuilder.toString());
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode =" + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine()) != null) {
				sb.append(line);
//				result += line;
			}
			br.close();
			conn.disconnect();
			result = sb.toString();
			result = result.replaceAll(" !HS ", "");
			result = result.replaceAll(" !HE ", "");
			System.out.println("result = " + result);
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			int totalCount = element.getAsJsonObject().get("TotalCount").getAsInt();
			if(totalCount == 0) {
				return "nan";
			}
			JsonArray dataList = element.getAsJsonObject().get("Data").getAsJsonArray();
			JsonArray resultList = dataList.get(0).getAsJsonObject().get("Result").getAsJsonArray();
			System.out.println("resultListSize = " + resultList.size());
			for(int i = 0; i < resultList.size(); ++i) {
				if(resultList.get(i).getAsJsonObject().get("prodYear").toString().replaceAll("\"", "").equals(openDt.substring(0, 4))) {
					posterUrl = resultList.get(i).getAsJsonObject().get("posters").toString().replaceAll("\"", "");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("MovieInfoApiService - getPosterUrl()");
		System.out.println("=================== END ===================");
		return posterUrl;
	}
	
	// 상세영화정보 API 호출
	private JsonObject getMovieDetailInfo(String movieCd) {
		System.out.println("=================== START ===================");
		System.out.println("MovieInfoApiService - getMovieDetailInfo()");
		String reqUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=f5eef3421c602c6cb7ea224104795888&movieCd=" + movieCd;
		JsonObject movieInfo = null;
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode =" + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = "";
			String result = "";
			
			while((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body ="+result);
			conn.disconnect();
			br.close();
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			JsonObject movieListResult = element.getAsJsonObject().get("movieInfoResult").getAsJsonObject();
			movieInfo = movieListResult.getAsJsonObject().get("movieInfo").getAsJsonObject(); 
			System.out.println(movieInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("MovieInfoApiService - getMovieDetailInfo()");
		System.out.println("=================== END ===================");
		return movieInfo;
	}

	public List<MovieInfoDTO> getMovieIdByUrlNan() {
		return repo.getMovieIdByUrlNan();
	}
	
	public void moviePosterUrlUpdate(String movieId, String movieNm, String openDt) throws IOException { 
		System.out.println("=================== START ===================");
		System.out.println("MovieInfoApiService - moviePosterUrlUpdate()");
		
		String posterUrl = "";
//		String reqUrl = "https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&title=마음";
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
		urlBuilder.append("&ServiceKey=" + URLEncoder.encode("0TN0PQIFW51T18N3L053", "UTF-8"));
		urlBuilder.append("&listCount=500");
		urlBuilder.append("&query=" + URLEncoder.encode(movieNm, "UTF-8"));
		urlBuilder.append("&createDts=" + URLEncoder.encode(openDt.substring(0, 4), "UTF-8"));
		urlBuilder.append("&title=" + URLEncoder.encode(movieNm, "UTF-8"));
		System.out.println(urlBuilder.toString());
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode =" + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine()) != null) {
				sb.append(line);
//				result += line;
			}
			br.close();
			conn.disconnect();
			result = sb.toString();
			result = result.replaceAll(" !HS ", "");
			result = result.replaceAll(" !HE ", "");
			System.out.println("result = " + result);
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			int totalCount = element.getAsJsonObject().get("TotalCount").getAsInt();
			if(totalCount == 0) {
				System.out.println("MovieInfoApiService - moviePosterUrlUpdate()");
				System.out.println("totalCount ==> 0");
				System.out.println("=================== END ===================");
				return;
			}
			JsonArray dataList = element.getAsJsonObject().get("Data").getAsJsonArray();
			JsonArray resultList = dataList.get(0).getAsJsonObject().get("Result").getAsJsonArray();
			System.out.println("resultListSize = " + resultList.size());
			for(int i = 0; i < resultList.size(); ++i) {
				if(resultList.get(i).getAsJsonObject().get("prodYear").toString().replaceAll("\"", "").equals(openDt.substring(0, 4))) {
					posterUrl = resultList.get(i).getAsJsonObject().get("posters").toString().replaceAll("\"", "");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!posterUrl.equals("")){
			repo.moviePosterUrlUpdate(movieId, posterUrl);
			System.out.println("MovieInfoApiService - moviePosterUrlUpdate()");
			System.out.println("=================== success update ===================");
			System.out.println(movieId + " " + movieNm + " " + posterUrl);
			System.out.println("=================== END ===================");
		}else {
			System.out.println("MovieInfoApiService - moviePosterUrlUpdate()");
			System.out.println("=================== update failed ===================");
			System.out.println(movieId + " " + movieNm + " " + posterUrl);
			System.out.println("=================== END ===================");
		}
		
		
	}
	
	public List<MovieInfoDTO> getAllMovieNotNan(){
		return repo.getAllMovieNotNan();
	}
}
