package com.watch.project.repository;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MemberDTO;

@Mapper
public interface MemberRepository {

	MemberDTO getUserInfoByEmail(String userEmail);

	int saveMemberInfo(MemberDTO dto);

	int deleteMemberInfo(String userEmail);

	MemberDTO getUserInfoBySocialId(String socialId);

	int updateMemberName(MemberDTO dto);

	int updateMemberInfo(MemberDTO dto);

	void updateKakaoAgreement(String userEmail);

	int updateNaverAgreement(String email);

	int updateGoogleAgreement(String email);

	void updateKakaoRefreshToken(MemberDTO userInfo);

//	String getKakaoRefreshToken(String email);

	void updateNaverRefreshToken(MemberDTO dtoForRefreshToken);


}
