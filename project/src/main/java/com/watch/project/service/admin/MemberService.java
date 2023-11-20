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
import com.watch.project.repository.admin.AdminMemberRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
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

	
	public String saveReport(String authorEmail, String comment, String commentId, String movieId, String reason) {
		String msg = "해당 댓글이 신고되었습니다.";
		int result = 0;
		String email = (String) session.getAttribute("userEmail");
		String id = movieId + authorEmail + email;
		System.out.println("service 단 id: "+id);
		System.out.println("service 단 reason : "+reason);
        // 날짜 데이터
		Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(currentDate);
        // movie_nm 가져오기
        String movieNm = repo.getMovieNmByMovieId(movieId);
        //comment_written_date 가져오기
        String commentDate = repo.getCommentDateByCommentId(commentId);
        System.out.println("commentDate ==>:"+commentDate);
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
		log.info("여기야1 author===>{}", author);
		log.info("여기야2 email===>{}", email);

		BlackListDTO dto = 
				BlackListDTO.builder()
				.id(id).build();
		log.info("여기야3 ===>{}", dto.getId());
		
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

	public List<BlackListWaitingDTO> getCurrentPageList(int currentPage, int total) {
		int startRow = (15*currentPage)-14;
		int endRow = startRow + 14;
		int totalRows = total;
		
		Map<String, Integer> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("totalRows", totalRows);
		
		List<BlackListWaitingDTO> result = repo.getCurrentPageList(map);
		
		for(BlackListWaitingDTO log : result) {
			System.out.println("list ==>"+log.getAuthorEmail());
			System.out.println("list ==>"+log.getRowNum());
		}
		return result;
	}

	public List<BlackListDTO> getReportedComments(String email) {
		List<BlackListDTO> finalList = new ArrayList<>();
		List<String> commentIds = repo.getReportedCommentIds(email);
		for(String commentId : commentIds) {
			log.info("id => {}", commentId);
			List<BlackListDTO> result = repo.getReportedCommentDatas(commentId); 
			//movie_nm, reported_comment, comment_written_date, reported_amount
			log.info("id2 => {}", commentId);
//			finalList.add(result);
		}
		return finalList;
	}


}
