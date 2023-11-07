<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:1px solid #ccc; margin:0px;">
<div  style="background-color:rgb(244, 244, 247); height:1000px;">
    <div class="user-info-main-card-frame" style="position: relative; top: 50px; background-color: white; width: 700px; height: 350px; margin: 0 auto; padding: 20px;">
		
		<div style="display:flex; justify-content:center">
			<div style="position:relative; margin-right:500px; margin-top:80px;">
				<img style="width:100px;" src="resources/img/bean_profile.png"/>
			</div>
			<div style="margin-top:20px; margin-right:0px;">
				<img style="width:30px; cursor:pointer;" src="resources/img/setting.png"/>
			</div>
		</div>
		
		<div style="margin-left:40px; margin-top:20px;">
			<div style="font-size:25px">
				<b>${dto.userName }</b>
			</div>
			<div>
				${dto.userEmail}
			</div>
		</div>
		
		<div style="display: flex; border-top: 1px solid rgb(244, 244, 247); margin-top:40px;">
			<a href="/userReviewList" style="flex: 1; text-align: center; text-decoration:none; margin-top:10px;">
				<span style="font-size:18px;">평가</span><br>
				<span>0</span>
			</a>
			<div style="background-color: rgb(232, 232, 239); width: 1px; height: 60px;"></div>
			<a href="/userReviewList" style="flex: 1; text-align: center; text-decoration:none; margin-top:10px;">
				<span style="font-size:18px;">코멘트</span><br>
				<span>0</span>
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
			<div style="margin-left:540px; margin-top:10px;">
				<img src="resources/img/next.png" style="width:8px;"/>
			</div>
		</div>
		
		<div onclick="moveToLikedActorList();" style="display:flex; font-size:20px; margin-left:25px; border-top: 1px solid rgb(244, 244, 247); cursor:pointer;">
			<div style="margin-top:10px; margin-bottom:10px;">
				좋아한 배우
			</div>
			<div style="margin-left:540px; margin-top:10px;">
				<img src="resources/img/next.png" style="width:8px;"/>
			</div>
		</div>
		
		<div onclick="moveToLikedCommentList();" style="display:flex; font-size:20px; margin-left:25px; border-top: 1px solid rgb(244, 244, 247); cursor:pointer;">
			<div style="margin-top:10px;">
				좋아한 코멘트
			</div>
			<div style="margin-left:520px; margin-top:10px;">
				<img src="resources/img/next.png" style="width:8px;"/>
			</div>
		</div>
		
	</div>
</div>

<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_common.js"></script>
<script src="/resources/js/member_info.js"></script>
</body>
</html>