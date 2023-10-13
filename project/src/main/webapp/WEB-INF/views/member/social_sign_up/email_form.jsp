<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div>${msg }
		<form action="/KakaoMemberRegister" method="post">
			<input type="hidden" name="userLoginType" value="2"> 
			<input type="hidden" name="socialLoginId" value="${member.socialLoginId }"><br>
			<input type="hidden" name="userName" value="${member.userName }"><br>
			<input type="hidden" name="userPw" value="kakaoMember"><br>
					
			<input type="email" name="userEmail" placeholder="example@example.com"  value="${member.userEmail }">
			<input type="submit" value="확인/인증">
		</form>
	</div>
</body>
</html>