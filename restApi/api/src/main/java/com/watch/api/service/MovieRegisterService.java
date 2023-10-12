package com.watch.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.watch.api.dto.MovieInfoDTO;

@Service
public class MovieRegisterService {
	
	public void getMovieInfoByMovieName() {
		String movieCd = "";
		String reqUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888&movieNm=마음이2";
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
			JsonObject movieListResult = element.getAsJsonObject().get("movieListResult").getAsJsonObject();
			JsonArray movieList = movieListResult.getAsJsonObject().get("movieList").getAsJsonArray();
			movieCd = movieList.get(0).getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonObject movieInfo = getMovieDetailInfo(movieCd);
		if(movieInfo != null) {
			MovieInfoDTO dto = setMovieInfoDto(movieInfo);
			
		}
	}
	
	private MovieInfoDTO setMovieInfoDto(JsonObject movieInfo) {
		String movieId = movieInfo.getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
		String movieNm = movieInfo.getAsJsonObject().get("movieNm").toString().replaceAll("\"", "");
		String movieNmEn = movieInfo.getAsJsonObject().get("movieNmEn").toString().replaceAll("\"", "");
		String prdtYear = movieInfo.getAsJsonObject().get("prdtYear").toString().replaceAll("\"", "");
		String openDt = movieInfo.getAsJsonObject().get("openDt").toString().replaceAll("\"", "");
		String typeNm = movieInfo.getAsJsonObject().get("typeNm").toString().replaceAll("\"", "");
		int showTime = Integer.parseInt(movieInfo.getAsJsonObject().get("showTm").toString().replaceAll("\"", ""));
		String nations = "";
		String genreNm = "";
		String actors = "";
		String cast = "";
		String watchGradeNm = "";
		JsonArray nationsList = movieInfo.getAsJsonObject().get("nations").getAsJsonArray();
		JsonArray genresList = movieInfo.getAsJsonObject().get("genres").getAsJsonArray();
		JsonArray actorsList = movieInfo.getAsJsonObject().get("actors").getAsJsonArray();
		JsonArray auditsList = movieInfo.getAsJsonObject().get("audits").getAsJsonArray();
		for(int i = 0; i < nationsList.size(); ++i) {
			if(i != nationsList.size()-1) {
				nations += nationsList.get(i).getAsJsonObject().get("nationNm").toString().replaceAll("\"", "") + ",";				
			}else
				nations += nationsList.get(i).getAsJsonObject().get("nationNm").toString().replaceAll("\"", "");
		}
		System.out.println("nations ==> " + nations);
		for(int i = 0; i < genresList.size(); ++i) {
			if(i != genresList.size()-1) {
				genreNm += genresList.get(i).getAsJsonObject().get("genreNm").toString().replaceAll("\"", "") + ",";
			}else
				genreNm += genresList.get(i).getAsJsonObject().get("genreNm").toString().replaceAll("\"", "");
		}
		for(int i = 0; i < actorsList.size(); ++i) {
			if(i != actorsList.size()-1) {
				actors += actorsList.get(i).getAsJsonObject().get("peopleNm").toString().replaceAll("\"", "") + ",";
				cast += actorsList.get(i).getAsJsonObject().get("cast").toString().replaceAll("\"", "") + ",";
			}else {
				actors += actorsList.get(i).getAsJsonObject().get("peopleNm").toString().replaceAll("\"", "");
				cast += actorsList.get(i).getAsJsonObject().get("cast").toString().replaceAll("\"", "");
			}
		}
		for(int i = 0; i < auditsList.size(); ++i) {
			if(i != auditsList.size()-1) 
				watchGradeNm += auditsList.get(i).getAsJsonObject().get("watchGradeNm").toString().replaceAll("\"", "") + ",";
			else
				watchGradeNm += auditsList.get(i).getAsJsonObject().get("watchGradeNm").toString().replaceAll("\"", "");
		}
		
		try {
			String posterUrl = getPosterUrl(movieNm, openDt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MovieInfoDTO dto = new MovieInfoDTO();
		dto.setMovieId(movieId);
		dto.setMovieNm(movieNm);
		dto.setMovieNmEn(movieNmEn);
		dto.setPrdtYear(prdtYear);
		dto.setOpenDt(openDt);
		dto.setTypeNm(typeNm);
		dto.setNations(nations);
		dto.setGenreNm(genreNm);
		dto.setShowTime(showTime);
		dto.setActors(actors);
		dto.setCast(cast);
		dto.setWatchGradeNm(watchGradeNm);
		return dto;
	}

	private String getPosterUrl(String movieNm, String openDt) throws IOException {                                                                       

		String reqUrl = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=0TN0PQIFW51T18N3L053&title=마음이2";
		System.out.println(reqUrl);
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("??");
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
			System.out.println("response body ="+sb.toString());
			
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private JsonObject getMovieDetailInfo(String movieCd) {
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
		return movieInfo;
	}
}
