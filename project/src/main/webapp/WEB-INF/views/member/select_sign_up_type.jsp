<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>소셜로 간편가입하기</h3>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=36b59ada5e8b70c6afae51b77c038484&redirect_uri=http://localhost:8080/signIn/kakao&response_type=code">
			<img src="resources/img/kakaoLogin.png"/>
		</a><br>
		<a href="${naverLoginUrl}">
			<img src="resources/img/naverLogin.png"/>
		</a>
		<br>
		<a href="/checkEmail">이메일로 가입하기</a>
</body>
</html>