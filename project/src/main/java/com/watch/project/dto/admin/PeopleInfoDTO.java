package com.watch.project.dto.admin;

import lombok.Data;

@Data
public class PeopleInfoDTO {
	private int num;
	// DB Entity
	private int peopleId;
	private String peopleNm;
	private String peopleNmEn;
	private String sex;
	private String movieId;
	private String movieNm;
	private int likeNum;
	private String profileUrl;
}
