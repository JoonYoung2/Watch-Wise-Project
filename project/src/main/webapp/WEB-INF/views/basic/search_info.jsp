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
	
	<div align="center" style="width: 100%;">
		<div align="center" style="width: 80%;">
			<% int cnt = 0; %>
			<c:if test="${ not empty searchList1 }">
				<%@ include file="../include/search_include/case1.jsp"%>
			</c:if>
			
			<% cnt = 0; %>
			<c:if test="${ not empty searchList2 }">
				<%@ include file="../include/search_include/case2.jsp"%>
			</c:if>
			
			<% cnt = 0; %>
			<c:if test="${ not empty searchList3 }">
				<%@ include file="../include/search_include/case3.jsp"%>
			</c:if>
			
			<% cnt = 0; %>
			<c:if test="${ not empty searchList4 }">
				<%@ include file="../include/search_include/case4.jsp"%>
			</c:if>
			
		</div>
	</div>
	
	<!-- Member 관련 JS -->
	<script src="/resources/js/common.js"></script>
	<!-- 검색 JS -->
	<script src="/resources/js/search_common.js"></script>
	<!-- 페이지 JS -->
	<script src="/resources/js/search_info.js"></script>
</body>
</html>