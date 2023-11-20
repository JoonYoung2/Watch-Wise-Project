package com.watch.project.repository.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.admin.BlackListDTO;
import com.watch.project.dto.admin.BlackListWaitingDTO;
import com.watch.project.dto.admin.MemberDTO;

@Mapper
public interface AdminMemberRepository {
	
	public List<MemberDTO> getMemberInfoListByStartAndEnd(Map<String, String> map);
	
	public List<MemberDTO> getMemberInfoListByStartAndEndQuery(Map<String, String> map);

	public int deleteMember(String userEmail);

	public int saveReportDatas(BlackListDTO dto);

	public BlackListDTO checkIfExist(BlackListDTO dto);

	public int updateReportDatas(BlackListDTO dto);

	public int deleteReportedDatas(BlackListDTO dto);

	public BlackListDTO checkIfReported(String commentIdPlusUserEmail);

	public List<BlackListWaitingDTO> getBlackListDTO();
	
	
}
