package com.watch.project.controller.ajax;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watch.project.service.PeopleInfoService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PeopleInfoAjaxController {
	private final PeopleInfoService service;
	
	/*
	 * 배우 좋아요 추가
	 */
	@GetMapping("/peopleLikeAdd")
	public LikeUpdateResponse peopleLikeAdd(@RequestParam("peopleId") int peopleId, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		int likeNum = 0;
		
		if(userEmail != null) 
			service.peopleLikeAdd(peopleId, userEmail);			
		
		
		likeNum = service.getLikeNumById(peopleId);
		
        return new LikeUpdateResponse(likeNum);
	}
	
	/*
	 * 배우 좋아요 취소
	 */
	@GetMapping("/peopleLikeCancel")
	public LikeUpdateResponse peopleLikeCancel(@RequestParam("peopleId") int peopleId, HttpSession session) {
		String userEmail = (String)session.getAttribute("userEmail");
		int likeNum = 0;
		
		if(userEmail != null)
			service.peopleLikeCancel(peopleId, userEmail);
		
		likeNum = service.getLikeNumById(peopleId);
		
		return new LikeUpdateResponse(likeNum);
	}
	
	@Data
	private class LikeUpdateResponse{
		private int likeNum;
		
		public LikeUpdateResponse(int likeNum) {
			this.likeNum = likeNum;
		}
	}
}
