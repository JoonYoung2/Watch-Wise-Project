package com.watch.project.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ContentSearchingDTO {
	private int id;
	private String userEmail;
	private String content;
	private String searchDate;
	private int searchUse;
	
	@Builder
	public ContentSearchingDTO(int id, String userEmail, String content, String searchDate, int searchUse) {
		this.id = id;
		this.userEmail = userEmail;
		this.content = content;
		this.searchDate = searchDate;
		this.searchUse = searchUse;
	}
}
