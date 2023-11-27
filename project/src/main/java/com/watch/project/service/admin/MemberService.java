package com.watch.project.service.admin;

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
import com.watch.project.dto.admin.BlackListWaitingDTO;
import com.watch.project.dto.admin.MemberDTO;
import com.watch.project.dto.admin.PagingConfigDTO;
import com.watch.project.dto.admin.ReportedCommentsDTO;
import com.watch.project.dto.admin.UserNotificationDTO;
import com.watch.project.repository.MemberRepository;
import com.watch.project.repository.admin.AdminMemberRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class MemberService {
	@Autowired private AdminMemberRepository repo;
	@Autowired private HttpSession session;
	@Autowired private MemberRepository memberRepo;
	
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

	
	public String saveReport(String authorEmail, String comment, String commentId, String movieId, String reason) {
		String msg = "해당 댓글이 신고되었습니다.";
		int result = 0;
		String email = (String) session.getAttribute("userEmail");
		String id = movieId + authorEmail + email;
        // 날짜 데이터
		Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(currentDate);
        // movie_nm 가져오기
        String movieNm = repo.getMovieNmByMovieId(movieId);
        //comment_written_date 가져오기
        String commentDate = repo.getCommentDateByCommentId(commentId);
        //review_score 가져오기
        float score = repo.getReviewScore(commentId);
        
		BlackListDTO dto =
				BlackListDTO.builder()
				.id(id)
				.reportedComment(comment)
				.reportedCommentId(commentId)
				.authorEmail(authorEmail)
				.reporterEmail(email)
				.reasonForReport(reason)
				.reportedDate(formattedDate)
				.movieNm(movieNm)
				.commentWrittenDate(commentDate)
				.movieId(movieId)
				.reviewScore(result)
				.build();
		 try {
		        BlackListDTO db = repo.checkIfExist(dto);
		        if (db != null) {
		            // 이미 저장된 데이터가 있는 경우 업데이트
		            result = repo.updateReportDatas(dto);
		        } else {
		            // 저장된 데이터가 없는 경우 새로운 데이터 저장
		            result = repo.saveReportDatas(dto);
		        }
		    } catch (NullPointerException e) {
		        // checkIfExist 메서드에서 예외 발생 시, 저장된 데이터가 없다고 간주하고 새로운 데이터 저장
		        result = repo.saveReportDatas(dto);
		    }

		if(result != 1) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return msg;
	}

	public String deleteReportedDatas(String movieId, String author) {
		
		int result = 0;
		String msg ="신고가 취소되었습니다.";
		String email = (String) session.getAttribute("userEmail");
		String id = movieId + author + email;

		BlackListDTO dto = 
				BlackListDTO.builder()
				.id(id).build();
		
		 try {
		        BlackListDTO db = repo.checkIfExist(dto);
		        if (db != null) {
		            // 이미 저장된 데이터가 있는 경우 삭제
		            result = repo.deleteReportedDatas(dto);
		        } else {
		            // 저장된 데이터가 없는 경우
		            msg = "아직 신고하지 않은 댓글1111111입니다.";///////
		        }
		    } catch (NullPointerException e) {
		        // checkIfExist 메서드에서 예외 발생 시, 저장된 데이터가 없다고 간주
	            msg = "아직 신고하지 않은 댓글22222222입니다.";
		    }
		return msg;
	}

	public List<BlackListWaitingDTO> getBlackListWaiting() {
		List<BlackListWaitingDTO> result = repo.getBlackListDTO();
		return result;
	}

	public List<BlackListWaitingDTO> getCurrentPageList(int currentPage, int total, int isBlack) {
		int startRow = (15*currentPage)-14;
		int endRow = startRow + 14;
		int totalRows = total;
		
		Map<String, Integer> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("totalRows", totalRows);
		map.put("isBlack", isBlack);
		
		List<BlackListWaitingDTO> result = repo.getCurrentPageList(map);

		return result;
	}

	public List<ReportedCommentsDTO> getReportedComments(String email) {
		List<ReportedCommentsDTO> finalList = new ArrayList<>();
		List<String> commentIds = repo.getReportedCommentIds(email);
		//movie_nm, reported_comment, comment_written_date, reported_amount
		for(String commentId : commentIds) {
			log.info("id => {}", commentId);
			ReportedCommentsDTO result = repo.getReportedCommentDatas(commentId); 
			result.setCommentId(commentId);
			result.setBlackListDto(repo.getBlackListDtoForModal(commentId));
			for(BlackListDTO list : result.getBlackListDto()) {
				log.info("here is where I shoud See => {}", list.getReasonForReport());				
			}
			finalList.add(result);
		}
		return finalList;
	}

	public String deleteReportedCommentDataFormAdmin(String commentId) {
		String msg = "해당 데이터가 삭제되었습니다";
		int result = repo.deleteReportedCommentDataFormAdmin(commentId);
		if(result != 1) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return msg;
	}

	public String updateToBlack(String email) {
		String msg=email+"님이 블랙리스트로 등록되었습니다";
		int resultOfBlackList = repo.updateToBlack4BlackList(email);
		int resultOfMovieReview = repo.updateToBlack4MovieReview(email);
		int resultOfMemberInfo = repo.updateToBlack4MemberInfo(email);
		if(resultOfBlackList==0 || resultOfMovieReview==0 ||resultOfMemberInfo==0) {
			msg="오류가 발생했습니다. 다시 시도해주세요.";
		}
		return msg;
	}

	public List<BlackListWaitingDTO> getBlackList() {
		List<BlackListWaitingDTO> result = repo.getBlackList();
		return result;
	}

	public String deleteFromBlackList(String email) {
		String msg=email+"님이 블랙리스트에서 해제되었습니다";
		int result4BlackList = repo.updateToWait4BlackList(email);
		int result4MovieReview = repo.updateToWait4MovieReview(email);
		int result4MemberInfo = repo.updateToWait4MemberInfo(email);
		if(result4BlackList == 0 || result4MovieReview == 0 || result4MemberInfo ==0) {
			msg="오류가 발생했습니다. 다시 시도해주세요.";
		}
		return msg;
	}

	public int checkIfBlack(String email) {
		int isBlack = repo.checkIfBlack(email);
		return isBlack;
	}

	/*
	 * 
	 * 	reciever_email varchar2(255),
    	reciever_name varchar2(255),
	sender_email varchar2(255),
   	sender_name varchar2(255),
	notification_content varchar2(1000),
	inserted_date  TIMESTAMP(6),
	is_seen number default 0
	 */
	public void giveNotificationToUser(String authorEmail, String content) {
		UserNotificationDTO noti = new UserNotificationDTO();
		//유저이름 가져오기
		String userNm = memberRepo.getUserNmByEmail(authorEmail);
		
		//날짜데이터 생성
		Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(currentDate);
        
        noti.setRecieverEmail(authorEmail);
        noti.setRecieverName(userNm);
        noti.setSenderEmail("WatchWise");
        noti.setSenderName("WatchWise");
        noti.setNotificationContent(content);
		noti.setInsertedDate(formattedDate);
		noti.setIsSeen(0);
		noti.setIsClicked(0);
		
		int result = repo.insertNotification(noti);
		System.out.println("result ===>"+result);
	}
	public void giveNotificationToUserForComment(String authorEmail, String comment) {
		UserNotificationDTO noti = new UserNotificationDTO();
		//유저이름 가져오기
		String userNm = memberRepo.getUserNmByEmail(authorEmail); //알림 받을 사람
		System.out.println("여기는 알림 받을 유저의 이메일 ==>"+ authorEmail);
		//알림 내용 설정
		String nmWhoClickedHeart = memberRepo.getUserNmByEmail((String)session.getAttribute("userEmail"));//좋아요 누른 사람
		System.out.println("여기는 좋아요 누른 유저의 이름 ==>"+ nmWhoClickedHeart);

		String content = "<b>"+nmWhoClickedHeart + "님이 회원님의 코멘트를 좋아합니다.</b> <br>"+comment;
		//날짜데이터 생성
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = sdf.format(currentDate);
		
		noti.setRecieverEmail(authorEmail);
		noti.setRecieverName(userNm);
		noti.setSenderEmail((String)session.getAttribute("userEmail"));
		noti.setSenderName(nmWhoClickedHeart);
		noti.setNotificationContent(content);
		noti.setInsertedDate(formattedDate);
		noti.setIsSeen(0);
		noti.setIsClicked(0);
		
		int result = repo.insertNotification(noti);
		System.out.println("result ===>"+result);
	}

	public int checkIfNewNoti(String userEmail) {
		int result = 0;
		try {
			List<UserNotificationDTO> dto = repo.getUserNotificationDtoByEmail(userEmail);
			if(dto.size() == 0) { 
				System.out.println("dto가 널임");
				result = 0;
			}else {
				System.out.println("dto가 널이 아님");
				result = 1;
			}
		}catch(NullPointerException e) {
			System.out.println("exception에 걸림");
			result = 0;
		}
		return result;
	}




}
