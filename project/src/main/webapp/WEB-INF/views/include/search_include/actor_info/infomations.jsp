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
	<a style="all:unset; cursor:pointer;" href="/peopleInfo?peopleId=${ peopleInfo.peopleId }">
		<div align="left" style="display:flex;">
			<div>
				<c:if test="${ peopleInfo.profileUrl ne 'nan' }">
					<div style="width: 80px; height: 80px; overflow: hidden; border-radius:100px;">
				        <img style="max-width: 100%;" src="${ peopleInfo.profileUrl }" alt="영화 포스터">
				    </div>
				</c:if>
				<c:if test="${ peopleInfo.profileUrl eq 'nan' }">
					<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
				</c:if>
			</div>
			<div style="padding-left:20px;">
				<div style="display:flex; align-items:center;">
					<div>
						<span style="font-size:20px; font-weight:bold; color:rgba(0, 0, 0, 0.5);">
							${ peopleInfo.sex }・배우
						</span>
					</div>
					<div style="display:flex; align-items:center; padding-left:7px;">
						<c:if test="${ not empty sessionScope.userEmail }">
							<c:if test="${ likeCheck[peopleCnt] eq 0 }">
								<img style="width:16px;" src="/resources/img/like.png"> <span style="padding-left:7px;">${ peopleInfo.likeNum }</span>
							</c:if>
							<c:if test="${ likeCheck[peopleCnt] eq 1 }">
								<img style="width:16px;" src="/resources/img/likeColor.png"> <span style="padding-left:7px; color:red;">${ peopleInfo.likeNum }</span>
							</c:if>
						</c:if>
						<c:if test="${ empty sessionScope.userEmail }">
							<img style="width:16px;" src="/resources/img/like.png"> <span style="padding-left:7px;">${ peopleInfo.likeNum }</span>
						</c:if>
					</div>
				</div>
				<c:if test="${ peopleInfo.peopleNmEn eq 'nan' }">
					<span style="font-size:30px; font-weight:bold;">${ peopleInfo.peopleNm }</span><br><br>
				</c:if>
				<c:if test="${ peopleInfo.peopleNmEn ne 'nan' }">
					<span style="font-size:30px; font-weight:bold;">${ peopleInfo.peopleNm } ( ${ peopleInfo.peopleNmEn } )</span><br><br>
				</c:if>
			</div>
		</div>
	</a>
</body>
</html>