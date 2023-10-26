package com.watch.project.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonObject;
import com.watch.project.dto.MemberDTO;
import com.watch.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NaverMemberService {
	@Autowired CommonMethods commonMethods;
	
	private final MemberRepository repo;
	public final static String CLIENT_ID = "wXYtfbDmAwaaISKhx2LT";
    public final static String CLIENT_SECRET = "BVDTLAjpcV";
    public final static String SESSION_STATE = "state";
    public final static String REDIRECT_URI = "http://localhost:8080/oauth2/login";
    public final static String REDIRECT_LOGUT_URI = "http://localhost:8080/oauth2/logout";
    public final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
	
    public String getUserProfile(OAuth2AccessToken oauthToken) throws IOException {
        OAuth20Service oauthService = new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI)
                .build(NaverLoginApi.instance());

        OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
        oauthService.signRequest(oauthToken, request);
        Response response = request.send();
        System.out.println("service getprofile : "+response.getBody());
        return response.getBody();
    }
    
	public String getAuthorizationUrl(HttpSession session) {
		String state = generateRandomString();
        /*생성한 난수를 세션에 저장*/
        setSession(session, state);
        /* Scribe에서 제공하는 인증 URL 생성 기능을 이용하여 네이버 아이디로 인증 URL 생성 */
//        OAuth20Service oAuthService = new ServiceBuilder()
//                .apiKey(CLIENT_ID)
//                .apiSecret(CLIENT_SECRET)
//                .callback(REDIRECT_URI)
//                .state(state)
//                .build(NaverLoginApi.instance());
//        
//        System.out.println("naver URL = " + oAuthService.getAuthorizationUrl());
//        return oAuthService.getAuthorizationUrl();
        String url = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
        url += "&client_id=" + CLIENT_ID;
        url += "&redirect_uri=" + REDIRECT_URI;
        url += "&state=" + state;
        return url;
        
	}
	
	 /*네이버 로그인 AccessToken 획득  get AccessToken*/
    public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {
        String sessionState = getSession(session);

        if (StringUtils.pathEquals(sessionState, state)) {
            OAuth20Service oAuthService = new ServiceBuilder()
                    .apiKey(CLIENT_ID)
                    .apiSecret(CLIENT_SECRET)
                    .callback(REDIRECT_URI)
                    .state(state)
                    .build(NaverLoginApi.instance());

            OAuth2AccessToken accessToken = oAuthService.getAccessToken(code);
            System.out.println("service get Access Token : "+ accessToken );
            return accessToken;
        }

        return null;
    }
    
    /*네이버 로그인 AccessToken 획득 get Logout AccessToken*/
    public OAuth2AccessToken getLogoutAccessToken(HttpSession session, String code, String state) throws IOException {
        String sessionState = getSession(session);

        if (StringUtils.pathEquals(sessionState, state)) {
            OAuth20Service oAuthService = new ServiceBuilder()
                    .apiKey(CLIENT_ID)
                    .apiSecret(CLIENT_SECRET)
                    .callback(REDIRECT_URI)
                    .state(state)
                    .build(NaverLoginApi.instance());

            OAuth2AccessToken accessToken = oAuthService.getAccessToken(code);

            return accessToken;
        }

        return null;
    }
    
    private String getSession(HttpSession session) {
        return (String) session.getAttribute(SESSION_STATE);
    }
	
	private String generateRandomString() {
        return UUID.randomUUID().toString();
    }
	
	private void setSession(HttpSession session, String state) {
        session.setAttribute(SESSION_STATE, state);
    }

	public int saveMemberInfo(MemberDTO user) {
		int result = repo.saveMemberInfo(user);
		return result;
	}

	public MemberDTO checkExistingMemberByEmail(String userEmail) {
		MemberDTO result = repo.getUserInfoByEmail(userEmail);
		return result;
	}

	public String createNewMember(JsonObject res_obj, String str_result) {
		String msg= null;
		MemberDTO user = new MemberDTO();
		String email = res_obj.get("email").toString().replaceAll("\"", "");
    	user.setUserEmail(email);
    	user.setUserPw("NaverMember");
    	user.setUserName(res_obj.get("name").toString().replaceAll("\"", ""));
    	user.setUserLoginType(3);
    	user.setSocialLoginId(res_obj.get("id").toString().replaceAll("\"", ""));        	

    	user.setAccessToken(str_result);
    	int storageResult = saveMemberInfo(user); //회원 정보 저장
    	int updateResult = repo.updateNaverAgreement(email);
    	
    	if(storageResult != 1 || updateResult != 1) {
    		msg = "오류가 발생했습니다. 다시 시도해주세요.";
    	}		
    	return msg;
	}

	public void unregisterProcess(MemberDTO dto) {
		String accessToken = getAccessTokenByRefreshToken(dto);
//		naverRevokeAgreement(accessToken);/////////////////////////////////////////////////여기야아아아아아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ

	}

	private String getAccessTokenByRefreshToken(MemberDTO dto) {
		String accessToken = null;
	    // Naver API 엔드포인트 및 API 키 설정
	    String apiUrl = "https://nid.naver.com/oauth2.0/token";
	    String clientId = "wXYtfbDmAwaaISKhx2LT";
	    String clientSecret = "BVDTLAjpcV";
	    String userRefreshToken = dto.getNaverRefreshToken();

	
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
	                "&client_id=" + clientId +
	                "&client_secret=" + clientSecret +
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
	            System.out.println("여기야야야야야야 naver response.toString()=====>"+response.toString());
	            //{"access_token":"cAr_JsiFUP4GisQ8-NalANKcg4z41U6O6k4KPXObAAABi2pBj3-BPKUF0hG4dQ","token_type":"bearer","id_token":"eyJraWQi...","expires_in":21599}
	           
	            ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode jsonNode = objectMapper.readTree(response.toString());

	            // "access_token" 값을 추출
	            accessToken = jsonNode.get("access_token").asText();
	            System.out.println("!!@@!!@@ naver new access_token: " + accessToken);

	        } else {
	            // 요청이 실패한 경우 처리
	            System.out.println("HTTP 요청 실패: " + responseCode);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return accessToken;
	}
}
