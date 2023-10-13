package com.watch.project.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDTO {
	private int emailToken;
	private String userEmail;
	private String userPw;
	private String userName;
	private int userLoginType;
	private String socialLoginId;
}
