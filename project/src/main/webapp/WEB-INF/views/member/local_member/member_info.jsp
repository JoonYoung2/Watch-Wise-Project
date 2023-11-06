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
<hr>
<c:if test="${not empty msg }">
	<script>
		alert('${msg}');
	</script>
</c:if>
<div style="position: absolute; top: 50%; left: 50%;transform: translate(-50%, -50%);">
<h3 style="text-align: center;"> My Informations </h3>
	<table border="1">
		<tr>
			<th>이메일</th>
			<td>${dto.userEmail}</td>
		</tr>
		<tr>
			<th>이름</th>
			<td>${dto.userName }</td>
		</tr>
		<tr>
			<th>내가 평가한 영화</th>
			<td><a href="/userReviewList">목록 보기</a></td>  <!-- com.watch.project.controller.review.ReviewController -->
		</tr>
		<tr>
			<th>내가 좋아요한 코멘트</th>
			<td><a href="/userLikedCommentList">목록 보기</a></td>  <!-- com.watch.project.controller.review.ReviewController -->
		</tr>
		<tr>
			<th>내가 좋아요한 영화</th>
			<td><a href="/userLikedMovieList">목록 보기</a></td>  <!-- com.watch.project.controller.member.MemberInfoController -->
		</tr>
		<tr>
			<th>내가 좋아요한 배우</th>
			<td><a href="/userLikedActorList">목록 보기</a></td>  <!-- com.watch.project.controller.member.MemberInfoController -->
		</tr>
	</table>
<a href="/pwCheck">정보 수정하기</a>
</div>
<hr>
<div>
	<!--  가져와야 하는 정보  -->
	<!--  
		내가 평가한 영화 => 내가 평가한 영화들 목록과 내가 매긴 평점 
		내가 작성한 코멘트 =>	내가 작성한 코멘트들과 영화
	 -->
</div>
</body>
</html>