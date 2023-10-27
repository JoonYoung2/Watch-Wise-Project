package com.watch.project.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MemberDTO;
import com.watch.project.repository.MemberRepository;

@Service
public class CommonMemberService {
	@Autowired MemberRepository repo;
	
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


}
