<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/admin/auto-table.css">
<link rel="stylesheet" href="/resources/css/admin/index.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	
	<%@ include file="./include/header.jsp" %>
	
	<div align="center" class="index-page">
		<br><br>
		<div class="page-menu">
			<div class="menus" onclick="menuClick(0)">
				인기 영화 순위
			</div>
			<div class="menus" onclick="menuClick(1)">
				인기 배우 순위
			</div>
			<div class="menus" onclick="menuClick(2)">
				실시간 인기 검색어
			</div>
			<div class="menus" onclick="menuClick(3)">
				회원 동향
			</div>
		</div>
		
		<% int movieCnt = 1; %>
		<!-- 인기 영화 -->
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
		<!-- 인기 영화 -->
		
		<% int actorCnt = 1; %>
		<!-- 인기 배우 -->
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
		<!-- 인기 배우 -->
		
		<% int liveCnt = 0; %>
		<!-- 실시간 인기 검색어 -->
		<div class="page-width">
			<div align="left" class="live-search-page">
				<div onclick="liveSearchLeftBtn();" id="live-search-left-btn" class="live-search-left-btn">◁</div>
				<div class="live-search-div">
					<table>
						<tr>
							<th>순위</th>
							<th>검색 수</th>
							<th>검색어</th>
						</tr>
						<c:if test="${ empty liveSearch }">
							<tr>
								<td colspan="3" class="table-width-450">
								<br><br><br><br><br>
								실시간 인기검색어가 없습니다.
								</td>
							</tr>
						</c:if>
						<c:forEach var="list" items="${ liveSearch }">
							<input type="hidden" class="chart-live-search-content" value="${ list.content }">
							<input type="hidden" class="chart-live-search-cnt" value="${ list.cnt }">
							<tr class="live-search-content" style="display:none;">
								<td class="table-width-75 live-search-contents">${ list.num }</td>
								<td class="table-width-75 live-search-contents">${ list.cnt }</td>
								<td class="table-width-300 live-search-contents">${ list.content }</td>
							</tr>
							<% liveCnt++; %>
						</c:forEach>
					</table>
				</div>
				<% if(liveCnt > 10){ %>
					<div onclick="liveSearchRightBtn();" id="live-search-right-btn" class="live-search-right-btn">▷</div>
				<% } %>
			</div>
			<div class="bar-chart-search">
				<canvas id="bar-chart-live-search"></canvas>
			</div>
		</div>
		<!-- 실시간 인기 검색어 -->
		
		<!-- 회원 동향 -->
		<div class="page-width">
			
		</div>
		<!-- 회원 동향 -->
		
		
	</div>
	<script src="/resources/js/admin/index.js"></script>
</body>
</html>