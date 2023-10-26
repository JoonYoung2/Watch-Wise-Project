<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/js2/common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
	<c:if test="${not empty msg }">
		<script>
			alert('${msg}');
		</script>
	</c:if>
	<form action="verifyPw" method="post">
		<input type="password" name="userPw">
		<input type="submit" value="인증하기">
	</form>
</body>
</html>