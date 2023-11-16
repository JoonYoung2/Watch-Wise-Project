package com.watch.project.service.admin;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.project.dto.admin.BlackListDTO;
import com.watch.project.dto.admin.MemberDTO;
import com.watch.project.dto.admin.MovieInfoDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.repository.admin.AdminMemberRepository;

import oracle.jdbc.OracleDatabaseException;

@Service
public class MemberService {
	@Autowired private AdminMemberRepository repo;
	@Autowired private HttpSession session;
	
	public List<MemberDTO> getMemberInfoList(int start, int end, PagingConfigDTO pagingConfigDto) {
		String tableNm = pagingConfigDto.getTableNm();
		String columns = pagingConfigDto.getColumns();
		String orderByColumn = pagingConfigDto.getOrderByColumn();
		Map<String, String> map = new HashMap<>();
		map.put("start", String.valueOf(start));
		map.put("end", String.valueOf(end));
		map.put("tableNm", tableNm);
		map.put("columns", columns);
		map.put("orderByColumn", orderByColumn);
		return repo.getMemberInfoListByStartAndEnd(map);
	}
	
	public List<MemberDTO> getMemberInfoListQuery(int start, int end, PagingConfigDTO pagingConfigDto,
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
		return repo.getMemberInfoListByStartAndEndQuery(map);
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

	public String deleteMemberByEmail(String userEmail) {
		String msg = "삭제되었습니다.";
		int result = repo.deleteMember(userEmail);
		if(result != 1) {
			msg = "오류가 발생했습니다.";
		}
		return msg;
	}

	public String saveReport(String authorEmail, String comment, String movieId) {
		String msg = "해당 댓글이 신고되었습니다.";
		int result = 0;
		String email = (String) session.getAttribute("userEmail");
		String id = movieId + authorEmail + email;
		
        // 날짜 데이터
		Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(currentDate);
        
		BlackListDTO dto = BlackListDTO.builder()
				.id(id)
				.reportedComment(comment)
				.authorEmail(authorEmail)
				.reporterEmail(email)
				.reportedDate(formattedDate).build();
		try {
			result = repo.saveReportDatas(dto);			
		}catch(Exception e) {
			msg="이미 신고한 댓글입니다.";
		}
		if(result != 1) {
			msg = "이미 신고한 댓글입니다.";
		}
		return msg;
	}
}
