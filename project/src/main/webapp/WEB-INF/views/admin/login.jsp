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
	<%@ include file="./include/header.jsp" %>
	
	<div align="center" style="width:100%;">
		<div align="center" style="width:80%;">
			${ msg }
			<form action="/admin/login" method="post">
				<input name="id" type="text" placeholder="ID" style="width:300px;"><br>
				<input name="pw" type="password" placeholder="PASSWORD" style="width:300px;"><br>
				<button style="width:150px;">로그인</button><button style="width:150px;" type="reset">취소</button>
			</form>
		</div>
	</div>
</body>
</html>