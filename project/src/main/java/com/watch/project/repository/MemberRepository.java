package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MemberDTO;
import com.watch.project.dto.admin.UserNotificationDTO;

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

	String getUserNmByEmail(String authorEmail);

	List<UserNotificationDTO> getNotificationListByEmail(String email);

	void setIsSeenForList(String email);

	void updateProfile(Map<String, String> map);

	String getProfileImgByEamil(String userEmail);


}
