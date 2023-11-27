<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/people_info.css">
<link rel="stylesheet" href="/resources/css/movie_common_btn.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<br><br>
<c:set var="imgWidth" value="300"/>
<c:set var="imgHeight" value="428.16"/>

<%@ include file="../include/actor_include/actor_info.jsp" %>

<c:if test="${ not empty sessionScope.userEmail }">
	<%@ include file="../include/actor_include/member_actor_like.jsp" %>
</c:if>

<c:if test="${ empty sessionScope.userEmail }">
	<%@ include file="../include/actor_include/unregister_actor_like.jsp" %>
</c:if>
<br>
<% 
	int cnt = 0; 
%>

<c:if test="${ not empty movieInfo }">
	<%@ include file="../include/actor_include/movie_appearance.jsp" %>
</c:if>

<script src="/resources/js/common.js"></script>
<script src="/resources/js/people_info.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>