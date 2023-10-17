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
	<form action="emailVerification" method="post">
		<input type="text" name="authKey" placeholder="인증코드">
		<input type="submit" value="인증하기">
	</form>
${msg }
</body>
</html>