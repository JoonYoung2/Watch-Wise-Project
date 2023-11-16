package com.watch.project.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.dto.admin.AutoPagingDTO;
import com.watch.project.dto.admin.MovieInfoDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.dto.admin.PeopleInfoDTO;
import com.watch.project.dto.admin.TableInfoDTO;
import com.watch.project.service.admin.AutoPagingService;
import com.watch.project.service.admin.PeopleInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller("adminPeopleInfoController")
@RequiredArgsConstructor
@Slf4j
public class PeopleInfoController {
	private final HttpSession session;
	private final AutoPagingService autoPagingService;
	private final PeopleInfoService service;
	
	@GetMapping("/admin/{listNm}/{tableNm}/{orderByColumn}/{pageNum}")
	public String movieInfoList(
			@PathVariable("listNm") String listNm,
			@PathVariable("tableNm") String tableNm, 
			@PathVariable("orderByColumn") String orderByColumn,
			@PathVariable("pageNum") int pageNum,
			@RequestParam("query") String query, 
			Model model) {
		if(adminCertification()) {
			return "/admin/login";
		}
		String[] titleNm = {"NUM", "ID", "배우명", "성별", "좋아요"};
		
		String[] conditionColumns= {"people_id", "people_nm", "people_nm_en"};
		
		String conditionColumn = service.getConditionColumn(conditionColumns, query);
		
		TableInfoDTO tableInfoDto = null;
		List<PeopleInfoDTO> peopleInfoList = null;
		
		PagingConfigDTO dto = new PagingConfigDTO();
		dto.setTableNm(tableNm);
		dto.setOrderByColumn(orderByColumn);
		
		PagingConfigDTO pagingConfigDto = autoPagingService.getPagingConfigByTableNmAndOrderByColumn(dto);
		pagingConfigDto.setConditionColumn(conditionColumn);
		
		if(query.equals("")) 
			tableInfoDto = autoPagingService.getTableInfoDto(pagingConfigDto);
		else 
			tableInfoDto = autoPagingService.getTableInfoDtoQuery(pagingConfigDto);
		
		
		AutoPagingDTO autoPagingDto = new AutoPagingDTO();
		
		int rowNum = pagingConfigDto.getRowNum();
		int end = autoPagingDto.getEnd(pageNum, rowNum);
		int start = autoPagingDto.getStart(end, rowNum);
		List<String> titleList = service.getTitleLList(titleNm);
		
		/*
		 * 테이블 내용
		 */
		if(query.equals("")) {
			peopleInfoList = service.getPeopleInfoList(start, end, pagingConfigDto);			
		}else {
			peopleInfoList = service.getPeopleInfoListQuery(start, end, pagingConfigDto, conditionColumn);	
		}
		
		autoPagingDto.setAutoPagingDto(listNm, tableNm, pageNum, rowNum, orderByColumn, titleList, tableInfoDto);
		
		query = query.replaceAll("''", "'");
		
		model.addAttribute("contentList", peopleInfoList);
		model.addAttribute("autoPaging", autoPagingDto);
		model.addAttribute("query", query);
		
		return "admin/actor/list";
	}
	
	private boolean adminCertification() {
		boolean check = false;
		if(session.getAttribute("admin") == null) {
			check = true;
		}
		return check;
	}
	
	
}
