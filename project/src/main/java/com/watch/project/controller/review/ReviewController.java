package com.watch.project.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.MovieReviewDTO;
import com.watch.project.service.ReviewService;

@Controller
public class ReviewController {
	@Autowired
	private ReviewService service;
	
	@GetMapping("/testSua")
	public String test() {
		return "test";
	}

	@PostMapping("/saveComment")
	public String saveComment(MovieReviewDTO dto, RedirectAttributes redirectAttr) {//movieId, reviewComment
		String msg = service.insertComment(dto);//코멘트 저장
//		String ifWroteComment = service.getComment(dto.getMovieId()); //여기 movie_info 컨트롤러로 옮겼으니까 테스트 후에 지워주기
		String movieId=dto.getMovieId();
		redirectAttr.addFlashAttribute("msg", msg);
//		redirectAttr.addFlashAttribute("ifWroteComment", ifWroteComment); ///여기 movie_info 컨트롤러로 옮겼으니까 테스트 후에 지워주기.
		return "redirect:/movieInfo?movieId="+movieId;
	}
	
	@GetMapping("/delete_comment")
	public String deleteComment(@RequestParam("id") String id, @RequestParam("movieId") String movieId, RedirectAttributes redirectAttr) {
		String msg = service.deleteComment(id);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/movieInfo?movieId="+movieId;
	}
	
	@PostMapping("/modifyComment")
	public String modifyComment(MovieReviewDTO dto, RedirectAttributes redirectAttr) {
		String msg = service.modifyComment(dto);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/movieInfo?movieId="+dto.getMovieId();
	}
}
