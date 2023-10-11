package com.watch.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.watch.project.service.HomeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	private final HomeService service;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("dto", service.getId());
		return "home"; 
	}
}
