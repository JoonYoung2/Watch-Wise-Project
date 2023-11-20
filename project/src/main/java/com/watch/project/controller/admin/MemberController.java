package com.watch.project.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.admin.AutoPagingDTO;
import com.watch.project.dto.admin.BlackListDTO;
import com.watch.project.dto.admin.BlackListWaitingDTO;
import com.watch.project.dto.admin.MemberDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.dto.admin.PagingDTO;
import com.watch.project.dto.admin.TableInfoDTO;
import com.watch.project.service.admin.AutoPagingService;
import com.watch.project.service.admin.MemberService;

@Controller
public class MemberController {
	@Autowired 
	private MemberService service;
	@Autowired
	private HttpSession session;
	@Autowired
	private AutoPagingService autoPagingService;
	
	//---------------------------------------------------------------------------------------------------------------------
	
	public String memberInfoList(String listNm, String tableNm, String orderByColumn, int pageNum, String query, Model model) {
		
		if(adminCertification()) {
			return "/admin/login";
		}
		String[] titleNm = {"NUM", "ID", "EMAIL", "PW", "NAME", "KAKAO", "NAVER", "GOOGLE"};
		
		/*
		 * 쿼리 추가하고 싶을 때 DB column명
		 */
		String[] conditionColumns= {"id" ,"user_email", "user_name"};
		
		String conditionColumn = service.getConditionColumn(conditionColumns, query);
		
		TableInfoDTO tableInfoDto = null;
		List<MemberDTO> memberInfoList = null;
		
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
			memberInfoList = service.getMemberInfoList(start, end, pagingConfigDto);			
		}else {
			memberInfoList = service.getMemberInfoListQuery(start, end, pagingConfigDto, conditionColumn);	
		}
		 
		autoPagingDto.setAutoPagingDto("member_list", tableNm, pageNum, rowNum, orderByColumn, titleList, tableInfoDto);
		
		model.addAttribute("contentList", memberInfoList);
		model.addAttribute("autoPaging", autoPagingDto);
		model.addAttribute("query", query);
		
		return "admin/member/member_list";
	}
	private boolean adminCertification() {
		boolean check = false;
		if(session.getAttribute("admin") == null) {
			check = true;
		}
		return check;
	} 
	
	//-----------------------------------------------------------------------------------------------------------------
//	@GetMapping("/reportComment")
//	public String reportComment(@RequestParam("author") String authorEmail, @RequestParam("comment") String comment, @RequestParam("movieId") String movieId, RedirectAttributes redirectAttr) {
//		String msg = service.saveReport(authorEmail, comment, movieId);
//		redirectAttr.addFlashAttribute("msg", msg);
//		return "redirect:/movieInfo?movieId="+ movieId;
//	}
	
	@GetMapping("/deleteMember")
	public String deleteMember(@RequestParam("userEmail") String userEmail, @RequestParam("pageNum") String pageNum, RedirectAttributes redirectAttr) {
		String msg = service.deleteMemberByEmail(userEmail);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/admin/member_info/id/" + pageNum + "?query=";
	}
	
	/*
	 * 	// http://localhost:8090/lprod/list?currentPage=1
	// defaultValue : 해당 요청 파라미터를 지정하지 않을 경우
	// defaultValue 속성에 지정한 문자열을 값으로 이용하게 됨
	@RequestMapping("/list")
	public String list(Model model, @RequestParam(defaultValue="1") int currentPage) {
		List<LprodVO> list = this.lprodService.list();

		// 상품 분류별 거래처 목록 행의 수
		int total = this.lprodService.listCount();
		
 전체글 개수, 현재페이지, 한 화면에 출력해 줄 글 개수, 한 화면에 보여주는 페이징 숫자 개수, 전체글 리스트
		model.addAttribute("list", new ArticlePage(total, currentPage, 7, 5, list));
		
		model.addAttribute("total", total);
		
		// forward
		return "lprod/list";
	}
	 */
	@GetMapping("/admin/black_list_waiting")
	public String blackListWaiting(Model model, @RequestParam(defaultValue="1") int currentPage) {
		List<BlackListWaitingDTO> list = service.getBlackListWaiting();
		int total = list.size();
		model.addAttribute("list", new PagingDTO(total, currentPage, 10, 5, list));
		model.addAttribute("total", total);
		model.addAttribute("contentList", list);
		return "admin/member/black_list_waiting";
	}
}
