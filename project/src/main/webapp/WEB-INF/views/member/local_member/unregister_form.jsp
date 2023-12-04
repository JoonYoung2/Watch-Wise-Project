<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:1px solid #ccc;">

<c:if test="${not empty msg }">
	<script type="text/javascript">
		alert('${msg}');
	</script>
</c:if>

<div class="sign-up-frame" style="position: absolute;top: 50%; left: 50%; transform: translate(-50%, -50%);width:400px; height:280px; border:1px solid #ccc; border-radius: 5px; margin-top: 30px;">
	<div align="center" style="padding-top: 30px; margin-bottom:30px">
		<h4>정말 탈퇴하시겠습니까?</h4>탈퇴하시려면 비밀번호를 입력해주세요.
	</div>	

	<div align="center" style="">
		<form id="unregisterForm" action="/passwordCh" method="post">
			<input type="password" id="pw" name="userPw" placeholder="password"><br><br>
			<input type="submit" value="탈퇴하기" ><br> <!-- onclick="return confirmUnregister();" -->
		</form>
	</div>
</div>

	
</body>
</html>