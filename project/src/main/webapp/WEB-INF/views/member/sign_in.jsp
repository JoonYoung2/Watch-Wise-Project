<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/js/common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<c:if test="${not empty msg }">
	<script type="text/javascript">
		alert('${msg}');
	</script>
</c:if>
	<div class="sign-in-box">
		<h3>로그인</h3>
		<form action="signInCheck" method="post"><br>
			<input type="text" id="email" name="userEmail" placeholder="email" value="${dto.userEmail }" ><br>
			<input type="password" id="pw" name="userPw" placeholder="password"value="${dto.userPw }"><br>
			<input type="submit" value="로그인">아직 회원이 아니신가요? <a href="/selectSignUpType">회원가입하러 가기</a>
		</form>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=36b59ada5e8b70c6afae51b77c038484&redirect_uri=http://localhost:8080/signIn/kakao&response_type=code">
			<img src="resources/img/kakaoLogin.png"/>
		</a>
		<a href="${naverLoginUrl}">
			<img src="resources/img/naverLogin.png"/>
		</a>
		<a href="${googleLoginUrl}">
			<img src="resources/img/googleLogin.png"/>
		</a>
	</div>
</body>
</html>