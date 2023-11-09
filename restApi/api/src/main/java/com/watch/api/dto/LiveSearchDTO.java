package com.watch.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiveSearchDTO {
	private int num;
	private int cnt;
	private String content;
	private String state;
	
	@Builder
	public LiveSearchDTO(int num, int cnt, String content, String state) {
		this.num = num;
		this.cnt = cnt;
		this.content = content;
		this.state = state;
	}
}