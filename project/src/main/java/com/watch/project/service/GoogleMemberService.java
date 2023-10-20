package com.watch.project.service;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.watch.project.dto.GoogleInfoResponseDTO;
import com.watch.project.dto.GoogleRequestDTO;
import com.watch.project.dto.GoogleResponseDTO;
import com.watch.project.dto.MemberDTO;
import com.watch.project.repository.MemberRepository;
@Service
public class GoogleMemberService {
	@Autowired 
	private MemberRepository repo;
	private CommonMethods common;
	
	public final static String CLIENT_ID = "339734555922-1robs84vtcrorvt5o56qf28rihef34kb.apps.googleusercontent.com";
	public final static String CLIENT_PW = "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";
	public final static String REDIRECT_URI = "http://localhost:8080/google/login";	
	
	public String getAuthorizationUrl(HttpSession session) {
        String googleUrl = "https://accounts.google.com/o/oauth2/v2/auth?" 
        		+ "&client_id=" + CLIENT_ID  //구글 개발자센터에서 발급받은 JavaScript Key
        		+ "&redirect_uri=" + REDIRECT_URI //구글 개발자센터에서 설정한 Callback URI
        		+ "&response_type=code" //'code'로 고정 (인가코드를 통한 로그인 방식)
                + "&scope=email profile openid" //토큰 발급 이후 유저 정보에서 어떤 항목을 조회할 것인가를 띄어쓰기를 구분으로 하여 ' ' 입력해준다.
//                + "&state=security_token%3D138r5719ru3e1%26url%3Dhttps://oauth2.example.com/token" 
                + "&access_type=offline";
		return googleUrl;
	}
	public String loginWithGoogle(String authCode) {
		RestTemplate restTemplate = new RestTemplate();
		//RestTemplate
		//=> RESTful 웹 서비스 또는 외부 API와 통신할 때 사용됨. 
		//=> HTTP GET, POST, PUT, DELETE 등의 HTTP메서드를 사용해서 원격 API로 요청을 보내고 응답을 처리할 수 있음.
		GoogleRequestDTO googleOauthRequestParam = GoogleRequestDTO
				.builder()
				.clientId(CLIENT_ID)
				.clientSecret(CLIENT_PW)
				.code(authCode)
				.redirectUri(REDIRECT_URI)///////////////////////////
				.grantType("authorization_code").build();
		
		ResponseEntity<GoogleResponseDTO> resultEntity =  //access token 얻기 위한 과정
				restTemplate.postForEntity("https://oauth2.googleapis.com/token", googleOauthRequestParam, GoogleResponseDTO.class); 
		//ResponseEntity  
		//=> HTTP 응답을 나타내는 클래스. 일반적으로 RestTemplate를 사용하여 원격 서버로부터 받은 HTTP 응답을 감싸는 데 사용됨.
		//postForEntity 
		//=> POST 요청을 보내고 서버로부터의 응답을 ResponseEntity로 받아옴.
		
		String jwtToken = resultEntity.getBody().getId_token(); //access token 가져옴.
		Map<String, String> map = new HashMap<>();
		map.put("id_token",  jwtToken);
		
		ResponseEntity<GoogleInfoResponseDTO> resultEntity2 = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo", map, GoogleInfoResponseDTO.class);
		String email = resultEntity2.getBody().getEmail();
		System.out.println("여기는 service 이고, email 확인 : "+ email);
		String name = resultEntity2.getBody().getName();
		System.out.println("여기는 service 이고, name 확인 : "+ name);
		
//		MemberDTO dto = MemberDTO
//				.builder()
//				.userEmail(email)
//				.userName(name)
//				.userPw("GoogleMember")
//				.userLoginType(4).build();
//		
//		MemberDTO existanceCheck = common.checkExistingMemberByEmail(email);
//		if(existanceCheck == null) { //회원이 아닐 경우
//			String msg = null;
//			int storageResult = common.saveMemberInfo(dto);
//			if(storageResult != 1){
//				msg = common.getAlertOnly("오류가 발생했습니다. 다시 시도해주세요.");
//				
//			}
//		}
		return null;
	}

}
