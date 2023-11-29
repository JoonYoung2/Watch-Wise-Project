<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<link rel="stylesheet" href="/resources/css/movie_common_btn.css">
<title>Insert title here</title>
	<c:if test="${signOutAlert == true }"><!--redirect 하면 알림 안뜸.-->
		<script type="text/javascript">
			alert('로그아웃 되었습니다.');
		</script>	
	</c:if>
	
	<c:if test="${unregisterAlert == true }">
		<script type="text/javascript">
			alert('회원 탈퇴가 완료되었습니다.');
		</script>	
	</c:if>
	
	<c:if test="${not empty msg }">
		<script type="text/javascript">
			alert('${msg}');
		</script>
	</c:if>
</head>
<body>
	<%@ include file="/WEB-INF/views/header.jsp" %>
	
	<c:set var="daily" value="${ movieInfoMap['daily'] }"/>
	<c:set var="weekly0" value="${ movieInfoMap['weekly0'] }"/>
	<c:set var="weekly1" value="${ movieInfoMap['weekly1'] }"/>
	<c:set var="weekly2" value="${ movieInfoMap['weekly2'] }"/>
	<c:set var="upcoming" value="${ movieInfoMap['upcoming'] }"/>
	<c:set var="recentlyKo" value="${ movieInfoMap['recentlyKorea'] }"/>
	<c:set var="recentlyFo" value="${ movieInfoMap['recentlyForeign'] }"/>
	<c:set var="imgWidth" value="300"/>
	<c:set var="imgHeight" value="428.16"/>
	<% 
		int cnt = 0; 
		int rankCnt = 1;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		int todayDate = Integer.parseInt(dtFormat.format(cal.getTime()));
	%>
	
	<!-- 일간 -->
	<c:if test="${ daily.size() > 0 }">
		<br><br>
		<%@ include file="../include/home_include/daily.jsp" %>
	</c:if>
	
	<!-- 개봉 전 -->
	<c:if test="${ upcoming.size() > 0 }">
		<br><br><br>
		<% 
			cnt = 0; 
		%>
		<%@ include file="../include/home_include/upcoming.jsp" %>
	</c:if>
	
	<!-- 광고 & 인기 그래프 -->
	<%@ include file="../include/home_include/main_board.jsp" %>
	
	<!-- 최근 개봉한 한국 영화 -->
	<c:if test="${ recentlyKo.size() > 0 }">
		<br><br><br>
		<% 
			cnt = 0; 
		%>
		<%@ include file="../include/home_include/recently_korean.jsp" %>
	</c:if>
	
	<!-- 최근 개봉한 외국 영화 -->
	<c:if test="${ recentlyFo.size() > 0 }">
		<br><br><br>
		<% 
			cnt = 0; 
		%>
		<%@ include file="../include/home_include/recently_foreign.jsp" %>
	</c:if>
	
	<!-- 주간 -->
	<c:if test="${ weekly0.size() > 0 }">
		<br><br><br>
		<% 
			cnt = 0; 
			rankCnt = 1;
		%>
	
		<%@ include file="../include/home_include/weekly0.jsp" %>
	</c:if>
	
	<!-- 주말 -->
	<c:if test="${ weekly1.size() > 0 }">
		<br><br><br>
		<% 
			cnt = 0; 
			rankCnt = 1;
		%>
		
		<%@ include file="../include/home_include/weekly1.jsp" %>
	</c:if>
	
	<!-- 주중 -->
	<c:if test="${ weekly2.size() > 0 }">
		<br><br><br>
		<% 
			cnt = 0; 
			rankCnt = 1;
		%>
		<%@ include file="../include/home_include/weekly2.jsp" %>
	</c:if>
	
	<script src="/resources/js/home.js"></script>
</body>
</html>