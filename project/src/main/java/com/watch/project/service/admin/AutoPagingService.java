package com.watch.project.service.admin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpSession;

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
	private final HttpSession session;
	
	public TableInfoDTO getTableInfoDto(PagingConfigDTO pagingConfigDto) {
		return repo.getTableInfoByRowNumAndTableNm(pagingConfigDto);
	}
	
	public TableInfoDTO getTableInfoDtoQuery(PagingConfigDTO pagingConfigDto) {
		return repo.getTableInfoByRowNumAndTableNmQuery(pagingConfigDto);
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
	
	public int getStart(int end, int rowNum) {
		return end - rowNum + 1;
	}

	public int getEnd(int pageNum, int rowNum) {
		return pageNum * rowNum;
	}
	
	/*
	 * 생성, 업데이트, 삭제 후 경로
	 */
	public String getCreateAndUpdateAndDeleteView(String listNm, String tableNm, String orderByColumn, int pageNum, String query) {
		return "redirect:/admin/" + listNm + "/" + tableNm + "/" + orderByColumn + "/" + pageNum + "?query=" + query;
	}
	
	/*
	 * 검색 유무
	 */
	public String getQuery(String query) {
		if(query.equals("nan")) {
			query = "";
		}
		
		try {
			query = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return query;
	}
	
	/*
	 * admin계정 로그인 확인
	 */
	public boolean adminCertification() {
		boolean check = false;
		if(session.getAttribute("admin") == null) {
			check = true;
		}
		return check;
	}
}
