package com.watch.project.repository;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MemberDTO;

@Mapper
public interface MemberRepository {

	MemberDTO getUserInfoByEmail(String userEmail);

	int saveMemberInfo(MemberDTO dto);

	int deleteMemberInfo(String userEmail);

	MemberDTO getUserInfoBySocialId(String socialId);

}
