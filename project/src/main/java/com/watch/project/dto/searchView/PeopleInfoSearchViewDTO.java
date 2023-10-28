package com.watch.project.dto.searchView;

import java.util.ArrayList;
import java.util.List;

import com.watch.project.dto.MovieInfoDTO;

import lombok.Data;

@Data
public class PeopleInfoSearchViewDTO {
	private int peopleId;
	private String peopleNm;
	private String peopleNmEn;
	private String sex;
	private int likeNum;
	private List<MovieInfoDTO> movieInfoList;
	
	public PeopleInfoSearchViewDTO(int peopleId, String peopleNm, String peopleNmEn, String sex, int likeNum) {
		this.peopleId = peopleId;
		this.peopleNm = peopleNm;
		this.peopleNmEn = peopleNmEn;
		this.sex = sex;
		this.likeNum = likeNum;
		this.movieInfoList = new ArrayList<>();
	}
}
