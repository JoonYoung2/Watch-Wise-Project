package com.watch.project.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.watch.project.controller.admin.AutoPagingController.PagingDTO;
import com.watch.project.controller.admin.AutoPagingController.RowNumUpdateDTO;
import com.watch.project.controller.admin.AutoPagingController.TitleDTO;
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
	
	@GetMapping("/admin/actor_list/people_id/{pageNum}")
	public String peopleInfoListOrderBypeopleId(@PathVariable("pageNum") int pageNum, Model model) {
		
		if(adminCertification()) {
			return "/admin/login";
		}
		
		String[] titleNm = {"NUM", "ID", "배우명", "성별", "좋아요"};
		
		String tableNm = "people_info_detail";
		String orderByColumn = "people_id";
		PagingConfigDTO dto = new PagingConfigDTO();
		dto.setTableNm(tableNm);
		dto.setOrderByColumn(orderByColumn);
		PagingConfigDTO pagingConfigDto = autoPagingService.getPagingConfigByTableNmAndOrderByColumn(dto);
		
		
		TableInfoDTO tableInfoDto = autoPagingService.getTableInfoDto(pagingConfigDto);
		
		/*
		 * 테이블 제목
		 */
		List<TitleDTO> titleList = service.getTitleLList(titleNm);
		
		/*
		 * 테이블 내용
		 */
		List<PeopleInfoDTO> peopleInfoList = service.getPeopleInfoList(pageNum, pagingConfigDto, tableInfoDto);
		
		/*
		 * 페이징 넘버
		 */
		PagingDTO pagingDto = service.getPagingDto(pageNum, tableInfoDto);
		
		/*
		 * 행 개수 Update할 때 필요
		 */
		RowNumUpdateDTO rowNumUpdateDto = 
				RowNumUpdateDTO
				.builder()
				.pageNum(pageNum)
				.rowNum(pagingConfigDto.getRowNum())
				.tableNm(tableNm)
				.build();
		
		model.addAttribute("titleList", titleList);
		model.addAttribute("contentList", peopleInfoList);
		model.addAttribute("paging", pagingDto);
		model.addAttribute("pagingConfig", rowNumUpdateDto);
		
		return "admin/actor/people_id_list";
	}
	
	private boolean adminCertification() {
		boolean check = false;
		if(session.getAttribute("admin") == null) {
			check = true;
		}
		return check;
	}
	
	
}
