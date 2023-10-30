<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<c:if test="${not empty msg}">
	<script type="text/javascript">
		alert('${msg}');
	</script>
</c:if>
	<h3>소셜로 간편가입하기</h3>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=36b59ada5e8b70c6afae51b77c038484&redirect_uri=http://localhost:8080/signIn/kakao&response_type=code">
			<img src="resources/img/kakaoLogin.png"/>
		</a><br>
		
		<a href="${naverLoginUrl}">
			<img src="resources/img/naverLogin.png"/>
		</a>
		
		<a href="${googleLoginUrl}">
			<img src="resources/img/googleLogin.png"/>
		</a>
		
		<br>
		<a href="/checkEmail">이메일로 가입하기</a>
</body>
</html>