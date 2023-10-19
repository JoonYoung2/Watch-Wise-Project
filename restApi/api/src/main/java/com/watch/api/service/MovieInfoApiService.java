package com.watch.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.watch.api.dto.MovieInfoDTO;
import com.watch.api.dto.MovieTopInfoDTO;
import com.watch.api.repository.MovieInfoApiRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieInfoApiService {
	private final MovieInfoApiRepository repo;
	
	// 영화 목록 API호출 -> 상세 영화정보 API호출 -> poster구하기 위한 API호출
	
	public void saveMovieInfoByMovieName(String movieNm) throws IOException {
		log.info("=================== START ===================");
		log.info("MovieInfoApiService - getMovieInfoByMovieName()");
		String movieCd = "";

		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888");
		urlBuilder.append("&movieNm=" + URLEncoder.encode(movieNm, "UTF-8"));
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			int responseCode = conn.getResponseCode();
			log.info("responseCode = {}", responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = "";
			String result = "";
			
			while((line = br.readLine()) != null) {
				result += line;
			}
			log.info("response body {}", result);
			conn.disconnect();
			br.close();
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			JsonObject movieListResult = element.getAsJsonObject().get("movieListResult").getAsJsonObject();
			int totCnt = movieListResult.getAsJsonObject().get("totCnt").getAsInt();
			if(totCnt == 0) {
				log.info("영화 명에 맞는 정보가 없습니다.");
				log.info("MovieInfoApiService - getMovieInfoByMovieName()");
				log.info("=================== END ===================");
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
				log.info("이미 영화가 등록되어있습니다.");
				log.info("MovieInfoApiService - getMovieInfoByMovieName()");
				log.info("=================== END ===================");
				return;
			}
			JsonObject movieInfo = getMovieDetailInfo(movieCd);
			if(movieInfo != null) {
				MovieInfoDTO movieInfoDto = setMovieInfoDto(movieInfo);
				if(movieInfoDto == null) {
					log.info("영화 등록이 실패하였습니다.");
					log.info("MovieInfoApiService - getMovieInfoByMovieName()");
					log.info("=================== END ===================");
					return;
				}else
					repo.movieInfoSave(movieInfoDto);
			}
		} catch (Exception e) {
			log.error("Error MovieInfoApiService - getMovieInfoByMovieName() => {} ", e);
		}
		// 가져온 api데이터를 통해 movie_id값으로 상세정보 api호출
		log.info("MovieInfoApiService - getMovieInfoByMovieName()");
		log.info("=================== END ===================");
	}
	
	public void saveMovieInfoByMovieId(String movieId) {
		log.info("=================== START ===================");
		log.info("MovieInfoApiService - getMovieInfoByMovieId()");
		try {
			// 저장하고자 하는 영화가 이미 DB에 있는지 checking 여부
			MovieInfoDTO dto = repo.getMovieInfoByMovieId(movieId);
			
			if(dto != null) {
				log.info("이미 영화가 등록되어있습니다.");
				log.info("MovieInfoApiService - getMovieInfoByMovieId()");
				log.info("=================== END ===================");
				return;
			}
			JsonObject movieInfo = getMovieDetailInfo(movieId);
			if(movieInfo != null) {
				MovieInfoDTO movieInfoDto = setMovieInfoDto(movieInfo);
				if(movieInfoDto == null) {
					log.info("영화 등록이 실패하였습니다.");
					log.info("MovieInfoApiService - getMovieInfoByMovieId()");
					log.info("=================== END ===================");
					return;
				}else
					repo.movieInfoSave(movieInfoDto);
		}
	} catch (Exception e) {
		log.error("Error MovieInfoApiService - getMovieInfoByMovieId() => {} ", e);
	}
	// 가져온 api데이터를 통해 movieId값으로 상세정보 api호출
	log.info("MovieInfoApiService - saveMovieInfoByMovieId()");
	log.info("=================== END ===================");
	}
	
	private MovieInfoDTO setMovieInfoDto(JsonObject movieInfo) throws IOException {
		Map<String, String> getKmdbApiMap = new HashMap<>();
		log.info("=================== START ===================");
		log.info("MovieInfoApiService - setMovieInfoDto()");
		String movieId = movieInfo.getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
		log.info("service - setMovieInfoDto - movieId ==> {} ", movieId);
		String movieNm = movieInfo.getAsJsonObject().get("movieNm").toString().replaceAll("\"", "");
		log.info("service - setMovieInfoDto - movieNm ==> {} ", movieNm);
		String movieNmEn = movieInfo.getAsJsonObject().get("movieNmEn").toString().replaceAll("\"", "");
		log.info("service - setMovieInfoDto - movieNmEn ==> {} ", movieNmEn);
		String prdtYear = movieInfo.getAsJsonObject().get("prdtYear").toString().replaceAll("\"", "");
		log.info("service - setMovieInfoDto - prdtYear ==> {} ", prdtYear);
		String openDt = movieInfo.getAsJsonObject().get("openDt").toString().replaceAll("\"", "");
		log.info("service - setMovieInfoDto - openDt ==> {} ", openDt);
		String typeNm = movieInfo.getAsJsonObject().get("typeNm").toString().replaceAll("\"", "");
		log.info("service - setMovieInfoDto - typeNm ==> {} ", typeNm);
		int showTime = 0;
		try {
			showTime = Integer.parseInt(movieInfo.getAsJsonObject().get("showTm").toString().replaceAll("\"", ""));
			log.info("MovieInfoApiService - setMovieInfoDto - showTime ==> {} ", showTime);
		}catch(NumberFormatException e) {
			showTime = 0;
			log.error("Error MovieInfoApiService - setMovieInfoDto => {}", e);
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
				
				log.info("MovieInfoApiService - setMovieInfoDto - nations ==> {} ", nations);
			}
		}catch(NumberFormatException e) {
			nations = "nan";
			log.error("Error MovieInfoApiService - setMovieInfoDto => {}", e);
		}
		
		try {
			JsonArray genresList = movieInfo.getAsJsonObject().get("genres").getAsJsonArray();
			for(int i = 0; i < genresList.size(); ++i) {
				if(i != genresList.size()-1) {
					genreNm += genresList.get(i).getAsJsonObject().get("genreNm").toString().replaceAll("\"", "") + ",";
				}else
					genreNm += genresList.get(i).getAsJsonObject().get("genreNm").toString().replaceAll("\"", "");
			}
			log.info("MovieInfoApiService - setMovieInfoDto - genreNm ==> {} ", genreNm);
		}catch(NumberFormatException e) {
			genreNm = "nan";
			log.error("Error MovieInfoApiService - setMovieInfoDto => {}", e);
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
			log.info("MovieInfoApiService - setMovieInfoDto - actors ==> {} ", actors);
			log.info("MovieInfoApiService - setMovieInfoDto - cast ==> {} ", cast);
		}catch(NumberFormatException e) {
			actors = "nan";
			cast = "nan";
			log.error("Error MovieInfoApiService - setMovieInfoDto => {}", e);
		}
		
		try {
			JsonArray auditsList = movieInfo.getAsJsonObject().get("audits").getAsJsonArray();
			for(int i = 0; i < auditsList.size(); ++i) {
				if(i != auditsList.size()-1) 
					watchGradeNm += auditsList.get(i).getAsJsonObject().get("watchGradeNm").toString().replaceAll("\"", "") + ",";
				else
					watchGradeNm += auditsList.get(i).getAsJsonObject().get("watchGradeNm").toString().replaceAll("\"", "");
				log.info("MovieInfoApiService - setMovieInfoDto - watchGradeNm ==> {} ", watchGradeNm);
			}
		}catch(NumberFormatException e) {
			watchGradeNm = "nan";
			log.error("Error MovieInfoApiService - setMovieInfoDto => {}", e);
		}
		
		try {
			getKmdbApiMap = getKmdbApiInfo(movieNm, openDt);
		} catch (IOException e) {
			log.error("Error MovieInfoApiService - setMovieInfoDto => {}", e);
		}
		log.info("posterUrl ==> {} ", posterUrl);
		MovieInfoDTO dto = new MovieInfoDTO();
		
		
		if(movieId != null) {
			if(!movieId.equals(""))
				dto.setMovieId(movieId);
			else
				return null;
		}else
			return null;
		
		
		
		if(movieNm != null) {
			if(!movieNm.equals(""))
				dto.setMovieNm(movieNm);							
			else 
				dto.setMovieNm("nan");
			
		}else
			dto.setMovieNm("nan");
		
		if(movieNmEn != null) {
			if(!movieNmEn.equals(""))
				dto.setMovieNmEn(movieNmEn);
			else
				dto.setMovieNmEn("nan");
		}else {
			dto.setMovieNmEn("nan");
		}
		
		if(prdtYear != null) {
			if(!prdtYear.equals(""))
				dto.setPrdtYear(prdtYear);
			else
				dto.setPrdtYear("nan");
		}else {
			dto.setPrdtYear("nan");
		}
		
		if(openDt != null) {
			if(!openDt.equals(""))
				dto.setOpenDt(openDt);
			else
				dto.setOpenDt("nan");
		}else {
			dto.setOpenDt("nan");
		}
		
		if(typeNm != null) {
			if(!typeNm.equals(""))
				dto.setTypeNm(typeNm);
			else
				dto.setTypeNm("nan");
		}else {
			dto.setTypeNm("nan");	
		}
		
		if(nations != null) {
			if(!nations.equals(""))
				dto.setNations(nations);
			else
				dto.setNations("nan");
		}else {
			dto.setNations("nan");	
		}
		
		if(genreNm != null) {
			if(!genreNm.equals(""))
				dto.setGenreNm(genreNm);
			else
				dto.setGenreNm("nan");
		}else {
			dto.setGenreNm("nan");
		}
		
		dto.setShowTime(showTime);

		if(actors != null) {
			if(!actors.equals("")) 
				dto.setActors(actors);							
			else 
				dto.setActors("nan");
			
		}else {
			dto.setActors("nan");
		}
		
		if(cast != null) {
			if(!cast.equals(""))
				dto.setCast(cast);							
			else
				dto.setCast("nan");
		}else {
			dto.setCast("nan");
		}
		
		if(watchGradeNm != null) {
			if(!watchGradeNm.equals(""))
				dto.setWatchGradeNm(watchGradeNm);
			else
				dto.setWatchGradeNm("nan");
		}else {
			dto.setWatchGradeNm("nan");
		}
		
		if(getKmdbApiMap.get("posterUrl").equals("") || getKmdbApiMap.get("posterUrl").equals("nan")) {
			dto.setPosterUrl("nan");
		}else {
			dto.setPosterUrl(getKmdbApiMap.get("posterUrl"));
		}
		
		if(getKmdbApiMap.get("docid").equals("") || getKmdbApiMap.get("docid").equals("nan")) {
			dto.setDocid("nan");
		}else {
			dto.setDocid(getKmdbApiMap.get("docid"));
		}
		
		log.info("movieId => {} ", dto.getMovieId());
		log.info("movieNm => {} ", dto.getMovieNm());
		log.info("moviewNmEn => {} ", dto.getMovieNmEn());
		log.info("prdtYear => {} ", dto.getPrdtYear());
		log.info("openDt => {} ", dto.getOpenDt());
		log.info("typeNm => {} ", dto.getTypeNm());
		log.info("nations => {} ", dto.getNations());
		log.info("genreNm => {} ", dto.getGenreNm());
		log.info("showTime => {} ", dto.getShowTime());
		log.info("actors => {} ", dto.getActors());
		log.info("cast => {} ", dto.getCast());
		log.info("watchGradeNm => {} ", dto.getWatchGradeNm());
		log.info("posterUrl => {} ", dto.getPosterUrl());
		log.info("docid => {} ", dto.getDocid());
		
		log.info("MovieInfoApiService - setMovieInfoDto()");
		log.info("=================== END ===================");
		return dto;
	}
	
	private Map<String, String> getKmdbApiInfo(String movieNm, String openDt) throws UnsupportedEncodingException {
		Map<String, String> map = new HashMap<>();
		log.info("=================== START ===================");
		log.info("MovieInfoApiService - getKmdbApiInfo()");
		
		String docid = "nan";
		String posterUrl = "nan";
		
		map.put("docid", docid);
		map.put("posterUrl", posterUrl);
		
//		String reqUrl = "https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&title=마음";
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
		urlBuilder.append("&ServiceKey=" + URLEncoder.encode("0TN0PQIFW51T18N3L053", "UTF-8"));
		urlBuilder.append("&listCount=500");
		urlBuilder.append("&title=" + URLEncoder.encode(movieNm, "UTF-8"));
		log.info("------------------- REST API 호출 -------------------");
		log.info(urlBuilder.toString());
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			int responseCode = conn.getResponseCode();
			log.info("responseCode = {} ", responseCode);
			
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
			if(sb.toString().length() > 700000) {
				log.info("{}의 길이가 70만이 넘습니다.", movieNm);
				log.info("MovieInfoApiService - getKmdbApiInfo()");
				log.info("=================== END ===================");
				return map;
			}
			result = sb.toString();
			result = result.replaceAll(" !HS ", "");
			result = result.replaceAll(" !HE ", "");
			log.info("result = {} ", result);
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			int totalCount = element.getAsJsonObject().get("TotalCount").getAsInt();
			if(totalCount == 0) {
				log.info("API 조회 결과가 없습니다.");
				log.info("MovieInfoApiService - getKmdbApiInfo()");
				log.info("=================== END ===================");
				return map;
			}
			JsonArray dataList = element.getAsJsonObject().get("Data").getAsJsonArray();
			JsonArray resultList = dataList.get(0).getAsJsonObject().get("Result").getAsJsonArray();
			log.info("resultListSize = {} ", resultList.size());
			for(int i = 0; i < resultList.size(); ++i) {
				String repRlsDate = resultList.get(i).getAsJsonObject().get("repRlsDate").toString().replaceAll("\"", "");
				if(repRlsDate.length() == 8) {
					if(repRlsDate.substring(0, 6).equals(openDt.substring(0, 6))) {
						
						docid = resultList.get(i).getAsJsonObject().get("DOCID").toString().replaceAll("\"", "");
						posterUrl = resultList.get(i).getAsJsonObject().get("posters").toString().replaceAll("\"", "");
						
						map.put("docid", docid);
						map.put("posterUrl", posterUrl);
						break;
					}					
				}
			}
		} catch (Exception e) {
			log.error("Error MovieInfoApiService - getKmdbApiInfo() => {}", e);
		}
		
		
		log.info("MovieInfoApiService - getKmdbApiInfo()");
		log.info("=================== END ===================");
		return map;
	}
	
	
	/*
	private String getPosterUrl(String movieNm, String openDt) throws IOException { 
		log.info("=================== START ===================");
		log.info("MovieInfoApiService - getPosterUrl()");
		
		String posterUrl = "nan";
//		String reqUrl = "https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&title=마음";
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
		urlBuilder.append("&ServiceKey=" + URLEncoder.encode("0TN0PQIFW51T18N3L053", "UTF-8"));
		urlBuilder.append("&listCount=500");
		urlBuilder.append("&title=" + URLEncoder.encode(movieNm, "UTF-8"));
		log.info("------------------- REST API 호출 -------------------");
		log.info(urlBuilder.toString());
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			int responseCode = conn.getResponseCode();
			log.info("responseCode = {} ", responseCode);
			
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
			if(sb.toString().length() > 700000) {
				log.info("{}의 길이가 70만이 넘습니다.", movieNm);
				log.info("MovieInfoApiService - getPosterUrl()");
				return "nan";
			}
			result = sb.toString();
			result = result.replaceAll(" !HS ", "");
			result = result.replaceAll(" !HE ", "");
			log.info("result = {} ", result);
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			int totalCount = element.getAsJsonObject().get("TotalCount").getAsInt();
			if(totalCount == 0) {
				return "nan";
			}
			JsonArray dataList = element.getAsJsonObject().get("Data").getAsJsonArray();
			JsonArray resultList = dataList.get(0).getAsJsonObject().get("Result").getAsJsonArray();
			log.info("resultListSize = {} ", resultList.size());
			for(int i = 0; i < resultList.size(); ++i) {
				String repRlsDate = resultList.get(i).getAsJsonObject().get("repRlsDate").toString().replaceAll("\"", "");
				if(repRlsDate.length() == 8) {
					if(repRlsDate.substring(0, 6).equals(openDt.substring(0, 6))) {
						posterUrl = resultList.get(i).getAsJsonObject().get("posters").toString().replaceAll("\"", "");
						break;
					}					
				}
			}
		} catch (Exception e) {
			log.error("Error MovieInfoApiService - getPosterUrl() => {}", e);
		}
		
		
		log.info("MovieInfoApiService - getPosterUrl()");
		log.info("=================== END ===================");
		return posterUrl;
	}
	*/
	
	
	// 상세영화정보 API 호출
	private JsonObject getMovieDetailInfo(String movieCd) {
		log.info("=================== START ===================");
		log.info("MovieInfoApiService - getMovieDetailInfo()");
		String reqUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=f5eef3421c602c6cb7ea224104795888&movieCd=" + movieCd;
		JsonObject movieInfo = null;
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			int responseCode = conn.getResponseCode();
			log.info("------------------- REST API 호출 -------------------");
			log.info(reqUrl);
			log.info("responseCode = {} ", responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = "";
			String result = "";
			
			while((line = br.readLine()) != null) {
				result += line;
			}
			log.info("response body = {}", result);
			conn.disconnect();
			br.close();
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			JsonObject movieListResult = element.getAsJsonObject().get("movieInfoResult").getAsJsonObject();
			movieInfo = movieListResult.getAsJsonObject().get("movieInfo").getAsJsonObject(); 
			log.info("{}", movieInfo);
		} catch (Exception e) {
			log.error("Error MovieInfoApiService - getMovieDetailInfo() => {}", e);
		}
		
		log.info("MovieInfoApiService - getMovieDetailInfo()");
		log.info("=================== END ===================");
		return movieInfo;
	}

	public List<MovieInfoDTO> getMovieIdByUrlNan() {
		return repo.getMovieIdByUrlNan();
	}
	
	public void moviePosterUrlUpdate(String movieId, String movieNm, String openDt) throws IOException { 
		log.info("=================== START ===================");
		log.info("MovieInfoApiService - moviePosterUrlUpdate()");
		
		String posterUrl = "";
//		String reqUrl = "https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&title=마음";
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
		urlBuilder.append("&ServiceKey=" + URLEncoder.encode("0TN0PQIFW51T18N3L053", "UTF-8"));
		urlBuilder.append("&listCount=500");
		urlBuilder.append("&query=" + URLEncoder.encode(movieNm, "UTF-8"));
		urlBuilder.append("&createDts=" + URLEncoder.encode(openDt.substring(0, 4), "UTF-8"));
		urlBuilder.append("&title=" + URLEncoder.encode(movieNm, "UTF-8"));
		log.info("------------------- REST API 호출 -------------------");
		log.info(urlBuilder.toString());
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			int responseCode = conn.getResponseCode();
			log.info("responseCode = {} ", responseCode);
			
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
			log.info("result = {} ", result);
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			int totalCount = element.getAsJsonObject().get("TotalCount").getAsInt();
			if(totalCount == 0) {
				log.info("MovieInfoApiService - moviePosterUrlUpdate()");
				log.info("totalCount ==> 0");
				log.info("=================== END ===================");
				return;
			}
			JsonArray dataList = element.getAsJsonObject().get("Data").getAsJsonArray();
			JsonArray resultList = dataList.get(0).getAsJsonObject().get("Result").getAsJsonArray();
			log.info("resultListSize = {} ", resultList.size());
			for(int i = 0; i < resultList.size(); ++i) {
				if(resultList.get(i).getAsJsonObject().get("prodYear").toString().replaceAll("\"", "").equals(openDt.substring(0, 4))) {
					posterUrl = resultList.get(i).getAsJsonObject().get("posters").toString().replaceAll("\"", "");
					break;
				}
			}
		} catch (Exception e) {
			log.error("Error MovieInfoApiService - getPosterUrl() => {}", e);
		}
		
		if(!posterUrl.equals("")){
			repo.moviePosterUrlUpdate(movieId, posterUrl);
			log.info("MovieInfoApiService - moviePosterUrlUpdate()");
			log.info("=================== success update ===================");
			log.info("{} {} {}", movieId, movieNm, posterUrl);
			log.info("=================== END ===================");
		}else {
			log.info("MovieInfoApiService - moviePosterUrlUpdate()");
			log.info("=================== update failed ===================");
			log.info("{} {} {}", movieId, movieNm, posterUrl);
			log.info("=================== END ===================");
		}
	}
	
	public List<MovieInfoDTO> getAllMovieNotNan(){
		List<MovieInfoDTO> list = repo.getAllMovieNotNan();
		for(int i = 0; i < list.size(); ++i) {
			String tmp = list.get(i).getPosterUrl().split("\\|")[0];
			list.get(i).setPosterUrl(tmp); 
		}
		return list;
	}
	
	public void saveDailyTopInfo(MovieTopInfoDTO dto) {
		repo.saveDailyTopInfo(dto);
	}
	
	public void saveWeeklyTopInfo(MovieTopInfoDTO dto) {
		repo.saveWeeklyTopInfo(dto);
	}
	
	public MovieTopInfoDTO getDailyTopInfoById(int id) {
		return repo.getDailyTopInfoById(id);
	}
	
	public MovieTopInfoDTO getWeeklyTopInfoById(int id) {
		return repo.getWeeklyTopInfoById(id);
	}
}
