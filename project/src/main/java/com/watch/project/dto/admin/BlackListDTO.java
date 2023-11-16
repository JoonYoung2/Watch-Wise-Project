package com.watch.project.dto.admin;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BlackListDTO {
	private String id;
	private String reportedComment;
	private String authorEmail;
	private String reporterEmail;
	private String reportedDate;
}
