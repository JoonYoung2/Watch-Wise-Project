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
	<div class="page-width">
		<div class="member-trend">
			<div class="member-trend-div">
				<c:forEach var="list" items="${ memberTrendChart }">
					<input type="hidden" class="member-trend-date" value="${ list.date }">
					<input type="hidden" class="member-trend-count" value="${ list.count }">
				</c:forEach>
			</div>
			<div id="total-cnt" style="font-size:20px;"></div>
			<div class="bar-chart-member">
				<canvas id="bar-chart-member"></canvas>
			</div>
		</div>
	</div>
</body>
</html>