package com.watch.api.scheduled.people;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.watch.api.dto.PeopleInfoDTO;
import com.watch.api.dto.PeopleInfoDetailDTO;
import com.watch.api.service.PeopleInfoApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
@RequiredArgsConstructor
@Slf4j
public class PeopleProfileUpdateScheduled {
	private static int cnt = 0;
	private final PeopleInfoApiService service;
	
	/*
	 * 결과가 1개만 있을 경우
	 */
	@Scheduled(cron = "5 12 14 * * *")
	public void peopleDetailSearchAndSave() throws UnsupportedEncodingException {
		List<PeopleInfoDetailDTO> peopleInfoDetailList = service.getAllPeopleInfoDetail();
		while(cnt != peopleInfoDetailList.size()) {
			String peopleNm = peopleInfoDetailList.get(cnt).getPeopleNm();
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("http://api.themoviedb.org/3/search/person?");
			urlBuilder.append("query=" + URLEncoder.encode(peopleNm, "UTF-8"));
			urlBuilder.append("&include_adult=false");
			urlBuilder.append("&language=ko-KR");
			urlBuilder.append("&page=1");
			urlBuilder.append("&api_key=102e55491572059f51bc45c7d1b03494");
			
			try {
				URL url = new URL(urlBuilder.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				log.info("------------------- REST API 호출 -------------------");
				log.info("{}", urlBuilder.toString());
				int responseCode = conn.getResponseCode();
				log.info("responseCode = {}", responseCode);
				
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				String line = "";
				StringBuilder sb = new StringBuilder();
				
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				
				log.info("response body = {}", sb.toString());
				conn.disconnect();
				br.close();

				JsonParser parser = new JsonParser();
				JsonElement element =  parser.parse(sb.toString());
				JsonArray results = element.getAsJsonObject().get("results").getAsJsonArray();
				
				if(results.size() == 1) {
					String profilePath = results.get(0).getAsJsonObject().get("profile_path").toString().replaceAll("\"", "");
					String profileUrl = "https://image.tmdb.org/t/p/w500" + profilePath;
					peopleInfoDetailList.get(cnt).setProfileUrl(profileUrl);
					service.updateProfileUrlByPeopleId(peopleInfoDetailList.get(cnt));
					cnt++;
					log.info("{}번째 업데이트 완료(성공)", cnt);
				}else {
					cnt++;
					log.info("{}번째 업데이트 완료(실패)", cnt);
					continue;
				}
			} catch (Exception e) {
				cnt++;
				log.info("Exception {}번째 업데이트 완료(실패)", cnt);
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 여러 결과가 있을 경우 DAO에 WHERE profile_url='nan' 추가
	 */
	@Scheduled(cron = "5 12 14 * * *")
	public void peopleDetailSearchAndSave2() throws UnsupportedEncodingException {
		List<PeopleInfoDetailDTO> peopleInfoDetailList = service.getAllPeopleInfoDetail();
		while(cnt != peopleInfoDetailList.size()) {
			String peopleNm = peopleInfoDetailList.get(cnt).getPeopleNm();
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("http://api.themoviedb.org/3/search/person?");
			urlBuilder.append("query=" + URLEncoder.encode(peopleNm, "UTF-8"));
			urlBuilder.append("&include_adult=false");
			urlBuilder.append("&language=ko-KR");
			urlBuilder.append("&page=1");
			urlBuilder.append("&api_key=102e55491572059f51bc45c7d1b03494");
			
			try {
				URL url = new URL(urlBuilder.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				log.info("------------------- REST API 호출 -------------------");
				log.info("{}", urlBuilder.toString());
				int responseCode = conn.getResponseCode();
				log.info("responseCode = {}", responseCode);
				
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				String line = "";
				StringBuilder sb = new StringBuilder();
				
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				
				log.info("response body = {}", sb.toString());
				conn.disconnect();
				br.close();

				JsonParser parser = new JsonParser();
				JsonElement element =  parser.parse(sb.toString());
				JsonArray results = element.getAsJsonObject().get("results").getAsJsonArray();
				
				if(results.size() == 1) {
					String profilePath = results.get(0).getAsJsonObject().get("profile_path").toString().replaceAll("\"", "");
					String profileUrl = "https://image.tmdb.org/t/p/w500" + profilePath;
					peopleInfoDetailList.get(cnt).setProfileUrl(profileUrl);
					service.updateProfileUrlByPeopleId(peopleInfoDetailList.get(cnt));
					cnt++;
					log.info("{}번째 업데이트 완료(성공)", cnt);
				}else {
					cnt++;
					log.info("{}번째 업데이트 완료(실패)", cnt);
					continue;
				}
			} catch (Exception e) {
				cnt++;
				log.info("Exception {}번째 업데이트 완료(실패)", cnt);
				e.printStackTrace();
			}
		}
	}
}
