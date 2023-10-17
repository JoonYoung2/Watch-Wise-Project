<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>이메일</h3>
	<form action="sendEmailforAuth" method="post">
		<input type="email" name="userEmail" placeholder="example@example.com">
		<input type="submit" value="인증코드 발송">
	</form>
</body>
</html>