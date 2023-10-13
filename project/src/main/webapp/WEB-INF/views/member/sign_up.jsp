<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
// document.getElementById("emailVerificationBtn").addEventListener("click", function () {
// 	let email = document.getElementById("email").value;
// 	let form = {email : email};
// 	$.ajax({
// 		url : "sendEmail",
// 		type : "post",
// 		data : JSON.stringify(form),
// 		contentType : "application/json; charset=utf-8",
// 		dataType : "json",
// 		success : function(data){
			
// 		}
// 	})

//   // AJAX 요청을 사용하여 MailService의 sendMail() 메소드 호출
//   var xhr = new XMLHttpRequest();
//   xhr.open("POST", "MailServiceEndpoint", true);
//   xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//   xhr.onreadystatechange = function () {
//     if (xhr.readyState === 4 && xhr.status === 200) {
//       // sendMail() 메소드 호출이 완료되었을 때 실행할 코드
//       var response = xhr.responseText;
//       // 이 코드로 sendMail() 메소드의 결과를 처리하거나 메시지를 표시할 수 있습니다.
//       // 페이지를 새로 고치지 않고 메시지를 표시할 수 있습니다.
//     }
//   };
//   xhr.send("email=" + email);
// });
</script>
</head>
<body>
	<div class="sign-up-box">
		<h3>Sign Up</h3>
		<form action="signUpDo" method="post"><br>
			<input type="email" id="email" name="userEmail" placeholder="Email"> <button id="emailVerificationBtn">이메일 인증</button> <hr>
			<input type="password" id="pw" name="userPw" placeholder="Password">(영문, 숫자, 특수문자 포함 10~15자)<hr>
			<input type="password" id="pwCh" name="pwCh" placeholder="Password Check"><hr>
			<input type="text" id="name" name="userName" placeholder="Name"><hr>
			<input type="hidden" name="userLoginType" value="1">
			<input type="hidden" name="socialLoginId" value="local">
			<input type="submit" value="Sign Up">이미 회원인가요? <a href="/signIn">로그인하러 가기</a>
		</form>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=36b59ada5e8b70c6afae51b77c038484&redirect_uri=http://localhost:8080/signIn/kakao&response_type=code">
			<img src="resources/img/kakaoLogin.png"/>
		</a>
	</div>
</body>
</html>