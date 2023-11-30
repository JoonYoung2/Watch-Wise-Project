<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/movie_info.css">
<link rel="stylesheet" href="/resources/css/movie_common_btn.css">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<c:set var="gradeNum" value="${ gradeInfo.gradeAvg } + ''"/>
<input type="hidden" id="chartMovie" value="${ chartMovie }">
<input type="hidden" id="reviewScore" value="${ ifWroteComment.reviewScore }">
<input type="hidden" id="gradeCnt" value="${ gradeInfo.gradeCnt }">

   	<!-- 영화 정보 타이틀 & 위시, 코멘트, 평점 기능 -->
   	<%@ include file="../include/movie_info/header/header.jsp" %>
   	
	<!-- 영화 포스터 ~ 코멘트 작성 -->
	<%@ include file="../include/movie_info/detail/detail.jsp" %>
   
	<!-- 배우 정보 -->
	<%@ include file="../include/movie_info/actor/actor.jsp" %>
   
	<!-- 작성된 코멘트들 -->
	<%@ include file="../include/movie_info/comment/comment.jsp" %>

	<!-- Modal -->
	<%@ include file="../include/movie_info/modal/modal.jsp" %>
   
   <!-- 포스터 -->
   <%@ include file="../include/movie_info/poster.jsp" %>

<script src="/resources/js/common.js"></script>
<script src="/resources/js/movie_info.js"></script>
<script src="/resources/js/search_common.js"></script>
<script src="/resources/js/admin/black_list.js"></script>

</body>
</html>