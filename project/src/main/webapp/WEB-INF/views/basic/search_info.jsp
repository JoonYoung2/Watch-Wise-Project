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
	<% int cnt = 0; %>
	<div align="center" style="width: 100%;">
		<div align="center" style="width: 80%;">
			
			<c:if test="${ not empty searchList1 }">
				<%@ include file="./search_include/case1.jsp"%>
			</c:if>
			<% cnt = 0; %>
			<c:if test="${ not empty searchList2 }">
				<%@ include file="./search_include/case2.jsp"%>
			</c:if>
			
			<% cnt = 0; %>
			
			<c:if test="${ not empty searchList3 }">
				<%@ include file="./search_include/case3.jsp"%>
			</c:if>
			
			<% cnt = 0; %>
			
			<c:if test="${ not empty searchList4 }">
				<%@ include file="./search_include/case4.jsp"%>
			</c:if>
		</div>
	</div>

	<script src="/resources/js/common.js"></script>
	<script src="/resources/js/search_common.js"></script>
	<script src="/resources/js/search_info.js"></script>
</body>
</html>