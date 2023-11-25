package com.watch.project.repository.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.admin.BlackListDTO;
import com.watch.project.dto.admin.BlackListWaitingDTO;
import com.watch.project.dto.admin.MemberDTO;
import com.watch.project.dto.admin.ReportedCommentsDTO;
import com.watch.project.dto.admin.UserNotificationDTO;

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

	public List<BlackListWaitingDTO> getCurrentPageList(Map<String, Integer> map);

	public String getMovieNmByMovieId(String movieId);

	public String getCommentDateByCommentId(String commentId);

	public List<String> getReportedCommentIds(String email);

	public float getReviewScore(String commentId);

	public ReportedCommentsDTO getReportedCommentDatas(String commentId);

	public int deleteReportedCommentDataFormAdmin(String commentId);

	public List<BlackListDTO> getBlackListDtoForModal(String commentId);

	public int updateToBlack4BlackList(String email);

	public int updateToBlack4MovieReview(String email);

	public List<BlackListWaitingDTO> getBlackList();

	public int updateToWait4BlackList(String email);

	public int updateToWait4MovieReview(String email);


	public int updateToBlack4MemberInfo(String email);
	
	public int checkIfBlack(String email);

	public int updateToWait4MemberInfo(String email);

	public int insertNotification(UserNotificationDTO noti);

	public UserNotificationDTO getUserNotificationDtoByEmail(String userEmail);
	
	
}
