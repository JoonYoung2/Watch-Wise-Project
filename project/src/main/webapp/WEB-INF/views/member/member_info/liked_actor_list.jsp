<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<div class="liked-actor-frame">
	<div class="liked-actor-title">
		<h3 style="text-align:center;">내가 좋아요한 배우</h3>	
	</div>
	<div class="liked-actor-list">
	    <c:forEach var="list" items="${likedActorList}" varStatus="loop">
	        <div class="actor-block">
			                  <img style="width:80px; height:80px;" src="/resources/img/bean_profile.png"><br>
			                  <span style="font-size:17px; font-weight:bold;">${ list.peopleNm}</span>
	        </div>
	        <c:if test="${loop.index % 4 == 3}">
	            <div style="clear: both;"></div>
	        </c:if>
	    </c:forEach>
	</div>
</div>


<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_common.js"></script>
<script src="/resources/js/member_info.js"></script>
</body>
</html>