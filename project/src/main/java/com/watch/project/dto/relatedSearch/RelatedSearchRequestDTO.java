package com.watch.project.dto.relatedSearch;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RelatedSearchRequestDTO {
	private String content;
	private String userEmail;
	
	@Builder
	public RelatedSearchRequestDTO(String content, String userEmail) {
		this.content = content;
		this.userEmail = userEmail;
	}
}
