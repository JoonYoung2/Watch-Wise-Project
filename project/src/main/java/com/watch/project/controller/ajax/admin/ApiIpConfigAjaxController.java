package com.watch.project.controller.ajax.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watch.project.dto.admin.ApiIpConfigDTO;
import com.watch.project.service.admin.ApiIpConfigService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiIpConfigAjaxController {
	private final ApiIpConfigService service;
	
	@GetMapping("/admin/getApiIpConfig")
	public ApiIpConfigDTO getApiConfigById(@RequestParam("id") int id) {
		return service.getApiIpConfigById(id);
	}
}
