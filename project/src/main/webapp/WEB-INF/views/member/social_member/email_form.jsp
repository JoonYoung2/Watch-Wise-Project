<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/resources/js/common.js"></script>
<script>
function validateForm() {
    var userEmailInput = document.getElementById("userEmail");
    var userEmailValue = userEmailInput.value.trim(); // Remove leading/trailing spaces

    if (userEmailValue === "") {
        alert("이메일을 입력하세요");
        return false; // 폼 제출을 중지
    }

    return true; // 폼을 제출
}
</script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
	<c:if test="${not empty msg }">
		<script type="text/javascript">
			alert('${msg}');
		</script>
	</c:if>
	<div>
		<form action="/kakaoEmailSend" method="post" onsubmit="return validateForm()">
			<input type="hidden" name="userLoginType" value="2"> 
			<input type="hidden" name="socialLoginId" value="${member.socialLoginId }"><br>
			<input type="hidden" name="userName" value="${member.userName }"><br>
			<input type="hidden" name="userPw" value="kakaoMember"><br>
					
			<input type="email" id="userEmail" name="userEmail" placeholder="example@example.com"  value="${member.userEmail }">
			<input type="submit" value="확인/인증">
		</form>
	</div>
</body>
</html>