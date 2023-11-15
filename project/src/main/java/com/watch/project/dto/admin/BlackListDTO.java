package com.watch.project.dto.admin;

import lombok.Data;

@Data
public class BlackListDTO {
	private String reporterEmail;
	private String authorEmail;
	private String comment;
	private String reportedDate;
}
