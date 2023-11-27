<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center" class="page-start">
		<div align="center" class="page-width">
			<hr>
				<div id="likeDiv">
					<c:if test="${ likeCheck eq 0 }">
						<div class="likeAdd like-add" onclick="likeAdd('${ peopleInfo.peopleId }');">
							<img class="like-img" src="/resources/img/like.png"> <span class="like-font">좋아요 ${ peopleInfo.likeNum }명이 이 인물을 좋아합니다.</span>
						</div>
					</c:if>
					<c:if test="${ likeCheck eq 1 }">
						<div class="likeCancel like-cancel" onclick="likeCancel('${ peopleInfo.peopleId }');">
							<img class="like-img" src="/resources/img/likeColor.png"> <span class="like-font">좋아요 ${ peopleInfo.likeNum }명이 이 인물을 좋아합니다.</span>
						</div>
					</c:if>
				</div>
			<hr>
		</div>
	</div>
</body>
</html>