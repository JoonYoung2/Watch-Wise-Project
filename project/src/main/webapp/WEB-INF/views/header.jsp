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
	<header>
	<div id="header" style="text-align:right;">
	<a href="/">HOME</a>
	<c:if test="${empty sessionScope.userLoginType }">
		<a href="selectSignUpType">sign up</a>
		<a href="/signIn">sign in</a>
	</c:if>
	<!---------------------------------------------------- Kakao ---------------------------------------------------------------------------------- -->
	<c:if test="${sessionScope.userLoginType eq 2 }">
		<a style="cursor:pointer" href="https://kauth.kakao.com/oauth/logout?client_id=36b59ada5e8b70c6afae51b77c038484&logout_redirect_uri=http://localhost:8080/kakaoSignOut" >
		sign out
		</a>
		<a style="cursor:pointer" onclick="socialConfirmUnregister()">
		Unregister
		</a>
		<a style="corsor:pointer" href="/socialMemberInfo">
			Info
		</a>
	</c:if>
	
	<!---------------------------------------------------- Naver ---------------------------------------------------------------------------------- -->
	<c:if test="${sessionScope.userLoginType eq 3 }">
		<a style="cursor:pointer" onclick="naverConfirmSignOut()"><!-- 혹시 탈퇴하거나 할 때 정보 동의 다 철회하고 토큰도 없애고 싶다면 a href ="remove?token=${sessionScope.accessToken }" 으로 하면 된다.-->
		sign out
		</a>
		<a style="cursor:pointer" onclick="socialConfirmUnregister()">
		Unregister
		</a>
		<a style="corsor:pointer" href="/socialMemberInfo">
			Info
		</a>
	</c:if>
		
	<!---------------------------------------------------- Google ---------------------------------------------------------------------------------- -->
	
	<c:if test="${sessionScope.userLoginType eq 4 }">
		<a style="cursor:pointer" onclick="googleConfirmSignOut()">
		sign out
		</a>
		<a style="cursor:pointer" onclick="socialConfirmUnregister()">
		Unregister
		</a>
		<a style="corsor:pointer" href="/socialMemberInfo">
			Info
		</a>
	</c:if>
	
	<!---------------------------------------------------- Local ---------------------------------------------------------------------------------- -->
	
	<c:if test="${sessionScope.userLoginType eq 0 || sessionScope.userLoginType eq 1  }">
		<a style="cursor:pointer" onclick="localConfirmSignOut()">
			sign out
		</a>
		<a href="/unregister">Unregister</a>
		<a href="/localMemberInfo">Info</a>
	</c:if>
		<!-------------------------------------------------------------------------------------------------------------------------------------- -->
	</div>
	<hr>
	</header>
</body>
</html>