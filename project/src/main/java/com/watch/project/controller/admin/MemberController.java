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
import com.watch.project.dto.admin.ReportedCommentsDTO;
import com.watch.project.dto.admin.TableInfoDTO;
import com.watch.project.dto.admin.UserNotificationDTO;
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

	
	@GetMapping("/deleteMember")
	public String deleteMember(@RequestParam("userEmail") String userEmail, @RequestParam("pageNum") String pageNum, RedirectAttributes redirectAttr) {
		String msg = service.deleteMemberByEmail(userEmail);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/admin/member_info/id/" + pageNum + "?query=";
	}
	

	@GetMapping("/admin/black_list_waiting")
	public String blackListWaiting(Model model, @RequestParam("currentPage") int currentPage) {
		System.out.println("controller로 옴. crrentPage --> "+currentPage);
		List<BlackListWaitingDTO> list = service.getBlackListWaiting();
		int total = list.size();
		List<BlackListWaitingDTO> currentPageList = service.getCurrentPageList(currentPage, total, 0);
		model.addAttribute("list", new PagingDTO(total, currentPage, 15, 10, list));
		model.addAttribute("total", total);
		model.addAttribute("contentList", list);
		model.addAttribute("showDatas", currentPageList);
		model.addAttribute("pageNum", currentPage);
		return "admin/member/black_list_waiting";
	}
	
	@GetMapping("/admin/showReportedComments")
	public String showReportedComments(@RequestParam("authorEmail") String email, @RequestParam("pageNum") int pageNum, Model model) {
		List<ReportedCommentsDTO> result = service.getReportedComments(email);
		model.addAttribute("commentList", result);
		model.addAttribute("email", email);
		model.addAttribute("pageNum", pageNum);
		return "admin/member/reported_comments";
	}
	
	@GetMapping("/admin/deleteReportedData")
	public String deleteReportedData(@RequestParam("commentId") String commentId, @RequestParam("email") String email, @RequestParam("pageNum") int pageNum, RedirectAttributes redirectAttr) {
		System.out.println("########################"+commentId);
		String msg = service.deleteReportedCommentDataFormAdmin(commentId);
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/admin/showReportedComments?authorEmail="+email+"&pageNum="+pageNum;
	}
	
	@GetMapping("/admin/addToBlackList")
	public String addToBlackList (@RequestParam("author") String authorEmail, @RequestParam("currentPage") int currentPage, RedirectAttributes redirectAttr) {
		String msg = service.updateToBlack(authorEmail);
		service.giveNotificationToUser(authorEmail, "부적절한 댓글 기능 사용으로 신고가 접수되었으며, 관리자 판단 결과, 더 이상 댓글 기능을 이용할 수 없음을 알립니다.");
		if(currentPage == 5000) {
			return "redirect:/admin";
		}
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/admin/black_list_waiting?currentPage="+currentPage;
	}
		
	@GetMapping("/admin/black_list")
	public String blackList(@RequestParam("currentPage") int pageNum, Model model) {
		List<BlackListWaitingDTO> list = service.getBlackList();
		int total = list.size();
		List<BlackListWaitingDTO> currentPageList = service.getCurrentPageList(pageNum, total, 1);
		model.addAttribute("list", new PagingDTO(total, pageNum, 15, 10, list));
		model.addAttribute("total", total);
		model.addAttribute("contentList", list);
		model.addAttribute("showDatas", currentPageList);
		model.addAttribute("pageNum", pageNum);
		return "admin/member/black_list";
	}
	
	@GetMapping("/admin/deleteFromBlackList")
	public String deleteFromBlackList(@RequestParam("author") String userEmail, @RequestParam("currentPage") int pageNum, RedirectAttributes redirectAttr) {
		String msg = service.deleteFromBlackList(userEmail);
		service.giveNotificationToUser(userEmail, "관리자 검토 결과, 회원님은 댓글 기능 사용을 다시 시작할 수 있습니다.");
		redirectAttr.addFlashAttribute("msg", msg);
		return "redirect:/admin/black_list?currentPage="+pageNum;
	}
	

}
