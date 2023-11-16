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
import com.watch.project.dto.admin.MemberDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.dto.admin.TableInfoDTO;
import com.watch.project.service.admin.AutoPagingService;
import com.watch.project.service.admin.MemberService;

@Controller
public class MemberContorller {
	@Autowired 
	private MemberService service;
	@Autowired
	private HttpSession session;
	@Autowired
	private AutoPagingService autoPagingService;
	
	//---------------------------------------------------------------------------------------------------------------------
	@GetMapping("/admin/member_info/id/{pageNum}")
	public String memberInfoList(@PathVariable("pageNum") int pageNum, @RequestParam("query") String query, Model model) {
		if(adminCertification()) {
			return "/admin/login";
		}
		String[] titleNm = {"NUM", "ID", "EMAIL", "PW", "NAME", "KAKAO", "NAVER", "GOOGLE"};
		
		/*
		 * 쿼리 추가하고 싶을 때 DB column명
		 */
		String[] conditionColumns= {"id" ,"user_email", "user_name"};
		
		String conditionColumn = service.getConditionColumn(conditionColumns, query);
		
		String tableNm = "member_info";
		String orderByColumn = "id";
		
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
		
		autoPagingDto.setAutoPagingDto(tableNm, pageNum, rowNum, orderByColumn, titleList, tableInfoDto);
		
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
	@GetMapping("/reportComment")
	public String reportComment(@RequestParam("author") String authorEmail, @RequestParam("comment") String comment, @RequestParam("movieId") String movieId, RedirectAttributes redirectAttr) {
		String msg = service.saveReport(authorEmail, comment, movieId);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/movieInfo?movieId="+ movieId;
	}
	
	@GetMapping("/deleteMember")
	public String deleteMember(@RequestParam("userEmail") String userEmail, @RequestParam("pageNum") String pageNum, RedirectAttributes redirectAttr) {
		String msg = service.deleteMemberByEmail(userEmail);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/admin/member_info/id/" + pageNum + "?query=";
	}
}
