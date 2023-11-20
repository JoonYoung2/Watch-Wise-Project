package com.watch.project.dto.admin;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class BlackListDTO {
	private String id;
	private String reportedComment;
	private String reportedCommentId;
	private String authorEmail;
	private String reporterEmail;
	private String reasonForReport;
	private String reportedDate;
	private String movieNm;
	private String commentWrittenDate;
	private int isBlack;
	private String movieId;
	private float reviewScore;
	//-----------DB 별도---------------
	private int reportedAmount;
	
	
}
/*movie_nm, 
reported_comment, 
comment_written_date, 
reported_amount
*/