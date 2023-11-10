package com.watch.project.repository.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.admin.PagingConfigDTO;

@Mapper
public interface AutoPagingRepository {
	
	public List<PagingConfigDTO> getAllPagingConfig();
	
	public PagingConfigDTO getPagingConfigByTableNmAndOrderByColumn(PagingConfigDTO dto);
	
	public void insertPagingConfig(PagingConfigDTO dto);
	
	public void updatePagingConfig(PagingConfigDTO dto);
	
	public void deletePagingConfig(String id);
	
	public void updateRowNumByTableNm(PagingConfigDTO dto);
	
}
