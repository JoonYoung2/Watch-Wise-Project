package com.watch.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.watch.api.service.MovieRegisterService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MovieRegisterController {
	private final MovieRegisterService service;
	
	@GetMapping("test")
	public String test() {
		service.getMovieInfoByMovieName();
		return "redirect:/";
	}
}
