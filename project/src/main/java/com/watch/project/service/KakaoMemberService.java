package com.watch.project.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.watch.project.dto.MemberDTO;
import com.watch.project.repository.MemberRepository;

@Service
public class KakaoMemberService {
	@Autowired
	private MemberRepository repo;
	
	@Autowired
	private HttpSession session;

	public String getAccessToken(String code) {
		String accessToken = "";
		String refreshToken = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);// OutputStream으로 POST 데이터를 넘겨주겠다는 옵션
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=36b59ada5e8b70c6afae51b77c038484");
			sb.append("&redirect_uri=http://localhost:8080/signIn/kakao");
			sb.append("&code="+code);
			
			bw.write(sb.toString());
			bw.flush();
			
			int responseCode = conn.getResponseCode();
			System.out.println("response code = " + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = "";
			String result = "";
			while((line = br.readLine())!=null) {
				result += line;
			}
			System.out.println("response body="+result);
			
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			
			accessToken = element.getAsJsonObject().get("access_token").getAsString();
			refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();
			
			br.close();
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}
	
	

	public MemberDTO getUserInfo(String accessToken) throws Exception {
		MemberDTO kakaoInput = null;
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String reqUrl = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode =" + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = "";
			String result = "";
			
			while((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body ="+result);
			
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			System.out.println(element);
			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
			String member_id = element.getAsJsonObject().get("id").getAsString();
			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
			
			System.out.println("kakao id : " + member_id);
			userInfo.put("nickname", nickname);
			userInfo.put("email", email);
			System.out.println("name" + nickname);
			System.out.println("email" + email);
			kakaoInput = new MemberDTO();
			kakaoInput.setUserId(member_id);
			kakaoInput.setUserName(nickname);
			kakaoInput.setUserEmail(email);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kakaoInput;
	}

	
	public MemberDTO getMember(String member_id) {
		MemberDTO member = repo.getUserInfoById(member_id);
		return member;
	}



	public String getJoinMsg(MemberDTO input) {
//		if(input.getMember_birth()==0) {
//			return "birthday 입력하세요";
//		}
//		if(input.getMember_phone_num()==null||input.getMember_phone_num().equals("")) {
//			return "phone_num 확인 좀 ;;";
//		}
//		if(input.getMember_zip_code()==null || input.getMember_zip_code().equals("")) {
//			return "check zip code";
//		}
//		if(input.getMember_address()==null || input.getMember_address().equals("")) {
//			return "check address";
//		}
//		if(input.getMember_address_detail()==null || input.getMember_address_detail().equals("")) {
//			return "check detail address";
//		}
//		
		session.setAttribute("userId", input.getUserId());
		session.setAttribute("loginType", 2);
//		MemberDTO kakaoSet = new MemberDTO();
//		kakaoSet.setuS
		
		repo.saveMemberInfo(input);
		return "가입완료";
	
	}


}
