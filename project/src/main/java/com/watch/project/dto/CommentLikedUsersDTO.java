package com.watch.project.dto;

import lombok.Data;

@Data
public class CommentLikedUsersDTO {
	private String id;
	private String commentId;
	private String likedUserEmail;
}
