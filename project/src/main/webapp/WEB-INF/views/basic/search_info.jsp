<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<link rel="stylesheet" href="/resources/css/search_info.css">
<title>Insert title here</title>
</head>
<body>
	<c:set var="imgWidth" value="300"/>
	<c:set var="imgHeight" value="428.16"/>
	<%@ include file="/WEB-INF/views/header.jsp"%>
	
	<c:if test="${ not empty nothing }">
		<script>
			alert("${nothing}");
			history.back();
		</script>
	</c:if>
	
	<div align="center" style="width: 100%;">
		<div align="center" style="width: 80%;">
			<% int cnt = 0; %>
			<c:if test="${ not empty movieNmSearchingCase }">
				<%@ include file="../include/search_include/movieNmSearchingCase.jsp"%>
			</c:if>
			
			<% cnt = 0; %>
			<c:if test="${ not empty actorNmSearchingCase }">
				<%@ include file="../include/search_include/actorNmSearchingCase.jsp"%>
			</c:if>
			
		</div>
	</div>

	<!-- Present page JS -->
	<script src="/resources/js/search_info.js"></script>
</body>
</html>