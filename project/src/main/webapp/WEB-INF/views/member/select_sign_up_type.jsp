<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

<c:if test="${not empty msg}">
	<script type="text/javascript">
		alert('${msg}');
	</script>
</c:if>
<div class="sign-up-frame" style="width:400px; height:400px; border:1px solid #ccc; border-radius: 5px; margin-top: 30px;">
	<div align="center" style="margin-top: 28px; margin-bottom:28px">
		<h2>Sign Up</h2>
	</div>	

	<div align="center">
		<a href="${naverLoginUrl}">
			<img src="resources/img/naverSignUp.png"/>
		</a> 
		<br><br>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=36b59ada5e8b70c6afae51b77c038484&redirect_uri=http://localhost:8080/signIn/kakao&response_type=code">
			<img src="resources/img/kakaoSignUp.png"/>
		</a>
		<br><br>
		<a href="${googleLoginUrl}">
			<img src="resources/img/googleSignUp.png"/>
		</a>
		<br><br>
		<a href="/checkEmail">
			<img src="resources/img/emailSignUp.png"/>	
		</a>
	</div>
	
	<div style="font-size:12px; text-align:center; margin-top:25px;">
			이미 회원이신가요? <a href="/signIn">로그인하러 가기</a>
	</div>
</div>
<style>
.sign-up-frame {
    position: absolute; /* 절대 위치로 설정 */
    top: 50%; /* 상단에서 50% 위치에 배치 */
    left: 50%; /* 왼쪽에서 50% 위치에 배치 */
    transform: translate(-50%, -50%); /* 가운데로 이동 */
}
</style>
</body>
</html>