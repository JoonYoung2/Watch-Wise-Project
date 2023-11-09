package com.watch.project.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PeopleLikeDTO {
	private String id;
	private int peopleId;
	private String userEmail;
	private String likeDate;
	
	@Builder
	public PeopleLikeDTO(String id, int peopleId, String userEmail, String likeDate) {
		this.id = id;
		this.peopleId = peopleId;
		this.userEmail = userEmail;
		this.likeDate = likeDate;
	}
}
