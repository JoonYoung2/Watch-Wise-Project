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
		<h3>Sign In</h3>
		<form action="signInCheck" method="post"><br>
			<input type="text" id="email" name="userEmail" placeholder="email"><br>
			<input type="password" id="pw" name="userPw" placeholder="password"><br>
			<input type="submit" value="Sign In">���� ȸ���� �ƴϽŰ���? <a href="/signUp">ȸ�������Ϸ� ����</a>
		</form>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=36b59ada5e8b70c6afae51b77c038484&redirect_uri=http://localhost:8080/signIn/kakao&response_type=code">
			<img src="resources/img/kakaoLogin.png"/>
		</a>
	</div>
</body>
</html>