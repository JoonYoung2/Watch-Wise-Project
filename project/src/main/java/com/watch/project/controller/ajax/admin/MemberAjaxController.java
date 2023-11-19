package com.watch.project.controller.ajax.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.controller.ajax.ReviewAjaxController;
import com.watch.project.controller.ajax.ReviewAjaxController.MsgOnlyResponse;
import com.watch.project.service.admin.MemberService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MemberAjaxController {
	@Autowired 
	private MemberService memberService;
	
	@Data
	public class MsgOnlyResponse{
		private String msg;
		public MsgOnlyResponse(String msg) {
			this.msg = msg;
		}
	}
	
	@PostMapping("/reportComment")
	public MsgOnlyResponse reportComment(@RequestParam("author") String authorEmail, @RequestParam("comment") String comment, @RequestParam("commentId") String commentId, @RequestParam("movieId") String movieId,@RequestParam("reason") String reason, RedirectAttributes redirectAttr) {
		String msg = memberService.saveReport(authorEmail, comment, commentId, movieId, reason);
		redirectAttr.addFlashAttribute("msg", msg);
		MsgOnlyResponse response = new MsgOnlyResponse(msg);
		return response;
	}	
	
	@GetMapping("/cancelReport")
	public MsgOnlyResponse cancelReport(@RequestParam("movieId") String movieId, @RequestParam("userEmail") String author) {
		log.info("movieId => {}", movieId);
		log.info("author => {}", author);
		String msg = memberService.deleteReportedDatas(movieId, author);
		MsgOnlyResponse response = new MsgOnlyResponse(msg);
		return response;
	}
	
}
