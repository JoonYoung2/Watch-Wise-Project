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

	public String insertMovieInfo(MovieInfoDTO movieInfoDto) {
		String msg = "";
		
		msg = verification(movieInfoDto);
		
		if(msg.equals("완료")) {
			repo.insertMovieInfo(movieInfoDto);
		}
		
		return msg;
	}
	public String updateMovieInfo(MovieInfoDTO movieInfoDto) {
		String msg = "";
		
		msg = verification(movieInfoDto);
		
		if(msg.equals("완료")) {
			repo.updateMovieInfo(movieInfoDto);
		}
		
		return msg;
	}
	
	public void deleteMovieInfo(String movieId) {
		repo.deleteMovieInfoByMovieId(movieId);
	}

	private String verification(MovieInfoDTO movieInfoDto) {
		String msg = "완료";
		
		if(movieInfoDto.getMovieId().length() < 8) {
			msg = "아이디의 길이는 8이상이어야 합니다.";
		}else if(movieInfoDto.getMovieNm().equals("")) {
			msg = "영화명은 필수입니다.";
		}
		if(movieInfoDto.getMovieNmEn().equals("") || movieInfoDto.getMovieNmEn() == null) {
			movieInfoDto.setMovieNmEn("nan");
		}
		if(movieInfoDto.getPrdtYear().equals("") || movieInfoDto.getPrdtYear() == null) {
			movieInfoDto.setPrdtYear("nan");
		}
		if(movieInfoDto.getOpenDt().equals("") || movieInfoDto.getOpenDt() == null) {
			movieInfoDto.setOpenDt("nan");
		}
		if(movieInfoDto.getTypeNm().equals("") || movieInfoDto.getTypeNm() == null) {
			movieInfoDto.setTypeNm("nan");
		}
		if(movieInfoDto.getNations().equals("") || movieInfoDto.getNations() == null) {
			movieInfoDto.setNations("nan");
		}
		if(movieInfoDto.getGenreNm().equals("") || movieInfoDto.getGenreNm() == null) {
			movieInfoDto.setGenreNm("nan");
		}
		if(movieInfoDto.getPosterUrl().equals("") || movieInfoDto.getPosterUrl() == null) {
			movieInfoDto.setPosterUrl("nan");
		}
		if(movieInfoDto.getActors().equals("") || movieInfoDto.getActors() == null) {
			movieInfoDto.setActors("nan");
		}
		if(movieInfoDto.getCast().equals("") || movieInfoDto.getCast() == null) {
			movieInfoDto.setCast("nan");
		}
		if(movieInfoDto.getWatchGradeNm().equals("") || movieInfoDto.getWatchGradeNm() == null) {
			movieInfoDto.setWatchGradeNm("nan");
		}
		return msg;
	}

	

	

	
}
