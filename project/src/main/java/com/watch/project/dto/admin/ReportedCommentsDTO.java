package com.watch.project.dto.admin;

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
}
/*movie_nm, 
reported_comment, 
comment_written_date, 
reported_amount,
review_score
*/