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
			<div align="left" class="actor-div">
				<div>
					<c:if test="${ peopleInfo.profileUrl ne 'nan' }">
						<div class="profile-div">
					        <img class="profile" src="${ peopleInfo.profileUrl }" alt="영화 포스터">
					    </div>
					</c:if>
					<c:if test="${ peopleInfo.profileUrl eq 'nan' }">
						<img class="not-porofile" src="/resources/img/bean_profile.png">
					</c:if>
				</div>
				<div class="actor-info-div">
					<span class="sex-font">${ peopleInfo.sex }・배우</span><br>
					<c:if test="${ peopleInfo.peopleNmEn eq 'nan' }">
						<span class="actor-info-font">${ peopleInfo.peopleNm }</span><br><br>
					</c:if>
					<c:if test="${ peopleInfo.peopleNmEn ne 'nan' }">
						<span class="actor-info-font">${ peopleInfo.peopleNm } ( ${ peopleInfo.peopleNmEn } )</span><br><br>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>