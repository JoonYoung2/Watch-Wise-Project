package com.watch.project.controller.member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.watch.project.dto.LiveSearchDTO;
import com.watch.project.dto.MemberDTO;
import com.watch.project.repository.MemberRepository;
import com.watch.project.service.SearchService;
import com.watch.project.service.member.CommonMemberService;
import com.watch.project.service.member.NaverMemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NaverMemberController {
	private final NaverMemberService service;
	private final MemberRepository repo;
	private final SearchService searchService;

	
	@GetMapping("/naverSignOut")
    public String Logout(HttpSession session) {
        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
	
	@GetMapping("/oauth2/login") //redirect url     /oauth2/login
    public String Oauth2Login(@RequestParam String code, @RequestParam String state, HttpSession session, HttpServletResponse res, Model model, RedirectAttributes redirectAttr) throws IOException, JsonParseException {
        OAuth2AccessToken oauthToken;
        oauthToken = service.getAccessToken(session, code, state);
        
        /*네이버 프로필 정보 가져오기*/
        String result = service.getUserProfile(oauthToken);
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
        String refresh_token = oauthToken.getRefreshToken();
        String str_result = access_token.replaceAll("\\\"","");
        String mail = res_obj.get("email").toString().replaceAll("\"", "");
        MemberDTO existanceCheck = service.checkExistingMemberByEmail(mail);
        
        if(existanceCheck == null) { //회원이 아닐 경우
        	String msg = service.createNewMember(res_obj, str_result);//회원정보 저장

        	if(msg!= null) {//정상적으로 DB에 회원정보가 저장되지 않았을 경우 alert 출력
        		searchService.searchModel(model);
        		model.addAttribute("msg", msg);
        		return "member/select_sign_up_type";   
        	}
        }
        //정상적으로 저장되었을 경우 or 이미 회원일 경우
        //네이버 1로 
        repo.updateNaverAgreement(mail);
        
        //refresh_token 저장
    	MemberDTO dtoForRefreshToken = repo.getUserInfoByEmail(mail);
    	dtoForRefreshToken.setNaverRefreshToken(refresh_token);
    	repo.updateNaverRefreshToken(dtoForRefreshToken);
    	
        session.setAttribute("userEmail", mail);
        session.setAttribute("userLoginType", 3); 
        session.setAttribute("accessToken", str_result);
        redirectAttr.addFlashAttribute("msg", "환영합니다.");
        return "redirect:/";
    }	
}
