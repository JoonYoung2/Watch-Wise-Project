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
public class MovieDailyUpdateScheduled {
	private final MovieInfoApiRepository repo;
	private final MovieInfoApiService service;
	private static int cnt = 1;
	
//	@Scheduled(fixedDelay = 1000)
	@Scheduled(cron = "0 37 14 * * *")
	public void DailyMovieInsert() throws UnsupportedEncodingException {
		MovieInfoDTO dto = new MovieInfoDTO();
		MovieTopInfoDTO topDto = new MovieTopInfoDTO();
		int topId = 0;
		String topIds = "";
    	String date = getYesterdayDate();
    	String movieId = "";
    	log.info("Auto Scheduled DailyMovieInsert() 호출");
    	log.info("date => {}", date);
    	
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888");
        urlBuilder.append("&targetDt=" + URLEncoder.encode(date, "UTF-8"));
        
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
			JsonArray dailyBoxOfficeList = boxOfficeResult.getAsJsonObject().get("dailyBoxOfficeList").getAsJsonArray();
			log.info("dailyBoxOfficeList Size => {}", dailyBoxOfficeList.size());
			int cnt = 0;
			for(int i = 0; i < dailyBoxOfficeList.size(); ++i) {
				movieId = dailyBoxOfficeList.get(i).getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
				dto = repo.getMovieInfoByMovieId(movieId);
				if(i != dailyBoxOfficeList.size() - 1) 
					topIds += "'" + movieId + "'" + ",";
				else
					topIds += "'" + movieId + "'";
				
				if(dto == null) { // movieId 값이 없으면 실행
					service.saveMovieInfoByMovieId(movieId);
					cnt++;
					String movieNm = dailyBoxOfficeList.get(i).getAsJsonObject().get("movieNm").toString().replaceAll("\"", "");
					log.info("{} 영화 저장을 완료했습니다.", movieNm);
					log.info("{}개 업데이트 완료", cnt);
				}else {
					log.info("이미 {} 영화가 존재합니다.", dto.getMovieNm());
					log.info("{}개 업데이트 완료", cnt);
				}
			}
			topId = Integer.parseInt(date);
			topDto = service.getDailyTopInfoById(topId);
			if(topDto == null) {
				topDto = new MovieTopInfoDTO();
				topDto.setId(topId);
				topDto.setMovieIds(topIds);
				service.saveDailyTopInfo(topDto);
				log.info("Daily Top Info 저장을 완료했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 가져온 api데이터를 통해 movie_id값으로 상세정보 api호출
        log.info("date ==> {}", date);
        log.info("MovieDailyUpdateScheduled - DailyMovieInsert()");
		log.info("=================== END ===================");
    }
	
//	@Scheduled(fixedDelay = 1000)
	@Scheduled(cron = "25 37 14 * * *")
	public void allFindAndInsert() {
		while(cnt <= 127) {
			MovieInfoDTO dto = new MovieInfoDTO();

	    	String movieId = "";
	    	log.info("Auto Scheduled allFindAndInsert() 호출");
	    	
	    	StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888");
			urlBuilder.append("&curPage=" + cnt);
			urlBuilder.append("&openStartDt=2023");
//			urlBuilder.append("&openEndDt=2100");
	        
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
				JsonObject movieListResult = element.getAsJsonObject().get("movieListResult").getAsJsonObject();
				JsonArray movieList = movieListResult.getAsJsonObject().get("movieList").getAsJsonArray();
				log.info("movieList Size => {}", movieList.size());
				int cnt = 0;
				for(int i = 0; i < movieList.size(); ++i) {
					movieId = movieList.get(i).getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
					dto = repo.getMovieInfoByMovieId(movieId);
					
					if(dto == null) { // movieId 값이 없으면 실행
						service.saveMovieInfoByMovieId(movieId);
						cnt++;
						String movieNm = movieList.get(i).getAsJsonObject().get("movieNm").toString().replaceAll("\"", "");
						log.info("{} 영화 저장을 완료했습니다.", movieNm);
						log.info("{}개 업데이트 완료", cnt);
					}else {
						log.info("이미 {} 영화가 존재합니다.", dto.getMovieNm());
						log.info("{}개 업데이트 완료", cnt);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        log.info("{}번째 페이지 업뎃 완료!", cnt++);
	        log.info("MovieDailyUpdateScheduled - allFindAndInsert()");
			log.info("=================== END ===================");
		}
	}

	private String getYesterdayDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		String date = dtFormat.format(cal.getTime()); // 어제 날짜
		return date;
	}
}
