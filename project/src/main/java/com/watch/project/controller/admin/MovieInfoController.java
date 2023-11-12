package com.watch.project.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.project.controller.admin.AutoPagingController.PagingDTO;
import com.watch.project.controller.admin.AutoPagingController.RowNumUpdateDTO;
import com.watch.project.controller.admin.AutoPagingController.TitleDTO;
import com.watch.project.dto.admin.MovieInfoDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.dto.admin.PeopleInfoDTO;
import com.watch.project.dto.admin.TableInfoDTO;
import com.watch.project.service.admin.AutoPagingService;
import com.watch.project.service.admin.MovieInfoService;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller("adminMovieInfoController")
@RequiredArgsConstructor
@Slf4j
public class MovieInfoController {
	private final HttpSession session;
	private final MovieInfoService service;
	private final AutoPagingService autoPagingService;
	
	@GetMapping("/admin/movie_info")
	public String movieInfo() {
		
		if(adminCertification()) {
			return "/admin/login";
		}
		
		return "/admin/movie/index";
	}
	
	@GetMapping("/admin/movie_list/open_dt/{pageNum}")
	public String movieInfoList(@PathVariable("pageNum") int pageNum, Model model) {
		
		if(adminCertification()) {
			return "/admin/login";
		}
		
		String[] titleNm = {"NUM", "ID", "영화명", "개봉일", "좋아요"};
		
		String tableNm = "movie_info";
		String orderByColumn = "open_dt";
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
		List<MovieInfoDTO> movieInfoList = service.getMovieInfoList(pageNum, pagingConfigDto);
		
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
		model.addAttribute("contentList", movieInfoList);
		model.addAttribute("paging", pagingDto);
		model.addAttribute("pagingConfig", rowNumUpdateDto);
		
		return "admin/movie/open_dt_list";
	}
	
	private boolean adminCertification() {
		boolean check = false;
		if(session.getAttribute("admin") == null) {
			check = true;
		}
		return check;
	} 
	
	
}
