package com.watch.project.service.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.watch.project.controller.MailController;
import com.watch.project.dto.MemberDTO;
import com.watch.project.repository.MemberRepository;
import com.watch.project.service.MailService;

@Service
public class KakaoMemberService {
	@Autowired
	private MemberRepository repo;
	@Autowired
	private LocalMemberService localMemberService;
	@Autowired
	private MailService mailService;
	@Autowired
	private HttpSession session;

	public String getAccessToken(String code) {
		String accessToken = "";
		String refreshToken = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();//HttpURLConnection 객체 생성
			conn.setRequestMethod("POST"); //http 요청 설정
			conn.setDoOutput(true);// OutputStream으로 POST 데이터를 넘겨주겠다는 옵션
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=36b59ada5e8b70c6afae51b77c038484");
			sb.append("&redirect_uri=http://localhost:8080/signIn/kakao");
			sb.append("&code="+code);
			
			bw.write(sb.toString());
			bw.flush();
			
			int responseCode = conn.getResponseCode();// 응답 코드 확인 (200은 성공을 의미)
			System.out.println("response code = " + responseCode);
			
			//응답 데이터 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = "";
			String result = "";
			while((line = br.readLine())!=null) {
				result += line;
			}
			System.out.println("response body="+result);
			

//			JsonElement element = JsonParser.parseString(result);
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);	
			
			accessToken = element.getAsJsonObject().get("access_token").getAsString();
			refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();
			System.out.println("refreshToken ===>"+refreshToken);
			session.setAttribute("refreshToken", refreshToken);
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
			
//			JsonElement element = JsonParser.parseString(result);
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			
			System.out.println(element);
			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
			String member_id = element.getAsJsonObject().get("id").getAsString();
			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
//			boolean email_needs_agreement = kakaoAccount.getAsJsonObject().get("email_needs_agreement").getAsBoolean();
//			System.out.println("email_needs_agreement============>"+email_needs_agreement);
			kakaoInput = new MemberDTO();
//			if(!email_needs_agreement) {//이메일 제공 동의 했으면
				String email = kakaoAccount.getAsJsonObject().get("email").getAsString();				
				userInfo.put("email", email);
				System.out.println("email" + email);
				kakaoInput.setUserEmail(email);
//				session.setAttribute("email_needs", 0);
//			}else {
//				session.setAttribute("email_needs", 1);
//			}
			
			System.out.println("kakao id : " + member_id);
			userInfo.put("nickname", nickname);
			System.out.println("name" + nickname);
			kakaoInput.setSocialLoginId(member_id);
			kakaoInput.setUserName(nickname);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kakaoInput;
	}

	
	public MemberDTO getMember(String socialId) {
		MemberDTO member = repo.getUserInfoBySocialId(socialId);
		return member;
	}



	public String getMsgOrSend(MemberDTO input) {
		System.out.println("service 단의 getJoinMsg 까지는 옴. input.get userEmail은 다음 줄에");
		String email = input.getUserEmail();
		mailService.issueCh(email);
		if(email==null || email.equals("")) {
			return "이메일을 입력하세요";
		}else if(localMemberService.existingEmailCh(email)==1) {//존재하는 이메일이면
			return "이미 가입된 회원의 이메일입니다. 로그인을 진행해주세요.";
		}else {
			mailService.send3(email);
			return null;
//			session.setAttribute("userEmail", input.getUserEmail());
//			session.setAttribute("userLoginType", 2);
//			
//			repo.saveMemberInfo(input);
//			return "가입완료";
		}
	}



	public void register(MemberDTO dto) {
		session.setAttribute("userLoginType", 2);
		
		repo.saveMemberInfo(dto);
	}



	public void kakaoRevokeAgreement(String accessToken) {
        try {
            String endpoint = "https://kapi.kakao.com/v1/user/unlink";

            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // HTTP POST 메서드 설정
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            // 요청 본문 설정 (없을 경우 생략 가능)
            // 만약 요청 본문이 필요하다면, 데이터를 설정하고 요청 본문을 전송합니다.
            String requestBody = ""; // 요청 본문 데이터
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);
            writer.writeBytes(requestBody);
            writer.flush();
            writer.close();
            os.close();

            // 응답 코드 확인 (200은 성공을 나타냅니다)
            int responseCode = connection.getResponseCode();
            System.out.println("HTTP 응답 코드: " + responseCode);

            // 응답 읽기 (생략 가능)
            // 요청에 대한 응답 데이터를 읽어올 수 있습니다.

            // 연결 닫기
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}



	public String getAccessTokenByRefreshToken(MemberDTO dto) {
		String accessToken = null;
	    // Kakao API 엔드포인트 및 API 키 설정
	    String apiUrl = "https://kauth.kakao.com/oauth/token";
	    String restApiKey = "36b59ada5e8b70c6afae51b77c038484"; // 여기에 Kakao API의 REST API 키를 설정
	    String userRefreshToken = dto.getKakaoRefreshToken();
//	    String userRefreshToken = "USER_REFRESH_TOKEN"; // 여기에 사용자의 리프레시 토큰을 설정
	
	    try {
	        // URL 설정
	        URL url = new URL(apiUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	
	        // HTTP 요청 메소드 설정
	        connection.setRequestMethod("POST");
	
	        // HTTP 요청 헤더 설정
	        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	
	        // POST 데이터 설정
	        String postParameters = "grant_type=refresh_token" +
	                "&client_id=" + restApiKey +
	                "&refresh_token=" + userRefreshToken;
	
	        // POST 요청 본문 작성
	        connection.setDoOutput(true);
	        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
	        out.writeBytes(postParameters);
	        out.flush();
	        out.close();
	
	        // HTTP 응답 코드 확인
	        int responseCode = connection.getResponseCode();
	
	        // HTTP 응답 내용 읽기
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	
	            // JSON 형식의 응답 출력
	            System.out.println("여기야야야야야야=====>"+response.toString());
	            //{"access_token":"cAr_JsiFUP4GisQ8-NalANKcg4z41U6O6k4KPXObAAABi2pBj3-BPKUF0hG4dQ","token_type":"bearer","id_token":"eyJraWQi...","expires_in":21599}
	           
	            ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode jsonNode = objectMapper.readTree(response.toString());

	            // "access_token" 값을 추출
	            accessToken = jsonNode.get("access_token").asText();
	            System.out.println("!!@@!!@@access_token: " + accessToken);

	        } else {
	            // 요청이 실패한 경우 처리
	            System.out.println("HTTP 요청 실패: " + responseCode);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return accessToken;
	}



	public void unregisterProcess(MemberDTO dto) {
		String accessToken = getAccessTokenByRefreshToken(dto);
		kakaoRevokeAgreement(accessToken);
	}



//	public void storageAccessToken(String accessToken) {
//		repo.storageAccessToken(accessToken);
//		
//	}
	



}
