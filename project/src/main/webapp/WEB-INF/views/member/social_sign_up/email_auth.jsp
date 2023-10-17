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
	<h3>인증번호를 발송했습니다. 인증번호를 입력해주세요.</h3>
	<form action="kakaoEmailVerification" method="post">
		<input type="hidden" name="userLoginType" value="2"> 
		<input type="hidden" name="socialLoginId" value="${dto.socialLoginId }"><br>
		<input type="hidden" name="userName" value="${dto.userName }"><br>
		<input type="hidden" name="userPw" value="kakaoMember"><br>
		
		<input type="text" name="authKey" placeholder="인증코드"> 
		<input type="submit" value="인증하기">
	</form>
	${msg }
</body>
</html>