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
import com.watch.project.dto.admin.TableInfoDTO;
import com.watch.project.service.admin.AutoPagingService;
import com.watch.project.service.admin.MovieInfoService;

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
	
	@GetMapping("/admin/movie_info/open_dt/{pageNum}")
	public String movieInfoList(@PathVariable("pageNum") int pageNum, @RequestParam("query") String query, Model model) {
		if(adminCertification()) {
			return "/admin/login";
		}
		String[] titleNm = {"NUM", "ID", "영화명", "개봉일", "좋아요"};
		
		String[] conditionColumns= {"movie_id", "movie_nm"};
		
		String conditionColumn = service.getConditionColumn(conditionColumns, query);
		
		String tableNm = "movie_info";
		String orderByColumn = "open_dt";
		
		TableInfoDTO tableInfoDto = null;
		List<MovieInfoDTO> movieInfoList = null;
		
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
			movieInfoList = service.getMovieInfoList(start, end, pagingConfigDto);			
		}else {
			movieInfoList = service.getMovieInfoListQuery(start, end, pagingConfigDto, conditionColumn);	
		}
		
		autoPagingDto.setAutoPagingDto(tableNm, pageNum, rowNum, orderByColumn, titleList, tableInfoDto);
		
		model.addAttribute("contentList", movieInfoList);
		model.addAttribute("autoPaging", autoPagingDto);
		model.addAttribute("query", query);
		
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
