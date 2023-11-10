package com.watch.project.dto.memberInfo;


import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieReviewDTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyScoredListDTO {
	private MovieReviewDTO movieReviewDto;
	private MovieInfoDTO movieInfoDto;
}
