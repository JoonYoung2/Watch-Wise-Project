<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<c:if test="${not empty msg }">
	<script>
		alert('${msg}');
	</script>
</c:if>
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:1px solid #ccc; margin:0px;">

<div  style="background-color:rgb(244, 244, 247); height:1000px;">
    <div class="user-info-main-card-frame" style="position: relative; top: 50px; background-color: white; width: 700px; height: 350px; margin: 0 auto; padding: 20px;">
		
		<div style="display:flex; justify-content:center">
			<div style="position:relative; margin-right:500px; margin-top:80px;">
				<img style="width:100px;" src="resources/img/bean_profile.png"/>
			</div>
			<div style="margin-top:20px; margin-right:0px;">
				<button id="openModalButton">
					<img style="width:30px; cursor:pointer;" onclick="moveToSetting('${dto.userEmail}');" src="resources/img/setting.png"/>
				</button>
			</div>
		</div>
		
		<div style="margin-left:40px; margin-top:20px;">
			<div style="font-size:25px; display:flex;">
				<div>
					<b>${dto.userName }</b>
				</div>
				<div style="margin-left:10px;">
					<c:choose>
						<c:when test="${sessionScope.userLoginType eq 0 || sessionScope.userLoginType eq 1  }"><!-- local이거나 admin이면 -->
							<a href="/pwCheck">
						</c:when>
						<c:otherwise>
							<a href="socialMemberInfoModify">
						</c:otherwise>
						</c:choose>
							<img src="resources/img/pencil.png" style="width:20px;"/>
							</a>
				</div>
			</div>
			<div>
				${dto.userEmail}
			</div>
		</div>
		
		<div style="display: flex; border-top: 1px solid rgb(244, 244, 247); margin-top:40px;">
			<a href="/userReviewList" style="flex: 1; text-align: center; text-decoration:none; margin-top:10px;">
				<span style="font-size:18px;">평가</span><br>
				<span>${map['score']}</span>
			</a>
			<div style="background-color: rgb(232, 232, 239); width: 1px; height: 60px;"></div>
			<a href="/userReviewList" style="flex: 1; text-align: center; text-decoration:none; margin-top:10px;">
				<span style="font-size:18px;">코멘트</span><br>
				<span>${map['comment']}</span>
			</a>
		</div>
	</div>
	
	<div style="height:10px;"></div>
	
	<div class="user-info-like-card-frame" style="position: relative; top: 50px; background-color: white; width: 700px; height: 200px; margin: 0 auto; padding: 20px;">
		<div style="display:flex; font-size:25px; margin-top:-10px; margin-left:25px; margin-bottom:15px;">
			<div style="margin-top:10px;">
				<b>좋아요</b>
			</div>
			<div style="margin-top:12px; margin-left:10px;">
				<img src="resources/img/likeColor.png" style="width:30px;"/>
			</div>
		</div>
		
		<div onclick="moveToLikedMovieList();" style="display:flex; font-size:20px; margin-left:25px; border-top: 1px solid rgb(244, 244, 247); cursor:pointer;">
			<div style="margin-top:10px; margin-bottom:10px;">
				좋아한 영화
			</div>
			<div style="margin-left:10px; margin-top:8px; color:grey;">
				(${map['likedMovie']})
			</div>
			<div style="margin-left: auto; margin-top:10px;">
				<img src="resources/img/next.png" style="width:8px;"/>&nbsp	
			</div>
		</div>
		
		<div onclick="moveToLikedActorList();" style="display:flex; font-size:20px; margin-left:25px; border-top: 1px solid rgb(244, 244, 247); cursor:pointer;">
			<div style="margin-top:10px; margin-bottom:10px;">
				좋아한 배우
			</div>
			<div style="margin-left:10px; margin-top:8px; color:grey;">
				(${map['likedActor']})
			</div>
			<div style="margin-left: auto; margin-top:10px;">
				<img src="resources/img/next.png" style="width:8px;"/>&nbsp	
			</div>
		</div>
		
		<div onclick="moveToLikedCommentList();" style="display:flex; font-size:20px; margin-left:25px; border-top: 1px solid rgb(244, 244, 247); cursor:pointer;">
			<div style="margin-top:10px;">
				좋아한 코멘트
			</div>
			<div style="margin-left:10px; margin-top:8px; color:grey;">
				(${map['likedComment']})
			</div>
			<div style="margin-left: auto; margin-top:10px;">
				<img src="resources/img/next.png" style="width:8px;"/>&nbsp	
			</div>
		</div>
		
	</div>
</div>


<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" id="closeModalButton">&times;</span>
        <p>모달 내용을 여기에 입력하세요.</p>
    </div>
</div>

<script src="/resources/js/common.js"></script>
<script src="/resources/js/member_info.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>