package com.watch.project.repository.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.admin.ApiIpConfigDTO;

@Mapper
public interface ApiIpConfigRepository {
	
	public void insertIpConfig(String requestIp);
	
	public void updateIpConfigById(ApiIpConfigDTO dto);
	
	public List<ApiIpConfigDTO> getApiIpConfig();
	
	public ApiIpConfigDTO getApiIpConfigById(int id);
	
	public void deleteIpConfigById(int id);
	
}
