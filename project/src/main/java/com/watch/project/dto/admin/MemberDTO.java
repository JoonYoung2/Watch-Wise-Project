package com.watch.project.dto.admin;

import lombok.Data;

@Data
public class MemberDTO {
	private int num;
    private int id; // PK 컬럼
	private String userEmail;
	private String userPw;
	private String userName;
	private int kakaoAgreement;
	private int naverAgreement;
	private int googleAgreement;
	private String joinedDate;//새로 추가해줌
	private String profileImg;//새로 추가해줌
}
