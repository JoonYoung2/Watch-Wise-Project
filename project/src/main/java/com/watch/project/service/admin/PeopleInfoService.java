package com.watch.project.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.watch.project.controller.admin.AutoPagingController.PagingDTO;
import com.watch.project.controller.admin.AutoPagingController.TitleDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.dto.admin.PeopleInfoDTO;
import com.watch.project.dto.admin.TableInfoDTO;
import com.watch.project.repository.admin.AdminPeopleInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("adminPeopleInfoService")
@RequiredArgsConstructor
@Slf4j
public class PeopleInfoService {
	private final AdminPeopleInfoRepository repo;
	
	public List<PeopleInfoDTO> getPeopleInfoList(int pageNum, PagingConfigDTO pagingConfigDto,
			TableInfoDTO tableInfoDto) {
		int end = getEnd(pageNum, pagingConfigDto.getRowNum());
		int start = getStart(end, pagingConfigDto.getRowNum());
		String tableNm = pagingConfigDto.getTableNm();
		String columns = pagingConfigDto.getColumns();
		String orderByColumn = pagingConfigDto.getOrderByColumn();
		Map<String, String> map = new HashMap<>();
		map.put("end", end + "");
		map.put("start", start + "");
		map.put("tableNm", tableNm);
		map.put("columns", columns);
		map.put("orderByColumn", orderByColumn);
		return repo.getPeopleInfoListByStartAndEnd(map);
	}
	
	public List<TitleDTO> getTitleLList(String[] titleNm) {
		List<TitleDTO> titleList = new ArrayList<>();
		for(int i = 0; i < titleNm.length; ++i) {
			TitleDTO titleDto = new TitleDTO();
			titleDto.setTitleNm(titleNm[i]);
			titleList.add(titleDto);
		}
		return titleList;
	}
	
	public PagingDTO getPagingDto(int pageNum, TableInfoDTO tableInfoDto) {
		PagingDTO pagingDto = new PagingDTO();
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
		return pagingDto;
	}

	private int getStart(int end, int rowNum) {
		return end - rowNum + 1;
	}

	private int getEnd(int pageNum, int rowNum) {
		return pageNum * rowNum;
	}
}
