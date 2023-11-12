package com.watch.project.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.watch.project.dto.admin.MovieInfoDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.dto.admin.PeopleInfoDTO;
import com.watch.project.dto.admin.TableInfoDTO;
import com.watch.project.repository.admin.AdminMovieInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("adminMovieInfoService")
@RequiredArgsConstructor
@Slf4j
public class MovieInfoService {
	private final AdminMovieInfoRepository repo;

	public TableInfoDTO getTableInfoDto(PagingConfigDTO pagingConfigDto) {
		return repo.getTableInfoByRowNumAndTableNm(pagingConfigDto);
	}

	public List<MovieInfoDTO> getMovieInfoList(int pageNum, PagingConfigDTO pagingConfigDto,
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
		return repo.getMovieInfoListByStartAndEnd(map);
	}
	
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
		log.info("{}", end);
		log.info("{}", start);
		log.info("{}", tableNm);
		log.info("{}", columns);
		log.info("{}", orderByColumn);
		return repo.getPeopleInfoListByStartAndEnd(map);
	}

	private int getStart(int end, int rowNum) {
		return end - rowNum + 1;
	}

	private int getEnd(int pageNum, int rowNum) {
		return pageNum * rowNum;
	}
}
