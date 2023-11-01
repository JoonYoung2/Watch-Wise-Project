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
<div>
	<table border="1">
		<tr>
			<th>Img</th>
			<th>Movie Name</th>
			<th>My score</th>	
			<th>My comment</th>		
		</tr>
		<c:forEach var="data" items="${reviewList}" >
		<tr>
			<td><img src="${ data.posterUrl }"></td>
			<td><a href="/movieInfo?movieId=${ data.movieId }">${data.movieNm}</a></td>
			<td>${data.reviewScore}</td>
			<td>${data.reviewComment}</td>
${ data.posterUrl }
		</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>