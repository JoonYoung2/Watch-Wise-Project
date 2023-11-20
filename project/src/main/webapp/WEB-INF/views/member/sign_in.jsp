<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:1px solid #ccc;">
<br>
<br>
<c:if test="${not empty msg }">
	<script type="text/javascript">
		alert('${msg}');
	</script>
</c:if>
<div class="sign-in-frame" style="width:400px; height:380px; border:1px solid #ccc; border-radius: 5px; margin-top: 30px;">
	<div align="center" style="margin-top: 45px;">
		<h2>Sign In</h2>
	</div>	
	<div align="center">
		<form action="signInCheck" method="post"><br>
			<div style="width:300px;">
				<div align="center" class="sign-in-email-input" style="margin-bottom: 6px;">
				<!-- 
					<div style="margin-right:5px;">
					Email
					</div>
				 -->			
				<input style="width:200px;" type="text" id="email" name="userEmail" placeholder="Email" value="${dto.userEmail }" ><br>
				</div>
				
				<div align="center" class="sign-in-pw-input" style="margin-bottom: 10px;">
				<!-- 
					<div style="margin-right:5px;">
					Password
					</div>
				 -->
				<input style="width:200px;" type="password" id="pw" name="userPw" placeholder="Password"value="${dto.userPw }"><br>
				</div>
			</div>
			<div style="cursor:pointer; margin-bottom:30px;">
			<input type="submit" id="submit-button" style="display: none;">						
			<img src="resources/img/signInEmail.png" style="width:210px;" id="submit-image">
			</div>
		</form>
	</div>
	<div style="font-size:12px; margin-bottom:10px;">
	-------------------------------- Social Sign In --------------------------------
	</div>
	
	<div align="center">
		<a style="text-decoration:none; margin-right:15px;" href="${naverLoginUrl}">
			<img style="width:50px; cursor:pointer;" src="resources/img/naverSignIn.png"/>
		</a>
		<a style="text-decoration:none; margin-right:15px;" href="https://kauth.kakao.com/oauth/authorize?client_id=36b59ada5e8b70c6afae51b77c038484&redirect_uri=http://localhost:8080/signIn/kakao&response_type=code">
			<img style="width:50px; cursor:pointer;" src="resources/img/kakaoSignIn.png"/>
		</a>
		<a style="text-decoration:none;" href="${googleLoginUrl}">
			<img style="width:50px; cursor:pointer;" src="resources/img/googleSignIn.png"/>
		</a>
	</div>
	<div style="font-size:12px; text-align:center; margin-top:25px;">
			아직 회원이 아니신가요? <a href="/selectSignUpType">회원가입하러 가기</a>
	</div>
</div>
    <script>
        document.getElementById("submit-image").addEventListener("click", function() {
            document.getElementById("submit-button").click();
        });
    </script>
<style>
.sign-in-frame {
    position: absolute; /* 절대 위치로 설정 */
    top: 50%; /* 상단에서 50% 위치에 배치 */
    left: 50%; /* 왼쪽에서 50% 위치에 배치 */
    transform: translate(-50%, -50%); /* 가운데로 이동 */
}
</style>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>