package com.watch.project.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
	
	public List<PeopleInfoDTO> getPeopleInfoList(int start, int end, PagingConfigDTO pagingConfigDto) {
		String tableNm = pagingConfigDto.getTableNm();
		String columns = pagingConfigDto.getColumns();
		String orderByColumn = pagingConfigDto.getOrderByColumn();
		Map<String, String> map = new HashMap<>();
		map.put("start", String.valueOf(start));
		map.put("end", String.valueOf(end));
		map.put("tableNm", tableNm);
		map.put("columns", columns);
		map.put("orderByColumn", orderByColumn);
		return repo.getPeopleInfoListByStartAndEnd(map);
	}
	
	public List<String> getTitleLList(String[] titleNm) {
		List<String> titleList = new ArrayList<>();
		for(int i = 0; i < titleNm.length; ++i) {
			titleList.add(titleNm[i]);
		}
		return titleList;
	}

	public String getConditionColumn(String[] conditionColumns, String query) {
		String conditionColumn = "";
		for(int i = 0; i < conditionColumns.length; ++i) {
			if(i != conditionColumns.length - 1) {
				conditionColumn += conditionColumns[i] + " LIKE '%"+query+"%' OR ";
			}else {
				conditionColumn += conditionColumns[i] + " LIKE '%"+query+"%'";
			}
		}
		return conditionColumn;
	}

	public List<PeopleInfoDTO> getPeopleInfoListQuery(int start, int end, PagingConfigDTO pagingConfigDto,
			String conditionColumn) {
		String tableNm = pagingConfigDto.getTableNm();
		String columns = pagingConfigDto.getColumns();
		String orderByColumn = pagingConfigDto.getOrderByColumn();
		Map<String, String> map = new HashMap<>();
		map.put("start", String.valueOf(start));
		map.put("end", String.valueOf(end));
		map.put("tableNm", tableNm);
		map.put("columns", columns);
		map.put("orderByColumn", orderByColumn);
		map.put("conditionColumn", conditionColumn);
		return repo.getPeopleInfoListByStartAndEndQuery(map);
	}
	
	public String insertPeopleInfo(PeopleInfoDTO peopleInfoDto) {
		String msg = "";
		
		msg = verification(peopleInfoDto);
		
		if(msg.equals("완료")) {
			repo.insertPeopleInfo(peopleInfoDto);
		}
		
		return msg;
	}
	public String updatePeopleInfo(PeopleInfoDTO peopleInfoDto) {
		String msg = "";
		
		msg = verification(peopleInfoDto);
		
		if(msg.equals("완료")) {
			repo.updatePeopleInfo(peopleInfoDto);
		}
		
		return msg;
	}
	
	public void deletePeopleInfo(int peopleId) {
		repo.deletePeopleInfoByPeopleId(peopleId);
	}

	private String verification(PeopleInfoDTO peopleInfoDto) {
		String msg = "완료";

		if(peopleInfoDto.getPeopleId() < 10000000) {
			msg = "아이디의 길이는 8이상이어야 합니다.";
		}else if(peopleInfoDto.getPeopleNm().equals("")) {
			msg = "배우명은 필수입니다.";
		}
		if(peopleInfoDto.getPeopleNmEn().equals("") || peopleInfoDto.getPeopleNmEn() == null) {
			peopleInfoDto.setPeopleNmEn("nan");
		}
		if(peopleInfoDto.getSex().equals("") || peopleInfoDto.getSex() == null) {
			peopleInfoDto.setSex("nan");
		}
		if(peopleInfoDto.getMovieId().equals("") || peopleInfoDto.getMovieId() == null) {
			peopleInfoDto.setMovieId("nan");
		}
		if(peopleInfoDto.getMovieNm().equals("") || peopleInfoDto.getMovieNm() == null) {
			peopleInfoDto.setMovieNm("nan");
		}
		if(peopleInfoDto.getProfileUrl().equals("") || peopleInfoDto.getProfileUrl() == null) {
			peopleInfoDto.setProfileUrl("nan");
		}
		return msg;
	}
}
