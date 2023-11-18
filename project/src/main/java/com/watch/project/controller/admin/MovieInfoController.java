package com.watch.project.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	private final MovieInfoService service;
	private final AutoPagingService autoPagingService;
	
	public String movieInfoList(String listNm, String tableNm, String orderByColumn, int pageNum, String query, Model model) { 
		if(autoPagingService.adminCertification()) {
			return "/admin/login";
		}
		
		/*
		 * 뷰에 있는 테이블 제목 (수정)
		 */
		String[] titleNm = {"NUM", "ID", "영화명", "개봉일", "좋아요"};
		
		/*
		 * 검색 조건을 위한 컬럼 명들 (수정)
		 */
		String[] conditionColumns= {"movie_id", "movie_nm"};
		
		/*
		 * 뷰에 있는 테이블 내용 List (수정)
		 */
		List<MovieInfoDTO> movieInfoList = null;
		
		/*
		 * 전체 행의 수, 페이지 수, 마지막 페이지 행의 수
		 */
		TableInfoDTO tableInfoDto = null;
		
		PagingConfigDTO dto = new PagingConfigDTO();
		dto.setTableNm(tableNm);
		dto.setOrderByColumn(orderByColumn);
		
		/*
		 * 테이블 이름과 오름차순할 컬럼 이름을 주면
		 * DB에 설정된 정보를 가져옴
		 * 이것을 이용해서 뷰에 뿌려질 행의 개수와 Start와 End를 알 수 있음
		 */
		PagingConfigDTO pagingConfigDto = autoPagingService.getPagingConfigByTableNmAndOrderByColumn(dto);
		
		String conditionColumn = service.getConditionColumn(conditionColumns, query);
		
		
		/*
		 * if) 검색이 없을때의 전체 행의 수, 페이지 수, 마지막 페이지 행의 수 정보
		 * else) 검색이 있을 때의 전체 행의 수, 페이지 수, 마지막 페이지 행의 수 정보
		 */
		if(query.equals("")) 
			tableInfoDto = autoPagingService.getTableInfoDto(pagingConfigDto);
		else {
			pagingConfigDto.setConditionColumn(conditionColumn);
			
			tableInfoDto = autoPagingService.getTableInfoDtoQuery(pagingConfigDto);
		}
		
		/*
		 * Model에 담겨질 페이징 테이블에 담겨질 모든 정보
		 * 테이블 타이틀, 경로이름(listNm, tableNm, orderByColumn), 
		 * paging 시작 페이지 번호, 마지막 페이지 번호, 행 수, 현재 페이지 번호, 
		 * 최종 페이지 번호
		 */
		AutoPagingDTO autoPagingDto = new AutoPagingDTO();
		
		int rowNum = pagingConfigDto.getRowNum();
		int end = autoPagingDto.getEnd(pageNum, rowNum);
		int start = autoPagingDto.getStart(end, rowNum);
		List<String> titleList = service.getTitleLList(titleNm);
		
		/*
		 * 뷰에 있는 테이블 내용 List
		 * if) 검색 조건이 없을 때
		 * else) 검색 조건이 있을 때
		 * 메서드 수정
		 */
		if(query.equals("")) {
			movieInfoList = service.getMovieInfoList(start, end, pagingConfigDto);			
		}else {
			movieInfoList = service.getMovieInfoListQuery(start, end, pagingConfigDto, conditionColumn);	
		}
		
		autoPagingDto.setAutoPagingDto(listNm, tableNm, pageNum, rowNum, orderByColumn, titleList, tableInfoDto);
		
		/*
		 * 사용자가 검색한 내용
		 */
		query = query.replaceAll("''", "'");
		
		model.addAttribute("contentList", movieInfoList);
		model.addAttribute("autoPaging", autoPagingDto);
		model.addAttribute("query", query);
		
		return "admin/movie/list";
	}

	public String insertMovieInfo(String listNm, String tableNm, String orderByColumn, int pageNum, String query, MovieInfoDTO movieInfoDto,
			RedirectAttributes attr) {
		
		if(autoPagingService.adminCertification()) {
			return "/admin/login";
		}
		
		query = autoPagingService.getQuery(query);
		
		String msg = "";
		
		msg = service.insertMovieInfo(movieInfoDto);
		
		if(msg.equals("완료")) {
			msg = "등록 완료";
		}
		
		attr.addFlashAttribute("msg", msg);
		
		String view = autoPagingService.getCreateAndUpdateAndDeleteView(listNm, tableNm, orderByColumn, pageNum, query);
		
		return view;
	}
	
	public String updateMovieInfo(String listNm, String tableNm, String orderByColumn, int pageNum, String query, MovieInfoDTO movieInfoDto, 
			RedirectAttributes attr) {
		
		if(autoPagingService.adminCertification()) {
			return "/admin/login";
		}
		
		query = autoPagingService.getQuery(query);
		
		String msg = "";
		
		msg = service.updateMovieInfo(movieInfoDto);
		
		if(msg.equals("완료")) {
			msg = "수정 완료";
		}
		
		attr.addFlashAttribute("msg", msg);
		
		String view = autoPagingService.getCreateAndUpdateAndDeleteView(listNm, tableNm, orderByColumn, pageNum, query);
		
		return view;
	}
	
	public String deleteMovieInfo(String listNm, String tableNm, String orderByColumn, int pageNum, String query, String movieId, 
			RedirectAttributes attr) {
		
		if(autoPagingService.adminCertification()) {
			return "/admin/login";
		}
		
		query = autoPagingService.getQuery(query);
		
		String msg = "삭제 완료";
		
		service.deleteMovieInfo(movieId);
		
		attr.addFlashAttribute("msg", msg);
		
		String view = autoPagingService.getCreateAndUpdateAndDeleteView(listNm, tableNm, orderByColumn, pageNum, query);
		
		return view;
	}	
}
