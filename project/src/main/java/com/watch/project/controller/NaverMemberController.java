package com.watch.project.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.watch.project.dto.MemberDTO;
import com.watch.project.service.NaverMemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NaverMemberController {
	private final NaverMemberService service;

	
	@RequestMapping(value = "/logout")
    public String Logout(HttpSession session) {
        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
	
	@GetMapping("/oauth2/login") //redirect url     /oauth2/login
    public String Oauth2Login(@RequestParam String code, @RequestParam String state, HttpSession session, HttpServletResponse res, Model model) throws IOException, JsonParseException {
		System.out.println("1111");
        OAuth2AccessToken oauthToken;
        System.out.println("2222");
        oauthToken = service.getAccessToken(session, code, state);
        //OAuth2AccessToken{access_token=AAAAOxwzWolZhu_SbW7gqyNHs4De4TdOkt-JQqD2l31A4JDgxbW1G7ljatGaDCfhu4lHsJqMWswAGWBCapAge5skJKA, token_type=bearer, expires_in=3600, refresh_token=gEwDRviiqKV7KBnaisMfUTwisBlNcgejRJ7iiAJPpUbDG5LBdAiisbtyQ3oWKRzagz1dIX7Zgip6yJgtN8TYUmXEqETyiiVap7e9Hk7Zv94r4GeMapzPNgT70VVWIp80xXIvIYc, scope=null}
        System.out.println("3333");

        /*네이버 프로필 정보 가져오기*/
        String result = service.getUserProfile(oauthToken); //result = {"resultcode":"00","message":"success","response":{"id":"knOmrEv4gmcf1mVR8R0p2rzOX_JjpDhZ3lLtRMNmEdY","email":"kimkitty123@naver.com","name":"\uae40\uc218\uc544"}}
        Object obj = null;

        JsonParser parser = new JsonParser();

        try {
            obj = parser.parse(result);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        JsonObject jobj = (JsonObject) obj;
        JsonObject res_obj = (JsonObject) jobj.get("response");
        String access_token = oauthToken.getAccessToken();
        String str_result = access_token.replaceAll("\\\"","");
        String mail = res_obj.get("email").toString().replaceAll("\"", "");
        MemberDTO existanceCheck = service.checkExistingMemberByEmail(mail);
        
        if(existanceCheck == null) { //회원이 아닐 경우
        	String msg = service.createNewMember(res_obj, str_result);//회원정보 저장
        	if(msg!= null) {//정상적으로 DB에 회원정보가 저장되지 않았을 경우 alert 출력
    			res.setContentType("text/html; charset=UTF-8");
    			PrintWriter out = res.getWriter();
    			out.print(msg);
        		return "/selectSignUpType";   
        	}
        }
        	//정상적으로 저장되었을 경우 or 이미 회원일 경우
        session.setAttribute("userEmail", mail);
        session.setAttribute("userLoginType", 3); 
        session.setAttribute("accessToken", str_result);
        return "redirect:/";
    }	
	
	@GetMapping("/remove") //token = access_token임
	public String remove(@RequestParam String token,HttpSession session, HttpServletRequest request, Model model ) {
		log.info("토큰 삭제중...");
		String apiUrl = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id="+service.CLIENT_ID+
		"&client_secret="+service.CLIENT_SECRET+"&access_token="+token+"&service_provider=NAVER";
		
			try {
				String res = requestToServer(apiUrl);
				model.addAttribute("res", res); //결과값 찍어주는용
			    session.invalidate();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    return "redirect:/";
	}
	
	private String requestToServer(String apiURL) throws IOException {
	    return requestToServer(apiURL, null);
	  }
	
	 private String requestToServer(String apiURL, String headerStr) throws IOException {
		    URL url = new URL(apiURL);
		    HttpURLConnection con = (HttpURLConnection)url.openConnection();
		    con.setRequestMethod("GET");
		    System.out.println("header Str: " + headerStr);
		    if(headerStr != null && !headerStr.equals("") ) {
		      con.setRequestProperty("Authorization", headerStr);
		    }
		    int responseCode = con.getResponseCode();
		    BufferedReader br;
		    System.out.println("responseCode="+responseCode);
		    if(responseCode == 200) { // 정상 호출
		      br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		    } else {  // 에러 발생
		      br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		    }
		    String inputLine;
		    StringBuffer res = new StringBuffer();
		    while ((inputLine = br.readLine()) != null) {
		      res.append(inputLine);
		    }
		    br.close();
		    if(responseCode==200) {
		    	String new_res=res.toString().replaceAll("&#39;", "");
				 return new_res; 
		    } else {
		      return null;
		    }
		  }
	
}
