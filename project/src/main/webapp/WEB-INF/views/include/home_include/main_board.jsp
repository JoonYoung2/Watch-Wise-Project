<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/admin/auto-table.css">
<link rel="stylesheet" href="/resources/css/home/main_board.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
</head>
<body>
	<div align="center" class="main-page">
		<br><br>
		<div class="main-page-menu">
			<div class="menus" onclick="menuClick(0)">
				인기 영화 순위
			</div>
			<div class="menus" onclick="menuClick(1)">
				인기 배우 순위
			</div>
		</div>
		
		<div class="ad-div">
			<a href="https://www.xexymix.com/"><img src="/resources/img/ad.png"></a>
		</div>
		
		<% int movieCnt = 1; %>
		<!-- 인기 영화 -->
		<div class="main-page-width">
			<div class="popular-movie">
<!-- 				<div id="movie-left-btn" class="movie-left-btn">◁</div> -->
<!-- 				<div id="movie-right-btn" class="movie-right-btn">▷</div> -->
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
		<!-- 인기 영화 -->
		
		<% int actorCnt = 1; %>
		<!-- 인기 배우 -->
		<div class="main-page-width">
			<div class="popular-actor">
<!-- 				<div id="actor-left-btn" class="actor-left-btn">◁</div> -->
<!-- 				<div id="actor-right-btn" class="actor-right-btn">▷</div> -->
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
		<!-- 인기 배우 -->
	</div>
	<script src="/resources/js/home/main_board.js"></script>
</body>
</html>