<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:1px solid #ccc; margin:0px;">

<c:if test="${not empty msg }">
	<script>
		alert('${msg}');
	</script>
</c:if>

<div  style="background-color:rgb(244, 244, 247); height:1000px;">
    <div class="pw-check-frame" style="position: relative; top: 350px; background-color: white; width: 600px; height: 200px; margin: 0 auto;">
		<div align="center" style="padding-top:70px;">
			<form action="verifyPw" method="post">
				<input type="password" name="userPw" placeholder="Password" style="margin-bottom:10px; width:250px; height:20px;"><br>
				<input type="submit" value="인증하기">
			</form>
		</div>
	</div>
</div>
</body>
</html>