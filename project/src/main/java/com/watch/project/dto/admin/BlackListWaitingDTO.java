package com.watch.project.dto.admin;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class BlackListWaitingDTO {
	private int rowNum;
    private String authorEmail;
    private int reportedCommentAmount;
}
