package com.watch.project.service.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MemberDTO;
import com.watch.project.dto.WishListDTO;
import com.watch.project.dto.admin.UserNotificationDTO;
import com.watch.project.repository.MemberRepository;
import com.watch.project.repository.MovieInfoRepository;
import com.watch.project.repository.PeopleInfoRepository;
import com.watch.project.repository.ReviewRepository;
import com.watch.project.repository.WishListRepository;

@Service
public class CommonMemberService {
	@Autowired private MemberRepository repo;
	@Autowired private ReviewRepository reviewRepo;
	@Autowired private MovieInfoRepository movieInfoRepo;
	@Autowired private PeopleInfoRepository peopleInfoRepo;
	@Autowired private WishListRepository wishListRepo;
	@Autowired private HttpSession session;
	
	public String getAlertLocation(String msg, String url) {
		String message = "<script>alert('" + msg + "');";
		message += "location.href='" +url + "';</script>";
		return message;
	}

	public String getAlertHistoryBack(String msg) {
		String message = "<script>alert('" + msg + "');";
		message += "window.history.back();</script>";
		return message;
	}
	
	public String getAlertOnly(String msg) {
		String message = "<script>alert('" + msg + "');</script>";
		return message;
	}
	
//	public MemberDTO checkExistingMemberByEmail(String userEmail) { //네이버와 겹침
//		MemberDTO result = repo.getUserInfoByEmail(userEmail);
//		return result;
//	}
	
	public int saveMemberInfo(MemberDTO user) { //네이버와 겹침
		int result = repo.saveMemberInfo(user);
		return result;
	}
	
	public int existingEmailCh(String email) {//local
		System.out.println("email11111"+email);

		try {
			System.out.println("email22222"+email);
			MemberDTO result = repo.getUserInfoByEmail(email);
			if(result.getUserEmail() == null || result.getUserEmail().equals("")) {
				return 0;
			}
			System.out.println("service existing - getUserId => "+result.getUserEmail());
		}catch(NullPointerException e) {
			return 0;
		}
		return 1;//존재하는 아이디
	}
	
	public String unregister(String email) { //구글과 겹침
		int result = repo.deleteMemberInfo(email);
		String msg = "회원탈퇴가 완료되었습니다.";
		if(result!=1) {//삭제가 잘 안되었다면
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return msg;
	}

	public MemberDTO getMemberInfoByEmail(String email) {
		MemberDTO info = repo.getUserInfoByEmail(email);
		return info;
	}

	public String updateMemberName(MemberDTO dto) {
		String msg = "";
		int result = repo.updateMemberName(dto);
		if(result!=1) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		} else {
			msg = "정보 수정이 완료되었습니다.";
		}
		return msg;
	}

	public Map<String, Integer> getNumbersOfDatasForMemberInfo(String userEmail) {
		Map<String, Integer> result = new HashMap<>();
		//평가 수
		result.put("score", reviewRepo.getAmountOfScoredOnes(userEmail));
		//코멘트 수
		result.put("comment", reviewRepo.getAmountOfComments(userEmail));
		//보고싶어요 수
		result.put("wishList", wishListRepo.getAmountOfWishedMovies(userEmail));
		//좋아한 영화 수
		result.put("likedMovie", movieInfoRepo.getAmountOfLikedMovies(userEmail));
		//좋아한 배우 수
		result.put("likedActor", peopleInfoRepo.getAountOfLikedActors(userEmail));
		//좋아한 코멘트 수
		result.put("likedComment", reviewRepo.getAmountOfLikedComments(userEmail));
		return result;
	}

	public List<UserNotificationDTO> getNotificationList() {
		String email = (String)session.getAttribute("userEmail");
		List<UserNotificationDTO> allNotisList = repo.getNotificationListByEmail(email);
		for(UserNotificationDTO list : allNotisList) {
			String storedDate = list.getInsertedDate();
			
			//오늘 날짜데이터 생성
			Date currentDate = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String formattedDate = sdf.format(currentDate);
	        
            try {
                // 문자열로 된 날짜를 Date 객체로 파싱
                Date storedDateObj = sdf.parse(storedDate);

                // 날짜 차이 계산 (밀리초 단위)
                long timeDifference = currentDate.getTime() - storedDateObj.getTime();

                // 차이를 일로 변환
                long daysDifference = timeDifference / (1000 * 60 * 60 * 24);

                // 여기서 daysDifference를 사용하여 필요한 작업 수행
                if (daysDifference <= 7) {
                	if(daysDifference == 0) {
                		list.setInsertedDate("오늘");
                	}else {
                	list.setInsertedDate(daysDifference+"일전");
                	}
                    System.out.println("Notification within 7 days: " + list.getNotificationContent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
		repo.setIsSeenForList(email);
		return allNotisList;
	}

	public String getJoinedDate() {
		Date currentDate = new Date(); //Fri Oct 27 21:35:40 KST 2023
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// Date를 ISO 8601 형식의 문자열로 변환
		String dateStr = dateFormat.format(currentDate);//2023-10-27T21:35:40Z
		return dateStr;
		
	}


}
