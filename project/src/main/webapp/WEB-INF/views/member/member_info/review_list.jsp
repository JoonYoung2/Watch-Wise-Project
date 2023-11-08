<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/js/common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:1px solid #ccc;">
<br>
<br>

	<h3 style="text-align: center;">나의 평점 기록</h3>
	<div align="center">
		<table border="1">
			<tr>
				<th>Img</th>
				<th>Movie Name</th>
				<th>My score</th>	
				<th>My comment</th>		
			</tr>
			<c:forEach var="data" items="${reviewList}" >
			<tr>
				<td><a href="/movieInfo?movieId=${ data.movieId }"><img style="width:75px; height: auto;" src="${ data.posterUrl }"></a></td>
				<td style="text-align: center;"><a href="/movieInfo?movieId=${ data.movieId }">${data.movieNm}</a></td>
				<td style="text-align: center;">${data.reviewScore}</td>
				<td>${data.reviewComment}</td>
			</tr>
			</c:forEach>
		</table>
	</div>	
</body>
</html>