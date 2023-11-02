package com.watch.project.dto.movieInfoView;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class PeopleInfoDTO {
	private int[] peopleId;
	private String[] peopleNm;
	private String[] peopleCast;
	private int end;
	
	@Builder
	public PeopleInfoDTO(int[] peopleId, String[] peopleNm, String[] peopleCast, int end) {
		this.peopleId = peopleId;
		this.peopleNm = peopleNm;
		this.peopleCast = peopleCast;
		this.end = end;
	}
}
