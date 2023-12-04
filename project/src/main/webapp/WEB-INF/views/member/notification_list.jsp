<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<div align="center" style="width:100%;">
	<div align="center" style="width:90%;">
		<hr style="border:0.5px solid #ccc; margin:0px;">
		<br>
		<br>
		<h3 style="text-align:center;">알림</h3>
		<div align="center" style="width:100%">
			<div align="center" style="width:35%">
				<div style="text-align:right;">
					<a href="/deleteAllNotifications">알림 전체 삭제</a>
				</div>
			</div>
		</div>
		<br><br>
		<div align="center" style="width:100%;">
			<div align="center" style="width:50%;">
				<table>
					<c:forEach var="list" items="${notiList }">
						<tr>
							<c:choose>
								<c:when test="${list.isSeen eq 0}">
									<td style=" word-wrap:break-word;width:600px;"><div style="font-weight:bold; font-size:15px; width:100%; text-align:center;">
										<div style="width:600px; text-align:left">
											${list.notificationContent }
										</div>
									</div></td>
								</c:when>
								<c:otherwise>
									<td style=" word-wrap:break-word;width:600px; font-size:15px;"><div style="font-size:15px; width:100%; text-align:center;">
										<div style="width:600px; text-align:left">
											${list.notificationContent }
										</div>
									</div></td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td style="font-size:13px; color:grey;">${list.insertedDate }</td>
						</tr>
						<tr>
							<td style="font-size:5px;border-bottom:0.5px solid #ccc;">&nbsp;</td>
						</tr>
						
						<tr>
							<td style="font-size:5px">&nbsp;</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</div>


</body>
</html>