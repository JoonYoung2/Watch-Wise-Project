package com.watch.project.dto.relatedSearch;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RelatedSearchResponseDTO {
	private String content;
	private String searchType;
	
	@Builder
	public RelatedSearchResponseDTO(String content, String searchType) {
		this.content = content;
		this.searchType = searchType;
	}
}
