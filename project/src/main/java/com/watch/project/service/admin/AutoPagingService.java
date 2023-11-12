package com.watch.project.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.dto.admin.TableInfoDTO;
import com.watch.project.repository.admin.AutoPagingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoPagingService {
	private final AutoPagingRepository repo;
	
	public TableInfoDTO getTableInfoDto(PagingConfigDTO pagingConfigDto) {
		return repo.getTableInfoByRowNumAndTableNm(pagingConfigDto);
	}
	
	public List<PagingConfigDTO> getAllPagingConfig(){
		return repo.getAllPagingConfig();
	}
	
	public PagingConfigDTO getPagingConfigByTableNmAndOrderByColumn(PagingConfigDTO dto) {
		return repo.getPagingConfigByTableNmAndOrderByColumn(dto);
	}
	
	public void insertPagingConfig(PagingConfigDTO dto) {
		repo.insertPagingConfig(dto);
	}
	
	public void updatePagingConfig(PagingConfigDTO dto) {
		repo.updatePagingConfig(dto);
	}
	
	public void deletePagingConfig(String id) {
		repo.deletePagingConfig(id);
	}
	
	public void updateRowNumByTableNm(PagingConfigDTO dto) {
		repo.updateRowNumByTableNm(dto);
	}
}
