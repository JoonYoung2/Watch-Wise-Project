<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<div class="sign-in-box">
		<h3>로그인</h3>
		<form action="signInCheck" method="post"><br>
			<input type="text" id="email" name="userEmail" placeholder="email"><br>
			<input type="password" id="pw" name="userPw" placeholder="password"><br>
			<input type="submit" value="로그인">아직 회원이 아니신가요? <a href="/signUp">회원가입하러 가기</a>
		</form>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=36b59ada5e8b70c6afae51b77c038484&redirect_uri=http://localhost:8080/signIn/kakao&response_type=code">
			<img src="resources/img/kakaoLogin.png"/>
		</a>
		<a href="${naverLoginUrl}">
			<img src="resources/img/naverLogin.png"/>
		</a>
	</div>
</body>
</html>