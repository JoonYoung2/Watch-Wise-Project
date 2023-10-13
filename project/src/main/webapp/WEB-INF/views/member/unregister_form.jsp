<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script type="text/javascript">
function confirmUnregister(){
	var confirmation = confirm("정말 탈퇴하시겠습니까?");
	if(confirmation){
		document.getElementById('unregisterForm').submit();
	}
	return confirmation;
}
</script>
</head>
<body>
	<form id="unregisterForm" action="passwordCh" method="post">
		<input type="password" id="pw" name="userPw"><br>
		<input type="submit" value="탈퇴하기" onclick="return confirmUnregister();"><br>
		<a href = "/">홈으로 가기</a>
	</form>
</body>
</html>