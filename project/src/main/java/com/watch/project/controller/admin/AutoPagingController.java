package com.watch.project.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watch.project.dto.admin.MovieInfoDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.dto.admin.PeopleInfoDTO;
import com.watch.project.service.admin.AutoPagingService;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AutoPagingController {
	private final MovieInfoController movieInfoController;
	private final PeopleInfoController peopleInfoController;
	private final MemberController memberController;
	private final AutoPagingService service;
	private final HttpSession session;
	
	@GetMapping("/admin/pagingConfig")
	public String pagingConfigView(Model model) {
		if(adminCertification()) {
			return "admin/login";
		}
		
		List<PagingConfigDTO> pagingConfigList = service.getAllPagingConfig();
		
		model.addAttribute("pagingConfig", pagingConfigList);
		
		return "admin/config/paging_config";
	}
	
	@GetMapping("/admin/deletePagingConfig")
	public String deletePagingConfig(String id, RedirectAttributes attr) {
		String msg = "삭제 완료";
		try {
			service.deletePagingConfig(id);			
		}catch(Exception e) {
			msg = "삭제 실패";
		}
		attr.addFlashAttribute("msg", msg);
		return "redirect:/admin/pagingConfig";
	}
	
	@PostMapping("/admin/updatePagingConfig")
	public String updatePagingConfig(PagingConfigDTO dto, RedirectAttributes attr) {
		String msg = "수정 완료";
		try {
			service.updatePagingConfig(dto);
		}catch(Exception e) {
			msg = "수정 실패";
		}
		attr.addFlashAttribute("msg", msg);
		return "redirect:/admin/pagingConfig";
	}
	
	@PostMapping("/admin/insertPagingConfig")
	public String insertPagingConfig(PagingConfigDTO dto, RedirectAttributes attr) {
		String msg = "등록 완료";
		try {
			service.insertPagingConfig(dto);
		}catch(Exception e) {
			msg = "등록 실패";
		}
		attr.addFlashAttribute("msg", msg);
		return "redirect:/admin/pagingConfig";
	}
	
	@PostMapping("/insert/{listNm}/{tableNm}/{orderByColumn}/{pageNum}/{query}")
	public String insertInfo(
			@PathVariable("listNm") String listNm,
			@PathVariable("tableNm") String tableNm, 
			@PathVariable("orderByColumn") String orderByColumn,
			@PathVariable("pageNum") int pageNum,
			@PathVariable("query") String query,
			MovieInfoDTO movieInfoDto,
			PeopleInfoDTO peopleInfoDto,
			RedirectAttributes attr) {
		
		String view = "";
		
		if(listNm.equals("movie_list")) {
			view = movieInfoController.insertMovieInfo(listNm, tableNm, orderByColumn, pageNum, query, movieInfoDto, attr);
		}else if(listNm.equals("actor_list")) {
			view = peopleInfoController.insertPeopleInfo(listNm, tableNm, orderByColumn, pageNum, query, peopleInfoDto, attr);
		}
		
		return view;
		
	}
	
	@GetMapping("/admin/{listNm}/{tableNm}/{orderByColumn}/{pageNum}")
	public String viewInfoList(
			@PathVariable("listNm") String listNm,
			@PathVariable("tableNm") String tableNm, 
			@PathVariable("orderByColumn") String orderByColumn,
			@PathVariable("pageNum") int pageNum,
			@RequestParam("query") String query, 
			Model model) {
		String view = "";
		if(listNm.equals("movie_list")) {
			view = movieInfoController.movieInfoList(listNm, tableNm, orderByColumn, pageNum, query, model);
		}else if(listNm.equals("actor_list")) {
			view = peopleInfoController.peopleInfoList(listNm, tableNm, orderByColumn, pageNum, query, model);
		}else if(listNm.equals("member_list")) {
			view = memberController.memberInfoList(listNm, tableNm, orderByColumn, pageNum, query, model);
		}
		return view;
	}
	
	@PostMapping("/update/{listNm}/{tableNm}/{orderByColumn}/{pageNum}/{query}")
	public String updateInfo(
			@PathVariable("listNm") String listNm,
			@PathVariable("tableNm") String tableNm, 
			@PathVariable("orderByColumn") String orderByColumn,
			@PathVariable("pageNum") int pageNum,
			@PathVariable("query") String query,
			PeopleInfoDTO peopleInfoDto,
			MovieInfoDTO movieInfoDto,
			RedirectAttributes attr) {
		
		String view = "";
		
		if(listNm.equals("movie_list")) {
			view = movieInfoController.updateMovieInfo(listNm, tableNm, orderByColumn, pageNum, query, movieInfoDto, attr);
		}else if(listNm.equals("actor_list")) {
			view = peopleInfoController.updatePeopleInfo(listNm, tableNm, orderByColumn, pageNum, query, peopleInfoDto, attr);
		}
		return view;
	}
	
	@GetMapping("/delete/{listNm}/{tableNm}/{orderByColumn}/{pageNum}/{query}")
	public String deleteInfo(
			@PathVariable("listNm") String listNm,
			@PathVariable("tableNm") String tableNm, 
			@PathVariable("orderByColumn") String orderByColumn,
			@PathVariable("pageNum") int pageNum,
			@PathVariable("query") String query,
			DeleteInfoDto deleteInfoDto,
			RedirectAttributes attr) {
		log.info("gd");
		String view = "";
		
		if(listNm.equals("movie_list")) {
			view = movieInfoController.deleteMovieInfo(listNm, tableNm, orderByColumn, pageNum, query, deleteInfoDto.getMovieId(), attr);
		}else if(listNm.equals("actor_list")) {
			view = peopleInfoController.deletePeopleInfo(listNm, tableNm, orderByColumn, pageNum, query, deleteInfoDto.getPeopleId(), attr);
		}
		return view;
	
	}
	
	private boolean adminCertification() {
		boolean check = false;
		if(session.getAttribute("admin") == null) {
			check = true;
		}
		return check;
	}
	
	@Data
	static class DeleteInfoDto{
		private String movieId;
		private int peopleId;
	}
}
