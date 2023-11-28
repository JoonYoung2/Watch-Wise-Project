<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% int actorCnt = 1; %>
	<div class="page-width">
		<div class="popular-actor">
			<div id="actor-left-btn" class="actor-left-btn">◁</div>
			<div id="actor-right-btn" class="actor-right-btn">▷</div>
			<div class="popular-actor-div">
				<c:forEach var="list" items="${ actorChart }">
					<input type="hidden" class="actor-like-num" value="${ list.likeNum }">
					<input type="hidden" class="actor-nm" value="${ list.peopleNm }">
					<div class="actor-profile">
						<a href="peopleInfo?peopleId=${ list.peopleId }">
							<div>						
								<img class="actor-profile-img" src="${ list.profileUrl }">
							</div>
						</a>
						<div class="actor-profile-rank">
							<%= actorCnt++ %>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="bar-chart-actor">
				<canvas id="bar-chart-actor"></canvas>
			</div>
		</div>
	</div>
</body>
</html>