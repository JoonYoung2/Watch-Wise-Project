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
	<h3 style="text-align:center">내가 좋아요한 코멘트들</h3>
	<c:forEach var="list" items="${likedCommentList}">
	<div class="movie-card" style=" background-color: lightgray;">
		<div class="movie-info">
		
		</div>
		<c:forEach var="comment" items="${list.commentDto }">
		<div class="comment-info">
		
		</div>
		</c:forEach>
	</div>
	</c:forEach>
</body>
</html>