<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
	<div class="sign-up-box">
		<h3>Sign Up</h3>
		<form action="signUpDo" method="post"><br>
			<input type="text" id="id" name="userId" placeholder="Id">(영문, 숫자 포함 8~12자)<hr>
			<input type="password" id="pw" name="userPw" placeholder="Password">(영문, 숫자, 특수문자 포함 10~15자)<hr>
			<input type="password" id="pwCh" name="pwCh" placeholder="Password Check"><hr>
			<input type="text" id="name" name="userName" placeholder="Name"><hr>
			<input type="email" id="email" name="userEmail" placeholder="Email"><hr>
			<input type="hidden" name="userLoginType" value="1">
			<input type="submit" value="Sign Up">이미 회원인가요? <a href="/signIn">로그인하러 가기</a>
		</form>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=36b59ada5e8b70c6afae51b77c038484&redirect_uri=http://localhost:8080/signIn/kakao&response_type=code">
			<img src="resources/img/kakaoLogin.png"/>
		</a>
	</div>
</body>
</html>