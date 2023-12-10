package com.watch.api.scheduled.people;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class PeopleInfoUpdateScheduled {
	private final PeopleInfoApiService service;
	private static int cnt = 0;
	
//	@Scheduled(fixedDelay = 1000)
//	@Scheduled(cron = "0 47 14 * * *")
	public void peopleDetailSearchAndSave() {
		int peopleCd = 0;
		List<PeopleInfoDTO> list = service.getPeopleInfoAll();
		while(cnt < 50) {
			log.info("=================== START ===================");
			log.info("PeopleInfoUpdateScheduled - peopleDetailSearchAndSave()");
			peopleCd = list.get(cnt).getPeopleId();
			System.out.println("peopleCd ==> " + peopleCd);
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("http://kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleInfo.json?key=f5eef3421c602c6cb7ea224104795888&peopleCd=" + peopleCd);
			
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
				JsonObject peopleInfoResult = element.getAsJsonObject().get("peopleInfoResult").getAsJsonObject();
				JsonObject peopleInfo = peopleInfoResult.getAsJsonObject().get("peopleInfo").getAsJsonObject();
				JsonArray filmos = peopleInfo.getAsJsonObject().get("filmos").getAsJsonArray();
				
				int peopleId = peopleInfo.getAsJsonObject().get("peopleCd").getAsInt();
				PeopleInfoDetailDTO check = service.getPeopleInfoDetailById(peopleId);
				if(check != null) {
					log.info("PeopleInfoUpdateScheduled - peopleDetailSearchAndSave()");
					log.info("=================== END ===================");
					log.info("{}번째 페이지 정보가 이미 존재합니다!", ++cnt);
					continue;
				}
				String peopleNm = peopleInfo.getAsJsonObject().get("peopleNm").toString().replaceAll("\"", "");
				String peopleNmEn = peopleInfo.getAsJsonObject().get("peopleNmEn").toString().replaceAll("\"", "");
				String sex = peopleInfo.getAsJsonObject().get("sex").toString().replaceAll("\"", "");
				String movieId = "";
				String movieNm = "";
				for(int i = 0; i < filmos.size(); ++i) {
					if(i != filmos.size()-1) {
						movieId += filmos.get(i).getAsJsonObject().get("movieCd").toString().replaceAll("\"", "") + ",";
						movieNm += filmos.get(i).getAsJsonObject().get("movieNm").toString().replaceAll("\"", "") + ",";						
					}else {
						movieId += filmos.get(i).getAsJsonObject().get("movieCd").toString().replaceAll("\"", "");
						movieNm += filmos.get(i).getAsJsonObject().get("movieNm").toString().replaceAll("\"", "");						
					}
				}
				
				if(peopleNm == null || peopleNm.equals("")) {
					peopleNm = "nan";
				}
				if(peopleNmEn == null || peopleNmEn.equals("")) { 
					peopleNmEn = "nan";
				}
				if(sex == null || sex.equals("")) {
					sex = "nan";
				}
				if(movieId == null || movieId.equals("")) {
					movieId = "nan";
				}
				if(movieNm == null || movieNm.equals("")) {
					movieNm = "nan";
				}
				
				PeopleInfoDetailDTO dto = new PeopleInfoDetailDTO();
				dto.setMovieId(movieId);
				dto.setMovieNm(movieNm);
				dto.setPeopleId(peopleId);
				dto.setPeopleNm(peopleNm);
				dto.setPeopleNmEn(peopleNmEn);
				dto.setSex(sex);
				service.savePeopleInfoDetail(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			log.info("PeopleInfoUpdateScheduled - peopleDetailSearchAndSave()");
			log.info("=================== END ===================");
			log.info("{}번째 페이지 업데이트 완료!", ++cnt);
		}
	}
	
//	@Scheduled(fixedDelay = 1000)
//	@Scheduled(cron = "20 43 14 * * *")
	public void peopleAllSearchAndSave() {
		int cnt = 0;
		while(cnt != 20) {
			log.info("=================== START ===================");
			log.info("PeopleInfoUpdateScheduled - peopleAllSearchAndSave()");
			
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("http://kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleList.json?key=f5eef3421c602c6cb7ea224104795888&curPage=" + cnt);
			
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
				JsonObject peopleListResult = element.getAsJsonObject().get("peopleListResult").getAsJsonObject();
				JsonArray peopleList = peopleListResult.getAsJsonObject().get("peopleList").getAsJsonArray();
				for(int i = 0; i < peopleList.size(); ++i) {
					String repRoleNm = peopleList.get(i).getAsJsonObject().get("repRoleNm").toString().replaceAll("\"", "");
					if(repRoleNm.equals("배우")) {
						int peopleId = peopleList.get(i).getAsJsonObject().get("peopleCd").getAsInt();
						int check = service.getPeopleInfoById(peopleId);
						
						if(check == 1) {
							continue;
						}
						
						String peopleNm = peopleList.get(i).getAsJsonObject().get("peopleNm").toString().replaceAll("\"", "");
						String peopleNmEn = peopleList.get(i).getAsJsonObject().get("peopleNmEn").toString().replaceAll("\"", "");
						String filmoNames = peopleList.get(i).getAsJsonObject().get("filmoNames").toString().replaceAll("\"", "");
						
						if(peopleNm == null || peopleNm.equals("")) {
							peopleNm = "nan";
						}
						if(peopleNmEn == null || peopleNmEn.equals("")) {
							peopleNmEn = "nan";
						}
						if(filmoNames == null || filmoNames.equals("")) {
							filmoNames = "nan";
						}
						
						PeopleInfoDTO dto = new PeopleInfoDTO();
						dto.setPeopleId(peopleId);
						dto.setPeopleNm(peopleNm);
						dto.setPeopleNmEn(peopleNmEn);
						dto.setFilmoNames(filmoNames);
						
						service.peopleInfoSave(dto);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			log.info("PeopleInfoUpdateScheduled - peopleAllSearchAndSave()");
			log.info("=================== END ===================");
			log.info("{}번째 페이지 업데이트 완료!", ++cnt);
		}
	}
}
