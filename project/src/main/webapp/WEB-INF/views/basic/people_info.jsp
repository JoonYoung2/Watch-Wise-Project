<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<link rel="stylesheet" href="/resources/css/people_info.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<c:set var="imgWidth" value="300"/>
<c:set var="imgHeight" value="428.16"/>

<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
<!-- 		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">프로필</span></div> -->
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
				<span stylze="font-size:20px; font-weight:bold; color:rgba(0, 0, 0, 0.5);">${ peopleInfo.sex }・배우</span><br>
				<c:if test="${ peopleInfo.peopleNmEn eq 'nan' }">
					<span style="font-size:30px; font-weight:bold;">${ peopleInfo.peopleNm }</span><br><br>
				</c:if>
				<c:if test="${ peopleInfo.peopleNmEn ne 'nan' }">
					<span style="font-size:30px; font-weight:bold;">${ peopleInfo.peopleNm } ( ${ peopleInfo.peopleNmEn } )</span><br><br>
				</c:if>
			</div>
		</div>
	</div>
</div>

<c:if test="${ not empty sessionScope.userEmail }">
	<div align="center" style="width:100%;">
		<div align="center" style="width:80%;">
			<hr>
				<div id="likeDiv">
					<c:if test="${ likeCheck eq 0 }">
						<div style="display:flex; justify-content:center; align-items:center;" class="likeAdd" onclick="likeAdd('${ peopleInfo.peopleId }');">
							<img style="width:16px;" src="/resources/img/like.png"> <span style="padding-left:7px;">좋아요 ${ peopleInfo.likeNum }명이 이 인물을 좋아합니다.</span>
						</div>
					</c:if>
					<c:if test="${ likeCheck eq 1 }">
						<div style="display:flex; justify-content:center; align-items:center;" class="likeCancel" onclick="likeCancel('${ peopleInfo.peopleId }');">
							<img style="width:16px;" src="/resources/img/likeColor.png"> <span style="padding-left:7px;">좋아요 ${ peopleInfo.likeNum }명이 이 인물을 좋아합니다.</span>
						</div>
					</c:if>
				</div>
			<hr>
		</div>
	</div>
</c:if>

<c:if test="${ empty sessionScope.userEmail }">
	<div align="center" style="width:100%;">
		<div align="center" style="width:80%;">
			<hr>
				<div id="likeDiv">
					<div style="display:flex; justify-content:center; align-items:center;" class="likeAdd" onclick="unregisterLickClick();">
						<img style="width:16px;" src="/resources/img/like.png"> <span style="padding-left:7px;">좋아요 ${ peopleInfo.likeNum }명이 이 인물을 좋아합니다.</span>
					</div>
				</div>
			<hr>
		</div>
	</div>
</c:if>
<br>
<% 
	int cnt = 0; 
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">영화/출연작품</span></div>
		<div style="display:flex; position:relative">
		
			<button id="movieInfoLeftBtn" class="leftBtn" onclick="movieInfoLeftBtn();">◀</button>
			
			<c:forEach var="list" items="${ movieInfo }">
			<% 
				if(cnt < 5){ 
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="movieInfo" style="all:unset; cursor:pointer; display:;">
				<div style="padding-right:10px;">
					<c:if test="${ list.posterUrl ne 'nan' }">
						<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					</c:if>
					<c:if test="${ list.posterUrl eq 'nan' }">
						<div style="width:${imgWidth}px; height:${ imgHeight }px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
					</c:if>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 15 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 15) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 15 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<c:set var="gradeNum" value="${ list.gradeAvg } + ''"/>
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ gradeNum.substring(0,3) }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ gradeNum.substring(0,3) }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
			}else{
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="movieInfo" style="all:unset; cursor:pointer; display:none;">
				<div style="padding-right:10px;">
					<c:if test="${ list.posterUrl ne 'nan' }">
						<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					</c:if>
					<c:if test="${ list.posterUrl eq 'nan' }">
						<div style="width:${imgWidth}px; height:${ imgHeight }px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
					</c:if>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
				}
			cnt++;
			%>
				
			</c:forEach>
			<% if(cnt > 5){ %>
				<button id="movieInfoRightBtn" class="rightBtn2" type="button" onclick="movieInfoRightBtn('<%=cnt%>');">▶</button>
			<% } %>
		</div>
	</div>
</div>

<script src="/resources/js/common.js"></script>
<script src="/resources/js/people_info.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>