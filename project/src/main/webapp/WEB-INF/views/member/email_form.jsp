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

<c:if test="${not empty msg }">
	<script type="text/javascript">
		alert('${msg}');
	</script>
</c:if>
	<h3>이메일</h3>
	<form action="sendEmailforAuth" method="post">
		<input type="email" name="userEmail" placeholder="example@example.com">
		<input type="submit" value="인증코드 발송">
	</form>
</body>
</html>