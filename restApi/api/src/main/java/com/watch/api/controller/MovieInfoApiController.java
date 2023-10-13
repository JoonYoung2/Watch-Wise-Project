package com.watch.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.watch.api.dto.MovieInfoDTO;
import com.watch.api.service.MovieInfoApiService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MovieInfoApiController {
	private final MovieInfoApiService service;
	
	@GetMapping("test")
	public String test() throws IOException {
		String movieNm = "크리에이터";
		service.saveMovieInfoByMovieName(movieNm);
		return "redirect:/";
	}
	
	@PostMapping("dailyMovieUpdate")
	public String dailyMovieUpdate(String movieNms) throws IOException {
		String[] movieNm = movieNms.split(",");
		for(int i = 0; i < movieNm.length; ++i) {
			System.out.println("Controller - dailyMovieUpdate - movieNm : " + movieNm[i]);
			service.saveMovieInfoByMovieId(movieNm[i]);
		}
		return "redirect:/";
	}
	
	@PostMapping("weeklyMovieUpdate")
	public String weeklyMovieUpdate(String movieNms) throws IOException {
		String[] movieNm = movieNms.split(",");
		for(int i = 0; i < movieNm.length; ++i) {
			System.out.println("Controller - weeklyMovieUpdate - movieNm : " + movieNm[i]);
			service.saveMovieInfoByMovieId(movieNm[i]);
		}
		return "redirect:/";
	}
	
	@PostMapping("weekly2MovieUpdate")
	public String weekly2MovieUpdate(String movieNms) throws IOException {
		String[] movieNm = movieNms.split(",");
		for(int i = 0; i < movieNm.length; ++i) {
			System.out.println("Controller - weekly2MovieUpdate - movieNm : " + movieNm[i]);
			service.saveMovieInfoByMovieId(movieNm[i]);
		}
		return "redirect:/";
	}
	
	@PostMapping("weekly3MovieUpdate")
	public String weekly3MovieUpdate(String movieNms) throws IOException {
		String[] movieNm = movieNms.split(",");
		for(int i = 0; i < movieNm.length; ++i) {
			System.out.println("Controller - weekly3MovieUpdate - movieNm : " + movieNm[i]);
			service.saveMovieInfoByMovieId(movieNm[i]);
		}
		return "redirect:/";
	}
	
	@GetMapping("posterUrlUpdate")
	public String posterUrlUpdate() throws IOException {
		List<MovieInfoDTO> list = service.getMovieIdByUrlNan();
		for(int i = 0; i < list.size()-1; ++i) {
			System.out.println(list.get(i).getMovieId() + " " + list.get(i).getMovieNm() + " " + list.get(i).getOpenDt());
			service.moviePosterUrlUpdate(list.get(i).getMovieId(), list.get(i).getMovieNm(), list.get(i).getOpenDt());
		}
		return "redirect:/";
	}
}
