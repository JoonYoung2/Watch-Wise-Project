package com.watch.project.controller.ajax.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.service.admin.AutoPagingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AutoPagingAjaxController {
	private final AutoPagingService service;
	@GetMapping("/updateRowNum")
	public void updateRowNum(@RequestParam("rowNum") int rowNum, @RequestParam("tableNm") String tableNm, 
			@RequestParam("orderByColumn") String orderByColumn) {
		PagingConfigDTO dto = new PagingConfigDTO();
		
		dto.setRowNum(rowNum);
		dto.setTableNm(tableNm);
		dto.setOrderByColumn(orderByColumn);
		
		service.updateRowNumByTableNm(dto);
	}
}
