package com.watch.project.repository;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MemberDTO;

@Mapper
public interface MemberRepository {

	MemberDTO getUserInfoById(String id);

	int saveMemberInfo(MemberDTO dto);

	int deleteMemberInfo(String userId);

}
