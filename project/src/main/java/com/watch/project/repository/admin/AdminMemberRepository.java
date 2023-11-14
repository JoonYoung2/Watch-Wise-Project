package com.watch.project.repository.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.admin.MemberDTO;

@Mapper
public interface AdminMemberRepository {
	
	public List<MemberDTO> getMemberInfoListByStartAndEnd(Map<String, String> map);
	
	public List<MemberDTO> getMemberInfoListByStartAndEndQuery(Map<String, String> map);
	
}
