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

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.watch.api.dto.MovieInfoDTO;
import com.watch.api.dto.MovieTopInfoDTO;
import com.watch.api.repository.MovieInfoApiRepository;
import com.watch.api.repository.PosterResearchApiRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PosterResearchApiService {
	private final PosterResearchApiRepository repo;
	public List<MovieInfoDTO> getPosterNan() {
		return repo.getPosterNan();
	}
	
	public void posterResearch() throws UnsupportedEncodingException {
		StringBuilder urlBuilder = new StringBuilder();
		
		urlBuilder.append("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&listCount=500");
		urlBuilder.append("&title=" + URLEncoder.encode("마음이2", "UTF-8"));
		
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
		}catch(Exception e) {
			log.error("Error PosterResearchApiService - posterResearch() {} ", e);
		}
		
		
	}
	
	public int posterResearch(String movieId, String movieNm, String openDt) throws UnsupportedEncodingException {
		StringBuilder urlBuilder = new StringBuilder();
		String posterUrl = "";
		openDt = openDt.substring(0, 6);
		urlBuilder.append("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&listCount=500");
		urlBuilder.append("&title=" + URLEncoder.encode(movieNm, "UTF-8"));
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
				log.info("PosterResearchApiService - posterResearch()");
				log.info("=================== END ===================");
				return 0;
			}
			
			result = sb.toString().replaceAll(" !HS ", "");
			result = result.replaceAll(" !HE ", "");
			int resultLength = result.length();
			log.info("result {} {} = {} ",resultLength ,movieNm ,result);
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			int totalCount = element.getAsJsonObject().get("TotalCount").getAsInt();
			if(totalCount == 0) {
				log.info("posterUrl 얻기 실패!");
				log.info("PosterResearchApiService - posterResearch()");
				log.info("=================== END ===================");
				return 0;
			}

			JsonArray dataList = element.getAsJsonObject().get("Data").getAsJsonArray();
			JsonArray resultList = dataList.get(0).getAsJsonObject().get("Result").getAsJsonArray();
			log.info("resultListSize = {} ", resultList.size());
			for(int i = 0; i < resultList.size(); ++i) {
				String repRlsDate = resultList.get(i).getAsJsonObject().get("repRlsDate").toString().replaceAll("\"", "");
				if(repRlsDate.length() == 8) {
					if(repRlsDate.substring(0, 6).equals(openDt)) {
						posterUrl = resultList.get(i).getAsJsonObject().get("posters").toString().replaceAll("\"", "");
						log.info("PosterResearchApiService - posterResearch() - posterUrl 엳기 성공!!");
						break;
					}					
				}
			}
		} catch (Exception e) {
			log.error("Error PosterResearchApiService - posterResearch() => {}", e);
		}
		
		if(!posterUrl.equals("")) {
			Map<String, String> map = new HashMap<>();
			map.put("movieId", movieId);
			map.put("posterUrl", posterUrl);
			repo.posterUpdate(map);
			log.info("{} 영화 포스터 업데이트 완료!", movieNm);
			log.info("PosterResearchApiService - posterResearch()");
			log.info("=================== END ===================");
			return 1;
		}
		
		log.info("PosterResearchApiService - posterResearch()");
		log.info("=================== END ===================");
		return 0;
	}
	
	public int posterResearch(String movieId, String movieNm, String openDt, String prdtYear) throws UnsupportedEncodingException {
		StringBuilder urlBuilder = new StringBuilder();
		String posterUrl = "";
		movieNm = movieNm.replaceAll(" ", "");
		openDt = openDt.substring(0, 6);
		urlBuilder.append("https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&listCount=500");
		urlBuilder.append("&title=" + URLEncoder.encode(movieNm, "UTF-8"));
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
				log.info("PosterResearchApiService - posterResearch()");
				log.info("=================== END ===================");
				return 0;
			}
			
			result = sb.toString().replaceAll(" !HS ", "");
			result = result.replaceAll(" !HE ", "");
			int resultLength = result.length();
			log.info("result {} {} = {} ",resultLength ,movieNm ,result);
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			int totalCount = element.getAsJsonObject().get("TotalCount").getAsInt();
			if(totalCount == 0) {
				log.info("posterUrl 얻기 실패!");
				log.info("PosterResearchApiService - posterResearch()");
				log.info("=================== END ===================");
				return 0;
			}

			JsonArray dataList = element.getAsJsonObject().get("Data").getAsJsonArray();
			JsonArray resultList = dataList.get(0).getAsJsonObject().get("Result").getAsJsonArray();
			log.info("resultListSize = {} ", resultList.size());
			for(int i = 0; i < resultList.size(); ++i) {
				String prodYear = resultList.get(i).getAsJsonObject().get("prodYear").toString().replaceAll("\"", "");
				String repRlsDate = resultList.get(i).getAsJsonObject().get("repRlsDate").toString().replaceAll("\"", "");
				if(repRlsDate.length() == 8) {
					if(!prdtYear.equals("nan")) {
						if(repRlsDate.substring(0, 6).equals(openDt) && prodYear.equals(prdtYear)) {
							posterUrl = resultList.get(i).getAsJsonObject().get("posters").toString().replaceAll("\"", "");
							log.info("PosterResearchApiService - posterResearch() - posterUrl 엳기 성공!!");
							break;
						}
					}else {
						if(repRlsDate.substring(0, 6).equals(openDt)) {
							posterUrl = resultList.get(i).getAsJsonObject().get("posters").toString().replaceAll("\"", "");
							log.info("PosterResearchApiService - posterResearch() - posterUrl 엳기 성공!!");
							break;
						}
					}
										
				}
			}
		} catch (Exception e) {
			log.error("Error PosterResearchApiService - posterResearch() => {}", e);
		}
		
		if(!posterUrl.equals("")) {
			Map<String, String> map = new HashMap<>();
			map.put("movieId", movieId);
			map.put("posterUrl", posterUrl);
			repo.posterUpdate(map);
			log.info("{} 영화 포스터 업데이트 완료!", movieNm);
			log.info("PosterResearchApiService - posterResearch()");
			log.info("=================== END ===================");
			return 1;
		}
		
		log.info("PosterResearchApiService - posterResearch()");
		log.info("=================== END ===================");
		return 0;
	}
}
