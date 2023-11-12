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
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:1px solid #ccc;">

<div class="liked-actor-list-frame">
<div style="margin-left:20px;">
	<a href="/memberInfo">
		<img src="resources/img/back.png" style="width:20px; margin-top:20px;">
	</a>
	<div class="liked-actor-title">
		<h3 style="text-align:center;">내가 좋아요한 배우</h3>	
	</div>
</div>
	
	<div align="center" class="liked-actor-list">
		<table style="text-align:center">
		<c:set var="cnt" value="0"/>
			<tr>
				<c:forEach var="list" items="${likedActorList }">
					<c:if test="${ cnt%4==0}">
						</tr>
						<tr>
					</c:if>
					<td height="200" width="200" style="text-align:center;">
						<div style="position:relative">
							<div>
								<c:if test="${ list.profileUrl ne 'nan' }">
									<div align="center">
										<div style="width: 80px; height: 80px; overflow: hidden; border-radius:100px;">
									        <img style="max-width: 100%;" src="${ list.profileUrl }" alt="영화 포스터">
									    </div>
								    </div>
								</c:if>
								<c:if test="${ list.profileUrl eq 'nan' }">
									<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
								</c:if>
								<div class="liked-actor-list-heart">
									<img src="/resources/img/likeColor.png" onclick="actorLikeCancel('${ list.peopleId }','${cnt }');" style="cursor:pointer; width:20px; position:absolute; bottom:20px; right:60px;">
								</div>
								<div onclick="moveToActorInfo('${list.peopleId}');" style="font-size:17px; font-weight:bold; cursor:pointer;">${list.peopleNm}</div>
							</div>
						</div>
					</td>
					<c:set var="cnt" value="${cnt+1 }"/>
				</c:forEach>
			</tr>
		</table>
	</div>
</div>
<div>
	
	
	
</div>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_common.js"></script>
<script src="/resources/js/member_info.js"></script>
</body>
</html>