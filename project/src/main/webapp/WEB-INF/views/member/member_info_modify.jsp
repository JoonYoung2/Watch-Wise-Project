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
<%@ include file="/WEB-INF/views/header.jsp" %>

<c:if test="${not empty msg }">
	<script type="text/javascript">
		alert('${msg}');
	</script>
</c:if>
<form action="/localModifyInfoDo" method="post">
	<table border="1">
		<tr>
			<th>이메일</th>
			<td>이메일은 변경이 불가합니다.<br><input type="text" name="userEmail" value="${dto.userEmail}" readonly></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><input type="text" name="userName" value="${dto.userName }"></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td>변경을 원할 경우에만 작성해주세요.<br><input type="password" name="userPw" ></td>
		</tr>
		<tr>
			<th>비밀번호 확인</th>
			<td><input type="password" name="pwCheck"></td>
		</tr>
	</table>
	<input type="submit" value="수정하기">
</form>
<a href="/localMemberInfo">뒤로가기</a>
</body>
</html>