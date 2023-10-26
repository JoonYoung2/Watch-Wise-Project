<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/js2/common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<h3> My Informations </h3>
<table border="1">
	<tr>
		<th>이메일</th>
		<td>${dto.userEmail}</td>
	</tr>
	<tr>
		<th>이름</th>
		<td>${dto.userName }</td>
	</tr>
</table>
<a href="socialMemberInfoModify">정보 수정하기</a>
</body>
</html>