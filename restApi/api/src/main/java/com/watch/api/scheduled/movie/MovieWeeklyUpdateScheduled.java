package com.watch.api.scheduled.movie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.watch.api.dto.MovieInfoDTO;
import com.watch.api.dto.MovieTopInfoDTO;
import com.watch.api.repository.MovieInfoApiRepository;
import com.watch.api.service.MovieInfoApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieWeeklyUpdateScheduled {
	private final MovieInfoApiRepository repo;
	private final MovieInfoApiService service;
	
//	@Scheduled(fixedDelay = 1000)
	@Scheduled(cron = "5 0 6 * * *")
	public void WeeklyMovieInsert0() throws UnsupportedEncodingException {
		MovieInfoDTO dto = new MovieInfoDTO();
		MovieTopInfoDTO topDto = new MovieTopInfoDTO();
		int topId = 0;
		String topIds = "";
    	String date = getWeekAgoDate();
    	String movieId = "";
    	
    	log.info("Auto Scheduled WeeklyMovieInsert() 호출");
    	log.info("date => {}", date);
    	
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888");
        urlBuilder.append("&targetDt=" + URLEncoder.encode(date, "UTF-8"));
        urlBuilder.append("&weekGb=0");
        
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
			JsonObject boxOfficeResult = element.getAsJsonObject().get("boxOfficeResult").getAsJsonObject();
			JsonArray weeklyBoxOfficeList = boxOfficeResult.getAsJsonObject().get("weeklyBoxOfficeList").getAsJsonArray();
			log.info("weeklyBoxOfficeList Size => {}", weeklyBoxOfficeList.size());
			
			int cnt = 0;
			for(int i = 0; i < weeklyBoxOfficeList.size(); ++i) {
				movieId = weeklyBoxOfficeList.get(i).getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
				dto = repo.getMovieInfoByMovieId(movieId);
				
				if(i != weeklyBoxOfficeList.size()-1) 
					topIds += "'" + movieId + "'" + ",";					
				else
					topIds += "'" + movieId + "'";
				
				if(dto == null) { // movieId 값이 없으면 실행
					service.saveMovieInfoByMovieId(movieId);
					String movieNm = weeklyBoxOfficeList.get(i).getAsJsonObject().get("movieNm").toString().replaceAll("\"", "");
					cnt++;
					log.info("{} 영화 저장을 완료했습니다.", movieNm);
					log.info("{}개 업데이트 완료", cnt);
				}else {
					log.info("이미 {} 영화가 존재합니다.", dto.getMovieNm());
					log.info("{}개 업데이트 완료", cnt);
				}
			}
			
			topId = Integer.parseInt(date) * 10;
			topDto = service.getWeeklyTopInfoById(topId);
			if(topDto == null) {
				topDto = new MovieTopInfoDTO();
				topDto.setId(topId);
				topDto.setMovieIds(topIds);
				service.saveWeeklyTopInfo(topDto);
				log.info("WeeklyTopInfo0 저장이 완료되었습니다.");
			}else
				log.info("WeeklyTopInfo0 의 정보가 이미 있습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 가져온 api데이터를 통해 movie_id값으로 상세정보 api호출
        log.info("date ==> {}", date);
        log.info("MovieWeeklyUpdateScheduled - WeeklyMovieInsert0()");
		log.info("=================== END ===================");
    }
	
//	@Scheduled(fixedDelay = 1000)
	@Scheduled(cron = "10 0 6 * * *")
	public void WeeklyMovieInsert1() throws UnsupportedEncodingException {
		MovieInfoDTO dto = new MovieInfoDTO();
		MovieTopInfoDTO topDto = new MovieTopInfoDTO();
		int topId = 0;
		String topIds = "";
    	String date = getWeekAgoDate();
    	String movieId = "";
    	log.info("Auto Scheduled WeeklyMovieInsert1() 호출");
    	log.info("date => {}", date);
    	
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888");
        urlBuilder.append("&targetDt=" + URLEncoder.encode(date, "UTF-8"));
        urlBuilder.append("&weekGb=1");
        
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
			JsonObject boxOfficeResult = element.getAsJsonObject().get("boxOfficeResult").getAsJsonObject();
			JsonArray weeklyBoxOfficeList = boxOfficeResult.getAsJsonObject().get("weeklyBoxOfficeList").getAsJsonArray();
			log.info("WeeklyBoxOfficeList Size => {}", weeklyBoxOfficeList.size());
			
			int cnt = 0;
			for(int i = 0; i < weeklyBoxOfficeList.size(); ++i) {
				movieId = weeklyBoxOfficeList.get(i).getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
				dto = repo.getMovieInfoByMovieId(movieId);
				
				if(i != weeklyBoxOfficeList.size()-1) 
					topIds += "'" + movieId + "'" + ",";					
				else
					topIds += "'" + movieId + "'";
				
				if(dto == null) { // movieId 값이 없으면 실행
					service.saveMovieInfoByMovieId(movieId);
					String movieNm = weeklyBoxOfficeList.get(i).getAsJsonObject().get("movieNm").toString().replaceAll("\"", "");
					cnt++;
					log.info("{} 영화 저장을 완료했습니다.", movieNm);
					log.info("{}개 업데이트 완료", cnt);
				}else {
					log.info("이미 {} 영화가 존재합니다.", dto.getMovieNm());
					log.info("{}개 업데이트 완료", cnt);
				}
			}
			
			topId = Integer.parseInt(date) * 10 + 1;
			topDto = service.getWeeklyTopInfoById(topId);
			if(topDto == null) {
				topDto = new MovieTopInfoDTO();
				topDto.setId(topId);
				topDto.setMovieIds(topIds);
				service.saveWeeklyTopInfo(topDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 가져온 api데이터를 통해 movie_id값으로 상세정보 api호출
        log.info("date ==> {}", date);
        log.info("MovieWeeklyUpdateScheduled - WeeklyMovieInsert1()");
		log.info("=================== END ===================");
    }
	
//	@Scheduled(fixedDelay = 1000)
	@Scheduled(cron = "15 0 6 * * *")
	public void WeeklyMovieInsert2() throws UnsupportedEncodingException {
		MovieInfoDTO dto = new MovieInfoDTO();
		MovieTopInfoDTO topDto = new MovieTopInfoDTO();
		int topId = 0;
		String topIds = "";
    	String date = getWeekAgoDate();
    	String movieId = "";
    	log.info("Auto Scheduled WeeklyMovieInsert2() 호출");
    	log.info("date => {}", date);
    	
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888");
        urlBuilder.append("&targetDt=" + URLEncoder.encode(date, "UTF-8"));
        urlBuilder.append("&weekGb=2");
        
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
			JsonObject boxOfficeResult = element.getAsJsonObject().get("boxOfficeResult").getAsJsonObject();
			JsonArray weeklyBoxOfficeList = boxOfficeResult.getAsJsonObject().get("weeklyBoxOfficeList").getAsJsonArray();
			log.info("WeeklyBoxOfficeList Size => {}", weeklyBoxOfficeList.size());
			
			int cnt = 0;
			for(int i = 0; i < weeklyBoxOfficeList.size(); ++i) {
				movieId = weeklyBoxOfficeList.get(i).getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
				dto = repo.getMovieInfoByMovieId(movieId);
				
				if(i != weeklyBoxOfficeList.size()-1) 
					topIds += "'" + movieId + "'" + ",";					
				else
					topIds += "'" + movieId + "'";
				
				if(dto == null) { // movieId 값이 없으면 실행
					service.saveMovieInfoByMovieId(movieId);
					String movieNm = weeklyBoxOfficeList.get(i).getAsJsonObject().get("movieNm").toString().replaceAll("\"", "");
					cnt++;
					log.info("{} 영화 저장을 완료했습니다.", movieNm);
					log.info("{}개 업데이트 완료", cnt);
				}else {
					log.info("이미 {} 영화가 존재합니다.", dto.getMovieNm());
					log.info("{}개 업데이트 완료", cnt);
				}
			}
			
			topId = Integer.parseInt(date) * 10 + 2;
			topDto = service.getWeeklyTopInfoById(topId);
			if(topDto == null) {
				topDto = new MovieTopInfoDTO();
				topDto.setId(topId);
				topDto.setMovieIds(topIds);
				service.saveWeeklyTopInfo(topDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 가져온 api데이터를 통해 movie_id값으로 상세정보 api호출
        log.info("date ==> {}", date);
        log.info("MovieWeeklyUpdateScheduled - WeeklyMovieInsert2()");
		log.info("=================== END ===================");
    }

	private String getWeekAgoDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		String date = dtFormat.format(cal.getTime());
		return date;
	}
}
