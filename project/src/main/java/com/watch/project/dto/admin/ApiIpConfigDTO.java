package com.watch.project.dto.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiIpConfigDTO {
	private int id;
	private String requestIp;
}
