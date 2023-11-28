package com.watch.project.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.watch.project.dto.admin.ApiIpConfigDTO;
import com.watch.project.repository.admin.ApiIpConfigRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiIpConfigService {
	private final ApiIpConfigRepository repo;
	
	public void insertIpConfig(String requestIp) {
		repo.insertIpConfig(requestIp);
	}
	
	public void updateIpConfigById(ApiIpConfigDTO dto) {
		repo.updateIpConfigById(dto);
	}
	
	public List<ApiIpConfigDTO> getApiIpConfig(){
		return repo.getApiIpConfig();
	}
	
	public ApiIpConfigDTO getApiIpConfigById(int id) {
		return repo.getApiIpConfigById(id);
	}
	
	public void deleteIpConfigById(int id) {
		repo.deleteIpConfigById(id);
	}
}
