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
	<div>
		<c:forEach var="movie" items="${list}">
			<div>
				<span>${movie.movieNm }</span><br>
				<img src="${ }"
			</div>
		</c:forEach>
	</div>
</body>
</html>