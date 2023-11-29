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
<hr style="border:1px solid #ccc;">


<div class="sign-up-frame" style="position: absolute;top: 50%; left: 50%; transform: translate(-50%, -50%);width:400px; height:340px; border:1px solid #ccc; border-radius: 5px; margin-top: 30px;">
	<div align="center" style="margin-top: 28px; margin-bottom:30px">
		<h3>회원가입</h3>
	</div>	

	<div align="center" style="">
		${msg }
		<form action="signUpDo" method="post"><br>
			<input type="email" name="userEmail" value="${userEmail}" readonly><hr>
			<input type="password" id="pw" name="userPw"  placeholder="Password"><div style="font-size:12px;">(영문, 숫자, 특수문자 포함 10~15자)</div><hr>
			<input type="password" id="pwCh" name="pwCh" placeholder="Password Check"><hr>
			<input type="text" id="name" name="userName" value="${dto.userName}" placeholder="Name"><hr>
			<input type="hidden" name="userLoginType" value="1">
			<input type="hidden" name="socialLoginId" value="local">
			<input type="submit" value="Sign Up">
		</form>		
	</div>
</div>
</body>
</html>