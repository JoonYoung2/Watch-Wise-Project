package com.watch.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.watch.api.service.MovieInfoApiService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	private final MovieInfoApiService service;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/getAllMovieNotNan")
	public String getAllMovieNotNan(Model model) {
		model.addAttribute("list", service.getAllMovieNotNan());
		return "movie_list";
	}
}
