package com.watch.project.service;

import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MemberDTO;
import com.watch.project.repository.MemberRepository;

@Service
//@RequiredArgsConstructor
public class LocalMemberService {
	@Autowired
	private MemberRepository repo;
	@Autowired
	private HttpSession session;
	
	BCryptPasswordEncoder encoder;
	public LocalMemberService() {
		encoder = new BCryptPasswordEncoder();
	}

	public String SignUpDo(MemberDTO dto, String pwCh) {
		String msg = "";
	String scriptMsg="";
	String pw = dto.getUserPw();
		System.out.println(dto.getUserEmail());
		//입력란 공백 체크
		if (dto.getUserEmail()==null || dto.getUserEmail().equals("")) {
			msg = "이메일 주소를 입력해주세요.";
		}else if(dto.getUserPw()==null || dto.getUserPw().equals("")) {
			msg = "비밀번호를 입력해주세요.";
		}else if(dto.getUserName()==null || dto.getUserName().equals("")) {
			msg = "이름을 입력해주세요.";
		}else {
			//이메일 중복 체크
			int existence = existingEmailCh(dto.getUserEmail());
			if(existence==1) {//이미 존재하는 이메일일 경우
				msg = "이미 존재하는 회원의 이메일 주소입니다. 로그인을 진행해주세요.";
			}else {
				//입력 양식 체크
//				if(id.length()<8 || id.length()>12) {
//					msg = "아이디는 8~12자로 입력해주세요.";
//				}else if(!Pattern.matches("^[a-zA-Z0-9]+$", id)){
//					msg = "아이디는 영문과 숫자로만 이루어질 수 있습니다.";
				if(pw.length()<10 || pw.length()>15) {
					msg = "비밀번호는 10~15자로 입력해주세요.";
				}else if(!Pattern.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).+$", pw)) {
					msg = "비밀번호는 영문, 숫자, 특수문자가 포함되어야 합니다.";
				}else if(!(dto.getUserPw().equals(pwCh))) {
					msg = "입력한 비밀번호 2개가 서로 일치하지 않습니다.";
				}else {
					//조건통과
					System.out.println("조건 통과는 되는거지???"+msg);
					//DB저장
					int result=0;
					if(msg==null || msg.equals("")) {//위에서 걸리지 않은 경우
						//비밀번호 암호화
						dto.setUserPw(encoder.encode(dto.getUserPw()));
						result = repo.saveMemberInfo(dto);			
						System.out.println("여기는 result : "+result);
						if(result == 1) {//저장이 잘 된 경우
							msg="회원가입이 완료되었습니다.";
						}else {
							msg="오류가 발생했습니다. 다시 시도해주세요.";
						}
					}					
				}
			}
		}

		System.out.println("여기는 msg : "+msg);
//		if(!msg.equals("회원가입이 완료되었습니다.")) {
//			scriptMsg = getAlertHistoryBack(msg);
//		}else {
//			scriptMsg = getAlertLocation(msg, "/signIn");
//		}
		return msg;
	}

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

	public int existingEmailCh(String email) {
		try {
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
	public String signInCheck(MemberDTO dto) {
		String msg;
		MemberDTO db = repo.getUserInfoByEmail(dto.getUserEmail());
		//이메일 존재여부
			//비밀번호 일치 여부
		if(existingEmailCh(dto.getUserEmail())==1) {//존재하는 이메일이면
			if(encoder.matches(dto.getUserPw(), db.getUserPw())) {//비밀번호가 일치하면
				System.out.println("match된다는데????");
				return null;
			}else {
				msg = getAlertHistoryBack("비밀번호가 일치하지 않습니다.");
			}
		}else {
			msg = getAlertHistoryBack("존재하지 않는 아이디입니다. 회원가입 후 로그인 해주세요.");
		}
		return msg;
	}
	
	public String signOut(String userEmail) {
		session.invalidate();
		String msg = getAlertLocation("로그아웃 되었습니다.", "/");
		return msg;
	}

	public String pwCh(MemberDTO dto) {
		String msg ="";
		String userEmail = (String)session.getAttribute("userEmail");
		MemberDTO db = repo.getUserInfoByEmail(userEmail);
		if(encoder.matches(dto.getUserPw(), db.getUserPw())) { //비번 인증 과정이 통과되면
			int deleteResult = repo.deleteMemberInfo(userEmail); //멤버 정보 삭제
			session.invalidate();
			if(deleteResult == 1) {
				msg = getAlertLocation("회원탈퇴가 완료되었습니다.", "/");				
			}else {
				msg = getAlertHistoryBack("오류가 발생했습니다. 다시 시도해주세요.");
			}
		}else {
			msg = getAlertHistoryBack("비밀번호가 일치하지 않습니다.");
		}
		return msg;
	}


}
