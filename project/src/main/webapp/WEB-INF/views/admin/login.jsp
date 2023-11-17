<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/admin/login.css">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="./include/header.jsp" %>
	<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	<div align="center" class="login-page">
		<div align="center" class="login-page-size">
			<span class="msg">${ msg }</span>
			<form action="/admin/login" method="post">
				<input name="id" type="text" placeholder="ID" class="input-id"><br>
				<input name="pw" type="password" placeholder="PASSWORD" class="input-password"><br>
				<button class="login-button">로그인</button>
			</form>
		</div>
	</div>
</body>
</html>