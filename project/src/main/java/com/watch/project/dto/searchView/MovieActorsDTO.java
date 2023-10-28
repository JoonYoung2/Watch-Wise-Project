package com.watch.project.dto.searchView;

import lombok.Data;

@Data
public class MovieActorsDTO {
	private int peopleId;
	private String peopleNm;
	private String cast;
	
	public MovieActorsDTO(int peopleId, String peopleNm, String cast) {
		this.peopleId = peopleId;
		this.peopleNm = peopleNm;
		this.cast = cast;
	}
}
