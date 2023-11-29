package com.watch.project.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.admin.CorsConfigDTO;
import com.watch.project.service.admin.CorsConfigService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CorsConfigController {
	private final CorsConfigService service;
	
	@GetMapping("/admin/corsConfig")
	public String getCorsConfig(Model model) {
		List<CorsConfigDTO> corsConfigList = service.getCorsConfig();
		model.addAttribute("config", corsConfigList);
		return "admin/config/cors";
	}
	
	@PostMapping("/admin/insertCorsConfig")
	public String insertCorsConfig(String allowedOrigins) {
		service.insertCorsConfig(allowedOrigins);
		return "redirect:/admin/corsConfig";
	}
	
	@PostMapping("/admin/updateCorsConfig")
	public String updateCorsConfigById(CorsConfigDTO dto) {
		service.updateCorsConfigById(dto);
		return "redirect:/admin/corsConfig";
	}
	
	@GetMapping("/admin/deleteCorsConfig")
	public String deleteCorsConfigById(@RequestParam("id") int id) {
		service.deleteCorsConfigById(id);
		return "redirect:/admin/corsConfig";
	}
}
