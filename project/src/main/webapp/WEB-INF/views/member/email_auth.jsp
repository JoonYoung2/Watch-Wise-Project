<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/resources/js2/common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>

	<c:if test="${not empty msg }">
		<script type="text/javascript">
			alert('${msg}');
		</script>
	</c:if>
	
	<h3>인증번호를 발송했습니다. 인증번호를 입력해주세요.</h3>
	<form action="emailVerification" method="post">
		<input type="text" name="authKey" placeholder="인증코드">
		<input type="submit" value="인증하기">
	</form>
</body>
</html>