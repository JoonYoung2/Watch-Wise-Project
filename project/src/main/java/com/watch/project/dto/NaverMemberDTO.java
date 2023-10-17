package com.watch.project.dto;

import lombok.Data;

@Data
public class NaverMemberDTO {
	private String id;

    private String email;

    private String nickname;

    private String profileImage;

    private String age;

    private String gender;

    private String name;

    private String resultCode;

    private String resultStr;

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private String expiresIn;

    private String loginType;
}
