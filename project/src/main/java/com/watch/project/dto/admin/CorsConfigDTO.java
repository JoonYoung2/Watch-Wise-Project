package com.watch.project.dto.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CorsConfigDTO {
	private int id;
	private String allowedOrigins;
}
