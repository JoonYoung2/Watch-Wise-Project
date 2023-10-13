package com.watch.api.scheduled;

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
import com.watch.api.repository.MovieInfoApiRepository;
import com.watch.api.service.MovieInfoApiService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovieUpdateScheduled {
	private static int cnt = 1;
	private final MovieInfoApiRepository repo;
	private final MovieInfoApiService service;
	
	@Scheduled(fixedDelay = 1000)
	public void DailyMovieUpdate() {
		MovieInfoDTO dto = new MovieInfoDTO();
    	
    	String date = getYesterdayDate(); // 어제 날짜 가져오기
//    	String date = "20051012";
    	System.out.println("date ==> " + date);
    	if(date.equals("20231013"))
			return;
    	String movieId = "";
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888");
        try {
			urlBuilder.append("&targetDt=" + URLEncoder.encode(date, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode =" + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = "";
			StringBuilder sb = new StringBuilder();
			
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			System.out.println("response body ="+sb.toString());
			conn.disconnect();
			br.close();
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(sb.toString());
			JsonObject boxOfficeResult = element.getAsJsonObject().get("boxOfficeResult").getAsJsonObject();
			JsonArray dailyBoxOfficeList = boxOfficeResult.getAsJsonObject().get("dailyBoxOfficeList").getAsJsonArray();
			System.out.println(dailyBoxOfficeList.size());
			for(int i = 0; i < dailyBoxOfficeList.size(); ++i) {
				movieId = dailyBoxOfficeList.get(i).getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
				System.out.println(movieId);
				dto = repo.getMovieInfoByMovieId(movieId);
				System.out.println(dto);
				if(dto == null) {
					service.saveMovieInfoByMovieId(movieId);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 가져온 api데이터를 통해 movie_id값으로 상세정보 api호출
        System.out.println("date ==> " + date);
		System.out.println("MovieInfoApiService - MovieUpdateQuartz()");
		System.out.println("=================== END ===================");
		DailyMovieUpdate();
        
    }

	private String getYesterdayDate() {
		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
		cal.set(2020, 7, 20);
		cal.add(Calendar.DATE, cnt);
		cnt++;
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		String date = dtFormat.format(cal.getTime()); // 어제 날짜
		System.out.println("Date ==> "+date);
		return date;
	}
}
