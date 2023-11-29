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
	<div style="width:30%; font-size:13px; margin-right:5px;">
		<c:choose>
			<c:when test="${isWished eq 1 }">	
				<img src="/resources/img/saved.png" onclick="location.href='/deleteMovieFromWishList?movieId=${ movieInfo.movieId }'" style="cursor:pointer; width:25px; margin-bottom:10px;"><br>
	   			저장됨
			</c:when>
			<c:otherwise>	
				<img src="/resources/img/plus.png" onclick="location.href='/addMovieIntoWishList?movieId=${ movieInfo.movieId }'" style="cursor:pointer; width:25px; margin-bottom:10px;"><br>
	    		보고싶어요
  			</c:otherwise>
  		</c:choose>
	</div>
</body>
</html>