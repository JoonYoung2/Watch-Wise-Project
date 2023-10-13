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
	<a href="javascript:void(0);" onclick="confirmSignOut()">sign out</a>
	<a href="/unregister">Unregister</a>
	
	<c:if test="${not empty sessionScope.userId }">
		<h3>세션 있음</h3>
	</c:if>
	<c:if test="${empty sessionScope.userId }">
		<h3>세션 없음</h3>
	</c:if>
</body>
</html>