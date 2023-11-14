package com.watch.project.dto.admin;

import lombok.Data;

@Data
public class MemberDTO {
	private int num;
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
	private String AccessToken;
}
