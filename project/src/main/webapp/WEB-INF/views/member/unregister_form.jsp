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
 	var confirmation = confirm("���� Ż���Ͻðڽ��ϱ�?");
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
<h4>���� Ż���Ͻðڽ��ϱ�?</h4>
Ż���Ͻ÷��� ��й�ȣ�� �Է����ּ���.
	<form id="unregisterForm" action="passwordCh" method="post">
		<input type="password" id="pw" name="userPw" placeholder="password"><br>
		<input type="submit" value="Ż���ϱ�" ><br> <!-- onclick="return confirmUnregister();" -->
		<a href = "/">Ȩ���� ����</a>
	</form>
</body>
</html>