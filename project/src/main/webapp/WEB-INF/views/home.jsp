<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/js2/common.js"></script>
</head>
<body>	
<%@ include file="/WEB-INF/views/header.jsp" %> 


	<c:if test="${signOutAlert == true }"><!--redirect 하면 알림 안뜸.-->
		<script type="text/javascript">
			alert('로그아웃 되었습니다.');
		</script>	
	</c:if>
	
	<c:if test="${unregisterAlert == true }">
		<script type="text/javascript">
			alert('회원 탈퇴가 완료되었습니다.');
		</script>	
	</c:if>
	
	<c:if test="${not empty msg }">
		<script type="text/javascript">
			alert('${msg}');
		</script>
	</c:if>
	
	
	<c:if test="${not empty sessionScope.userLoginType }">
		<h3>세션 있음</h3>
	</c:if>
	<c:if test="${empty sessionScope.userLoginType }">
		<h3>세션 없음</h3>
	</c:if>
	
	<a href="http://pf.kakao.com/_tDGjG"><img src="https://play-lh.googleusercontent.com/cf3DwPLbS8Z55sDvUSTQ6PL1goPdm8KbZ81g4TEUAaeUh5LDhkzdjxKQ2RX6BGpv0x4"></a>
</body>
</html>