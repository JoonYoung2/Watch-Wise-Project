package com.watch.project.dto.admin;

import java.util.List;

import lombok.Data;

@Data
public class ReportedCommentsDTO {
	private String reportedComment;
	private String movieNm;
	private String commentWrittenDate;
	private float reviewScore;
	private String movieId;
	private int reportedAmount;
	private String commentId;
	private List<BlackListDTO> blackListDto;
}
/*movie_nm, 
reported_comment, 
comment_written_date, 
reported_amount,
review_score
*/