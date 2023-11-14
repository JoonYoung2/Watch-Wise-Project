package com.watch.project.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.watch.project.dto.admin.MovieInfoDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.repository.admin.AdminMovieInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("adminMovieInfoService")
@RequiredArgsConstructor
@Slf4j
public class MovieInfoService {
	private final AdminMovieInfoRepository repo;

	public List<MovieInfoDTO> getMovieInfoList(int start, int end, PagingConfigDTO pagingConfigDto) {
		String tableNm = pagingConfigDto.getTableNm();
		String columns = pagingConfigDto.getColumns();
		String orderByColumn = pagingConfigDto.getOrderByColumn();
		Map<String, String> map = new HashMap<>();
		map.put("start", String.valueOf(start));
		map.put("end", String.valueOf(end));
		map.put("tableNm", tableNm);
		map.put("columns", columns);
		map.put("orderByColumn", orderByColumn);
		return repo.getMovieInfoListByStartAndEnd(map);
	}
	
	public List<MovieInfoDTO> getMovieInfoListQuery(int start, int end, PagingConfigDTO pagingConfigDto,
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
		return repo.getMovieInfoListByStartAndEndQuery(map);
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

	
}
