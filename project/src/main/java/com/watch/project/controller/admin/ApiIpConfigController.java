package com.watch.project.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.admin.ApiIpConfigDTO;
import com.watch.project.service.admin.ApiIpConfigService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ApiIpConfigController {
	private final ApiIpConfigService service;
	
	@GetMapping("/admin/apiIpConfig")
	public String apiIpConfig(Model model) {
		List<ApiIpConfigDTO> apiIpConfigList = service.getApiIpConfig();
		model.addAttribute("config", apiIpConfigList);
		return "admin/config/api_ip";
	}
	
	@PostMapping("/admin/insertApiIpConfig")
	public String insertIpConfig(String requestIp) {
		log.info("{}", requestIp);
		service.insertIpConfig(requestIp);
		return "redirect:/admin/apiIpConfig";
	}
	
	@PostMapping("/admin/updateApiIpConfig")
	public String updateIpConfig(ApiIpConfigDTO dto) {
		service.updateIpConfigById(dto);
		return "redirect:/admin/apiIpConfig";
	}
	
	@GetMapping("/admin/deleteApiIpConfig")
	public String deleteIpConfigById(@RequestParam("id") int id) {
		log.info("{}", id);
		service.deleteIpConfigById(id);
		return "redirect:/admin/apiIpConfig";
	}
}
