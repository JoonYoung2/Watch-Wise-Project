package com.watch.project.dto;

import lombok.Data;

@Data
public class PeopleInfoDetailDTO {
	private int peopleId;
	private String peopleNm;
	private String peopleNmEn;
	private String sex;
	private String movieId;
	private String movieNm;
	private int likeNum;
	private String profileUrl;
}
