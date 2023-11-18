package com.watch.project.dto.admin.chart;

import lombok.Data;

@Data
public class MovieChartDTO {
	private int num;
	private String movieId;
	private String movieNm;
	private String posterUrl;
	private String likeNum;
}
