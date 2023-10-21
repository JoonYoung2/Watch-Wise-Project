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
	<c:if test="${signOutAlert == true }"><!--redirect 하면 알림 안뜸.-->
		<script type="text/javascript">
			alert('로그아웃되었습니다.');
		</script>	
	</c:if>
	
	<c:if test="${not empty msg }">
		<script type="text/javascript">
			alert('${msg}');
		</script>
	</c:if>
	
	
	<a href="selectSignUpType">sign up</a>
	<a href="/signIn">sign in</a>
	
	<!---------------------------------------------------- Kakao ---------------------------------------------------------------------------------- -->
	<c:if test="${sessionScope.userLoginType eq 2 }">
		<a style="cursor:pointer" href="https://kauth.kakao.com/oauth/logout?client_id=36b59ada5e8b70c6afae51b77c038484&logout_redirect_uri=http://localhost:8080/kakaoSignOut" >
		sign out
		</a>
		<a style="cursor:pointer" onclick="kakaoConfirmUnregister()">
		Unregister
		</a>
	</c:if>
	
	<!---------------------------------------------------- Naver ---------------------------------------------------------------------------------- -->
	<c:if test="${sessionScope.userLoginType eq 3 }">
		<a style="cursor:pointer" onclick="naverConfirmSignOut()"><!-- 혹시 탈퇴하거나 할 때 정보 동의 다 철회하고 토큰도 없애고 싶다면 a href ="remove?token=${sessionScope.accessToken }" 으로 하면 된다.-->
		sign out
		</a>
		<a style="cursor:pointer" onclick="naverConfirmUnregister()">
		Unregister
		</a>

		
	<!---------------------------------------------------- Google ---------------------------------------------------------------------------------- -->
		
	</c:if>
	<c:if test="${sessionScope.userLoginType eq 4 }">
		<a style="cursor:pointer" onclick="googleConfirmSignOut()">
		sign out
		</a>
		<a style="cursor:pointer" onclick="googleConfirmUnregister()">  완료
		Unregister
		</a>
	</c:if>
	<!---------------------------------------------------- Local ---------------------------------------------------------------------------------- -->
	
	<c:if test="${sessionScope.userLoginType eq 0 || sessionScope.userLoginType eq 1  }">
		<a style="cursor:pointer" onclick="localConfirmSignOut()">
		sign out
		</a>
		<a href="/unregister">Unregister</a>
	</c:if>
		<!-------------------------------------------------------------------------------------------------------------------------------------- -->
	
	
	
	
	
	
	
	
	
	<c:if test="${not empty sessionScope.userLoginType }">
		<h3>세션 있음</h3>
	</c:if>
	<c:if test="${empty sessionScope.userLoginType }">
		<h3>세션 없음</h3>
	</c:if>
	
	<a href="http://pf.kakao.com/_tDGjG">카톡 채널 추가하기</a>
</body>
</html>