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
			<input type="text" id="id" name="userId"><br>
			<input type="password" id="pw" name="userPw"><br>
			<input type="submit" value="Sign In">아직 회원이 아니신가요? <a href="/signUp">회원가입하러 가기</a>
		</form>
	</div>
</body>
</html>