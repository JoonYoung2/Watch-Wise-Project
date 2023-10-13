<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function confirmSignOut(){
	var confirmation = confirm("로그아웃 하시겠습니까?");
	if(confirmation){
		window.location.href="/signOut";
	}
}
</script>
</head>
<body>
	gdgd
	
	<a href="/signUp">sign up</a>
	<a href="/signIn">sign in</a>
	
	
	<c:if test="${sessionScope.userLoginType eq 2 }">
		<a href="https://kauth.kakao.com/oauth/logout?client_id=36b59ada5e8b70c6afae51b77c038484&logout_redirect_uri=http://localhost:8080/kakaoSignOut">
		sign out
		</a>
	</c:if>
	<c:if test="${sessionScope.userLoginType eq 0 || sessionScope.userLoginType eq 1  }">
	<a href="javascript:void(0);" onclick="confirmSignOut()">sign out</a>
	</c:if>
	
	
	<a href="/unregister">Unregister</a>
	
	<c:if test="${not empty sessionScope.userEmail }">
		<h3>세션 있음</h3>
	</c:if>
	<c:if test="${empty sessionScope.userEmail }">
		<h3>세션 없음</h3>
	</c:if>
	
	<a href="http://pf.kakao.com/_tDGjG">카톡 채널 추가하기</a>
</body>
</html>