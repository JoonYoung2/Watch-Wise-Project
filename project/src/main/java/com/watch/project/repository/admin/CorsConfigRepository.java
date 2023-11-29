package com.watch.project.repository.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.admin.CorsConfigDTO;

@Mapper
public interface CorsConfigRepository {
	
	public void insertCorsConfig(String requestCors);
	
	public void updateCorsConfigById(CorsConfigDTO dto);
	
	public List<CorsConfigDTO> getCorsConfig();
	
	public CorsConfigDTO getCorsConfigById(int id);
	
	public void deleteCorsConfigById(int id);
	
}
