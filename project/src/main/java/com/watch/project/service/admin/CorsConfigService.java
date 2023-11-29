package com.watch.project.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.watch.project.dto.admin.CorsConfigDTO;
import com.watch.project.repository.admin.CorsConfigRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CorsConfigService {
	private final CorsConfigRepository repo;
	
	public void insertCorsConfig(String allowedOrigins) {
		repo.insertCorsConfig(allowedOrigins);
	}
	
	public void updateCorsConfigById(CorsConfigDTO dto) {
		repo.updateCorsConfigById(dto);
	}
	
	public List<CorsConfigDTO> getCorsConfig(){
		return repo.getCorsConfig();
	}
	
	public CorsConfigDTO getCorsConfigById(int id) {
		return repo.getCorsConfigById(id);
	}
	
	public void deleteCorsConfigById(int id) {
		repo.deleteCorsConfigById(id);
	}
}
