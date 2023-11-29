<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<hr style="border:1px solid #ccc;">

<c:if test="${not empty msg }">
	<script type="text/javascript">
		alert('${msg}');
	</script>
</c:if>

<div class="sign-up-frame" style="position: absolute;top: 50%; left: 50%; transform: translate(-50%, -50%);width:400px; height:200px; border:1px solid #ccc; border-radius: 5px; margin-top: 30px;">
	<div align="center" style="margin-top: 28px; margin-bottom:30px">
		<h2>이메일</h2>
	</div>	

	<div align="center">
		<form action="sendEmailforAuth" method="post">
			<input type="email" name="userEmail" placeholder="example@example.com">
			<input type="submit" value="인증코드 발송">
		</form>
	</div>
</div>
</body>
</html>