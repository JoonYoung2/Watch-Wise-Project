<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script type="text/javascript">
function confirmUnregister(){
	var confirmation = confirm("���� Ż���Ͻðڽ��ϱ�?");
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
		<input type="submit" value="Ż���ϱ�" onclick="return confirmUnregister();"><br>
		<a href = "/">Ȩ���� ����</a>
	</form>
</body>
</html>