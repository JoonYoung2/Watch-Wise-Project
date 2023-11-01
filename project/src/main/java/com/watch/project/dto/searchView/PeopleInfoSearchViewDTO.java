package com.watch.project.dto.searchView;

import java.util.ArrayList;
import java.util.List;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;

import lombok.Builder;
import lombok.Data;

@Data
public class PeopleInfoSearchViewDTO {
	private int peopleId;
	private String peopleNm;
	private String peopleNmEn;
	private String sex;
	private int likeNum;
	private List<MovieInfoDTO> movieInfoList;
	
	@Builder
	public PeopleInfoSearchViewDTO(PeopleInfoDetailDTO peopleInfoDetailDto) {
		this.peopleId = peopleInfoDetailDto.getPeopleId();
		this.peopleNm = peopleInfoDetailDto.getPeopleNm();
		this.peopleNmEn = peopleInfoDetailDto.getPeopleNmEn();
		this.sex = peopleInfoDetailDto.getSex();
		this.likeNum = peopleInfoDetailDto.getLikeNum();
		this.movieInfoList = new ArrayList<>();
	}
}
