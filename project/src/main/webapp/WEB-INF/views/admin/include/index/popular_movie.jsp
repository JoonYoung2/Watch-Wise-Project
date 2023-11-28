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
	<% int movieCnt = 1; %>
	<div class="page-width">
		<div class="popular-movie">
			<div id="movie-left-btn" class="movie-left-btn">◁</div>
			<div id="movie-right-btn" class="movie-right-btn">▷</div>
			<div class="popular-movie-div">
				<c:forEach var="list" items="${ movieChart }">
					<input type="hidden" class="movie-like-num" value="${ list.likeNum }">
					<input type="hidden" class="movie-nm" value="${ list.movieNm }">
					<div class="movie-poster">
						<a href="movieInfo?movieId=${ list.movieId }">
							<div>						
								<img class="movie-poster-img" src="${ list.posterUrl }">
							</div>
						</a>
						<div class="movie-poster-rank">
							<%= movieCnt++ %>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="bar-chart-movie">
				<canvas id="bar-chart-movie"></canvas>
			</div>
		</div>
	</div>
</body>
</html>