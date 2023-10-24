<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<!-- <script type="text/javascript">
 function confirmUnregister(){
 	var confirmation = confirm("정말 탈퇴하시겠습니까?");
 	if(confirmation){
 		document.getElementById('unregisterForm').submit();
 	}
 	return confirmation;
 }
</script> -->
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<c:if test="${not empty msg }">
	<script type="text/javascript">
		alert('${msg}');
	</script>
</c:if>
<h4>정말 탈퇴하시겠습니까?</h4>
탈퇴하시려면 비밀번호를 입력해주세요.
	<form id="unregisterForm" action="passwordCh" method="post">
		<input type="password" id="pw" name="userPw" placeholder="password"><br>
		<input type="submit" value="탈퇴하기" ><br> <!-- onclick="return confirmUnregister();" -->
		<a href = "/">홈으로 가기</a>
	</form>
</body>
</html>