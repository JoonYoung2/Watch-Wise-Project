<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
	<div class="sign-up-box">
		<h3>회원가입</h3>
		${msg }
		<form action="signUpDo" method="post"><br>
			<input type="email" name="userEmail" value="${userEmail}" readonly><hr>
			<input type="password" id="pw" name="userPw"  placeholder="Password">(영문, 숫자, 특수문자 포함 10~15자)<hr>
			<input type="password" id="pwCh" name="pwCh" placeholder="Password Check"><hr>
			<input type="text" id="name" name="userName" value="${dto.userName}" placeholder="Name"><hr>
			<input type="hidden" name="userLoginType" value="1">
			<input type="hidden" name="socialLoginId" value="local">
			
			<input type="submit" value="Sign Up">이미 회원인가요? <a href="/signIn">로그인하러 가기</a>
		</form>

	</div>
</body>
</html>