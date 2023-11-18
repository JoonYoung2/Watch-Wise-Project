package com.watch.project.dto.admin;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BlackListWaitingDTO {
	private int num;
	private String userEmail;
	private int reportedCommentAmount;
}
