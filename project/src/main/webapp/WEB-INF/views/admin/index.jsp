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
		<!-- 메뉴 설정 -->
		<%@ include file="./include/index/menu_config.jsp" %>
		<!-- 메뉴 설정 -->
		
		<!-- 신고 접수 -->
		<%@ include file="./include/index/member_reported.jsp" %>
		<!-- 신고 접수 -->
		
		<!-- 회원 동향 -->
		<%@ include file="./include/index/member_trend.jsp" %>
		<!-- 회원 동향 -->
		
		<!-- 실시간 인기 검색어 -->
		<%@ include file="./include/index/live_search.jsp" %>
		<!-- 실시간 인기 검색어 -->
		
		<!-- 인기 영화 -->
		<%@ include file="./include/index/popular_movie.jsp" %>
		<!-- 인기 영화 -->
		
		<!-- 인기 배우 -->
		<%@ include file="./include/index/popular_actor.jsp" %>
		<!-- 인기 배우 -->
	</div>
	<script src="/resources/js/admin/index.js"></script>
</body>
</html>