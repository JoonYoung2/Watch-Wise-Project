package com.watch.project.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class MemberDTO {
    private int id; // PK 컬럼
	private String userEmail;
	private String userPw;
	private String userName;
	private int userLoginType;
	private String socialLoginId;
	private int kakaoAgreement;
	private int naverAgreement;
	private int googleAgreement;
	private String kakaoRefreshToken;
	private String naverRefreshToken;
	private int isBlack;
	private String AccessToken;//얘는 테이블에 없는애. 순서 바뀜.
	
}
