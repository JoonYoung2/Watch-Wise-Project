package com.watch.project.dto.admin;

import java.util.List;

import lombok.Data;

@Data
public class AutoPagingDTO {
	private List<String> titleList;
	private String listNm;
	private String tableNm;
	private String orderByColumn;
	private int start;
	private int end;
	private int last;	
	private int rowNum;
	private int pageNum;
	
	public int getStart(int end, int rowNum) {
		return end - rowNum + 1;
	}
	
	public int getEnd(int pageNum, int rowNum) {
		return pageNum * rowNum;
	}
	
	public void setPagingInfo(int pageNum, TableInfoDTO tableInfoDto) {
		if(pageNum > 5 && pageNum < tableInfoDto.getPageNum() - 5) {
			setStart(pageNum-4);
			setEnd(pageNum+5);
			setLast(tableInfoDto.getPageNum());
			if(tableInfoDto.getPageNum() < 10) {
				setEnd(tableInfoDto.getPageNum());
				setLast(0);
			}
		}else if(pageNum <= 5) {
			setStart(1);
			setEnd(10);
			setLast(tableInfoDto.getPageNum());
			if(tableInfoDto.getPageNum() < 10) {
				setEnd(tableInfoDto.getPageNum());
				setLast(0);
			}
		}else {
			setStart(tableInfoDto.getPageNum() - 9);
			setEnd(tableInfoDto.getPageNum());
			if(tableInfoDto.getPageNum() < 10) {
				setEnd(tableInfoDto.getPageNum());
			}
		}
	}

	public void setAutoPagingDto(String listNm, String tableNm, int pageNum, int rowNum, String column, List<String> titleList, TableInfoDTO tableInfoDto) {
		setListNm(listNm);
		setTableNm(tableNm);
		setPageNum(pageNum);
		setRowNum(rowNum);
		setOrderByColumn(column);
		/*
		 * 테이블 제목
		 */
		setTitleList(titleList);

		/*
		 * 페이징 넘버
		 */
		setPagingInfo(pageNum, tableInfoDto);
	}
}
