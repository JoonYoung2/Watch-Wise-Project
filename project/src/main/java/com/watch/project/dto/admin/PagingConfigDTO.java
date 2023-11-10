package com.watch.project.dto.admin;

import lombok.Data;

@Data
public class PagingConfigDTO {
	private int id;
	private String tableNm;
	private String columns;
	private String orderByColumn;
	private int rowNum;
}
