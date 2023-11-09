package com.watch.project.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberConfigDTO {
	private String userEmail;
	private int searchHistory;
	
	@Builder
	public MemberConfigDTO(String userEmail, int searchHistory) {
		this.userEmail = userEmail;
		this.searchHistory = searchHistory;
	}
}
