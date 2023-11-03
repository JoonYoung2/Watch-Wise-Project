package com.watch.project.dto.movieInfoView;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GradeInfoDTO {
	private float gradeAvg;
	private int gradeCnt;
	
	@Builder
	public GradeInfoDTO(float gradeAvg, int gradeCnt){
		this.gradeAvg = gradeAvg;
		this.gradeCnt = gradeCnt;
	}
}
