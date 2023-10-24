<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
//---------------------Sign out-----------------------------------------
function localConfirmSignOut(){
	var confirmation = confirm("로그아웃 하시겠습니까?");
	if(confirmation){
		window.location.href="/signOut";
	}
}

function kakaoConfirmSignOut(){
	var confirmation = confirm("로그아웃 하시겠습니까?");
	if(confirmation){
		window.location.href="";
	}
}

function naverConfirmSignOut(){
	var confirmation = confirm("로그아웃 하시겠습니까?");
	if(confirmation){
		window.location.href="/naverSignOut";
	}
}

function googleConfirmSignOut(){
	var confirmation = confirm("로그아웃 하시겠습니까?");
	if(confirmation){
		window.location.href="/googleSignOut";
	}
}


//---------------------Unregister-----------------------------------------

function kakaoConfirmUnregister(){
	var confirmation = confirm("정말 탈퇴 하시겠습니까?");
	if(confirmation){
		window.location.href="/kakaoUnregister";
	}
}

function naverConfirmUnregister(){
	var confirmation = confirm("정말 탈퇴 하시겠습니까?");
	if(confirmation){
		window.location.href="/naverUnregister?token=${sessionScope.accessToken }";
	}
}

function googleConfirmUnregister(){
	var confirmation = confirm("정말 탈퇴 하시겠습니까?");
	if(confirmation){
		window.location.href="/googleUnregister";
	}
}


</script>
</head>
<body>	
<%@ include file="/WEB-INF/views/header.jsp" %> 


	<c:if test="${signOutAlert == true }"><!--redirect 하면 알림 안뜸.-->
		<script type="text/javascript">
			alert('로그아웃 되었습니다.');
		</script>	
	</c:if>
	
	<c:if test="${unregisterAlert == true }">
		<script type="text/javascript">
			alert('회원 탈퇴가 완료되었습니다.');
		</script>	
	</c:if>
	
	<c:if test="${not empty msg }">
		<script type="text/javascript">
			alert('${msg}');
		</script>
	</c:if>
	
	
	<c:if test="${not empty sessionScope.userLoginType }">
		<h3>세션 있음</h3>
	</c:if>
	<c:if test="${empty sessionScope.userLoginType }">
		<h3>세션 없음</h3>
	</c:if>
	
	<a href="http://pf.kakao.com/_tDGjG"><img src="https://play-lh.googleusercontent.com/cf3DwPLbS8Z55sDvUSTQ6PL1goPdm8KbZ81g4TEUAaeUh5LDhkzdjxKQ2RX6BGpv0x4"></a>
</body>
</html>