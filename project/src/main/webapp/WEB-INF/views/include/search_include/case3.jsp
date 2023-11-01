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
	<% int movieCnt = 0; %>
	<% int pageCnt = 0; %>
	<c:set var="peopleCnt" value="0"/>
	<input type="hidden" id="searchListSize" value="${ searchList3.size() }">
	&quot${ query }&quot 검색결과  ${ searchList3.size() }개<br>
	
	<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">&quot${ query }&quot 연관 인물</span></div>
	<c:forEach var="peopleInfo" items="${ searchList3 }">
		<% if(pageCnt < 3){ %>
			<div class="pageCnt" style="display:;">
				<%@ include file="./actor_info/infomations.jsp"%>
				<c:set var="peopleCnt" value="${ peopleCnt+1 }"/>
				<c:if test="${ not empty peopleInfo.movieInfoList }">
					<%@ include file="./actor_movies/movies.jsp"%>
				</c:if>
				<br><br><br>
			</div>
		<% }else{ %>
			<div class="pageCnt" style="display:none;">
				<%@ include file="./actor_info/infomations.jsp"%>
				<c:set var="peopleCnt" value="${ peopleCnt+1 }"/>
				<c:if test="${ not empty peopleInfo.movieInfoList }">
					<%@ include file="./actor_movies/movies.jsp"%>
				</c:if>
				<br><br><br>
			</div>
		<% } pageCnt++; %>
	</c:forEach>
	
	<% if(pageCnt > 3){ %>
		<div id="actorInfoAdd" style="all:unset; cursor:pointer; display:;" onclick="actorInfoAdd('<%=pageCnt%>');">
			<hr>
				<div>배우정보 더 보기 ▽</div>
			<hr>
		</div>
	<% } %>
	
	<br><br><br>
	
	<!-- SearchCase3 회원 추천 Movies START -->
	<c:if test="${ memberCommend.size() > 0 }">
		<%@ include file="./recommend/memberRecommend.jsp"%>
	</c:if>
	<!-- SearchCase3 회원 추천 Movies START -->
				
				
</body>
</html>