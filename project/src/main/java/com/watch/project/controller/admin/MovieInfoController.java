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

import com.watch.project.dto.admin.MovieInfoDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.dto.admin.PeopleInfoDTO;
import com.watch.project.dto.admin.TableInfoDTO;
import com.watch.project.service.admin.AutoPagingService;
import com.watch.project.service.admin.MovieInfoService;

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
	
	@GetMapping("/admin/movie_list/open_dt/{pageNum}")
	public String movieInfoList(@PathVariable("pageNum") int pageNum, Model model) {
		
		String[] titleNm = {"NUM", "ID", "영화명", "개봉일", "좋아요"};
		
		String tableNm = "movie_info";
		String orderByColumn = "open_dt";
		PagingConfigDTO dto = new PagingConfigDTO();
		dto.setTableNm(tableNm);
		dto.setOrderByColumn(orderByColumn);
		PagingConfigDTO pagingConfigDto = autoPagingService.getPagingConfigByTableNmAndOrderByColumn(dto);
		
		
		TableInfoDTO tableInfoDto = service.getTableInfoDto(pagingConfigDto);
		
		/*
		 * 테이블 제목
		 */
		List<TitleDTO> titleList = new ArrayList<>();
		
		/*
		 * 테이블 내용
		 */
		List<MovieInfoDTO> movieInfoList = service.getMovieInfoList(pageNum, pagingConfigDto, tableInfoDto);
		
		/*
		 * 페이징 넘버
		 */
		PagingDTO pagingDto = new PagingDTO();
		
		/*
		 * 행 개수 Update할 때 필요
		 */
		RowNumUpdateDTO rowNumUpdateDto = new RowNumUpdateDTO();
		
		if(pageNum > 5 && pageNum < tableInfoDto.getPageNum() - 5) {
			pagingDto.setStart(pageNum-4);
			pagingDto.setEnd(pageNum+5);
			pagingDto.setLast(tableInfoDto.getPageNum());
		}else if(pageNum <= 5) {
			pagingDto.setStart(1);
			pagingDto.setEnd(10);
			pagingDto.setLast(tableInfoDto.getPageNum());
		}else {
			pagingDto.setStart(tableInfoDto.getPageNum() - 9);
			pagingDto.setEnd(tableInfoDto.getPageNum());
		}
		
		for(int i = 0; i < titleNm.length; ++i) {
			TitleDTO titleDto = new TitleDTO();
			titleDto.setTitleNm(titleNm[i]);
			titleList.add(titleDto);
		}
		
		rowNumUpdateDto.setPageNum(pageNum);
		rowNumUpdateDto.setTableNm(tableNm);
		rowNumUpdateDto.setRowNum(pagingConfigDto.getRowNum());
		
		model.addAttribute("titleList", titleList);
		model.addAttribute("contentList", movieInfoList);
		model.addAttribute("paging", pagingDto);
		model.addAttribute("pagingConfig", rowNumUpdateDto);
		
		return "admin/movie/list";
	}
	
	@GetMapping("/admin/movie_list/like_num/{pageNum}")
	public String movieInfoListOrderByLikeNum(@PathVariable("pageNum") int pageNum, Model model) {
		
		String[] titleNm = {"NUM", "ID", "영화명", "개봉일", "좋아요"};
		
		String tableNm = "movie_info";
		String orderByColumn = "like_num";
		PagingConfigDTO dto = new PagingConfigDTO();
		dto.setTableNm(tableNm);
		dto.setOrderByColumn(orderByColumn);
		PagingConfigDTO pagingConfigDto = autoPagingService.getPagingConfigByTableNmAndOrderByColumn(dto);
		
		
		TableInfoDTO tableInfoDto = service.getTableInfoDto(pagingConfigDto);
		
		/*
		 * 테이블 제목
		 */
		List<TitleDTO> titleList = new ArrayList<>();
		
		/*
		 * 테이블 내용
		 */
		List<MovieInfoDTO> movieInfoList = service.getMovieInfoList(pageNum, pagingConfigDto, tableInfoDto);
		
		/*
		 * 페이징 넘버
		 */
		PagingDTO pagingDto = new PagingDTO();
		
		/*
		 * 행 개수 Update할 때 필요
		 */
		RowNumUpdateDTO rowNumUpdateDto = new RowNumUpdateDTO();
		
		if(pageNum > 5 && pageNum < tableInfoDto.getPageNum() - 5) {
			pagingDto.setStart(pageNum-4);
			pagingDto.setEnd(pageNum+5);
			pagingDto.setLast(tableInfoDto.getPageNum());
		}else if(pageNum <= 5) {
			pagingDto.setStart(1);
			pagingDto.setEnd(10);
			pagingDto.setLast(tableInfoDto.getPageNum());
		}else {
			pagingDto.setStart(tableInfoDto.getPageNum() - 9);
			pagingDto.setEnd(tableInfoDto.getPageNum());
		}
		
		for(int i = 0; i < titleNm.length; ++i) {
			TitleDTO titleDto = new TitleDTO();
			titleDto.setTitleNm(titleNm[i]);
			titleList.add(titleDto);
		}
		
		rowNumUpdateDto.setPageNum(pageNum);
		rowNumUpdateDto.setTableNm(tableNm);
		rowNumUpdateDto.setRowNum(pagingConfigDto.getRowNum());
		
		model.addAttribute("titleList", titleList);
		model.addAttribute("contentList", movieInfoList);
		model.addAttribute("paging", pagingDto);
		model.addAttribute("pagingConfig", rowNumUpdateDto);
		
		return "admin/movie/like_num_list";
	}
	
	@GetMapping("/admin/actor_list/people_id/{pageNum}")
	public String peopleInfoListOrderBypeopleId(@PathVariable("pageNum") int pageNum, Model model) {
		
		String[] titleNm = {"NUM","ID", "배우명", "성별", "좋아요"};
		
		String tableNm = "people_info_detail";
		String orderByColumn = "people_id";
		PagingConfigDTO dto = new PagingConfigDTO();
		dto.setTableNm(tableNm);
		dto.setOrderByColumn(orderByColumn);
		PagingConfigDTO pagingConfigDto = autoPagingService.getPagingConfigByTableNmAndOrderByColumn(dto);
		
		
		TableInfoDTO tableInfoDto = service.getTableInfoDto(pagingConfigDto);
		
		/*
		 * 테이블 제목
		 */
		List<TitleDTO> titleList = new ArrayList<>();
		
		/*
		 * 테이블 내용
		 */
		List<PeopleInfoDTO> movieInfoList = service.getPeopleInfoList(pageNum, pagingConfigDto, tableInfoDto);
		
		/*
		 * 페이징 넘버
		 */
		PagingDTO pagingDto = new PagingDTO();
		
		/*
		 * 행 개수 Update할 때 필요
		 */
		RowNumUpdateDTO rowNumUpdateDto = new RowNumUpdateDTO();
		
		if(pageNum > 5 && pageNum < tableInfoDto.getPageNum() - 5) {
			pagingDto.setStart(pageNum-4);
			pagingDto.setEnd(pageNum+5);
			pagingDto.setLast(tableInfoDto.getPageNum());
		}else if(pageNum <= 5) {
			pagingDto.setStart(1);
			pagingDto.setEnd(10);
			pagingDto.setLast(tableInfoDto.getPageNum());
		}else {
			pagingDto.setStart(tableInfoDto.getPageNum() - 9);
			pagingDto.setEnd(tableInfoDto.getPageNum());
		}
		
		for(int i = 0; i < titleNm.length; ++i) {
			TitleDTO titleDto = new TitleDTO();
			titleDto.setTitleNm(titleNm[i]);
			titleList.add(titleDto);
		}
		
		rowNumUpdateDto.setPageNum(pageNum);
		rowNumUpdateDto.setTableNm(tableNm);
		rowNumUpdateDto.setRowNum(pagingConfigDto.getRowNum());
		
		model.addAttribute("titleList", titleList);
		model.addAttribute("contentList", movieInfoList);
		model.addAttribute("paging", pagingDto);
		model.addAttribute("pagingConfig", rowNumUpdateDto);
		
		return "admin/actor/list";
	}
	
	private boolean adminCertification() {
		boolean check = false;
		if(session.getAttribute("admin") == null) {
			check = true;
		}
		return check;
	}
	
	@Data
	public class PagingDTO{
		private int start;
		private int end;
		private int last;
	}
	
	@Data
	public class TitleDTO{
		private String titleNm;
	}
	
	@Data
	public class RowNumUpdateDTO{
		private String tableNm;
		private int rowNum;
		private int pageNum;
	}
}
