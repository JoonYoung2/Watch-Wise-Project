package com.watch.project.dto.admin;

import lombok.Data;

@Data
public class UserNotificationDTO {
	private String recieverEmail;
	private String recieverName;
	private String senderEmail;
	private String senderName;
	private String notificationContent;
	private String insertedDate;
	private Integer isSeen;
}
