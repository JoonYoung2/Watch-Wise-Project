<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<title>Insert title here</title>
</head>
<body>
	<div align="center" style="width:100%;">
		<div align="center" style="width:80%; display:flex;">
			<div align="center" style="width:50%;">
				<img style="width:40%;" src="${ movieInfo.posterUrl[0] }">
			</div>
			<div align="left" style="width:50%;">
			<br><br><br>
				<span style="font-size:40px; font-weight:bold;">${ movieInfo.movieNm }</span><br><br>
				<c:if test="${ movieInfo.movieNmEn ne 'nan' }">
					<span style="font-size:18px;">${ movieInfo.movieNmEn }</span><br>
				</c:if>
				<c:if test="${ movieInfo.openDt ne 'nan' }">
					<span style="font-size:18px;">${ movieInfo.openDt.substring(0, 4) }</span>
				</c:if>
				<c:if test="${ movieInfo.genreNm ne 'nan' }">
					・ <span style="font-size:18px;">${ movieInfo.genreNm.replaceAll(",", "/") }</span>
				</c:if>
				<c:if test="${ movieInfo.nations ne 'nan' }">
					・ <span style="font-size:18px;">${ movieInfo.nations.replaceAll(",", "/") }</span>
				</c:if>
				<br>
				<span style="font-size:18px;">${ movieInfo.showTime }</span>
				<c:if test="${ movieInfo.watchGradeNm ne 'nan' }">
					・ <span style="font-size:18px;">${ movieInfo.watchGradeNm.replaceAll(",", "/") }</span>
				</c:if>
				<br>
				<c:if test="${ movieInfo.openDt ne 'nan' }">
					<span style="font-size:18px;">개봉일 : ${ movieInfo.openDt.substring(0, 4) }년 ${ movieInfo.openDt.substring(4, 6) }월 ${ movieInfo.openDt.substring(6, 8) }일</span>
				</c:if>
			</div>
		</div>
	</div>
	
	
	
	<c:if test="${ movieInfo.posterUrl[0] ne 'nan' }">
		<br><br><br>
		<% int cnt = 0; %>
		<div align="center" style="width:100%;">
			<div align="center" style="width:80%;">
				<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">Posters</span></div>
				<div style="display:flex; position:relative">
				
					<button id="postersLeftBtn" class="leftBtn" type="button" onclick="postersLeftBtn();">◀</button>
					<c:forEach var="url" items="${ movieInfo.posterUrl }">
					<% if(cnt < 5){ %>
						<div class="posters" style="display:; padding-right:10px; position:relative;">
							<img style="width:300px; height:428.16px;" src="${ url }">
						</div>
					<% }else{ %>
						<div class="posters" style="display:none; padding-right:10px; position:relative;">
							<img style="width:300px; height:428.16px;" src="${ url }">
						</div>
					<% } cnt++; %>
					</c:forEach>
					<% if(cnt > 5){ %>
						<button id="postersRightBtn" class="rightBtn" type="button" onclick="postersRightBtn('<%=cnt%>');">▶</button>
					<% } %>
				</div>
			</div>
		</div>
	</c:if>
	아이디 : ${ movieInfo.movieId }<br>
	영화명 : ${ movieInfo.movieNm }<br>
	영어영화명 : ${ movieInfo.movieNmEn }<br>
	제작년도 : ${ movieInfo.prdtYear }<br>
	개봉일 : ${ movieInfo.openDt }<br>
	유형 : ${ movieInfo.typeNm }<br>
	제작국가 : ${ movieInfo.nations }<br>
	장르 : ${ movieInfo.genreNm }<br>
	
		포스터URL : 
		<c:forEach var="url" items="${ movieInfo.posterUrl }">
			<img src="${ url }">
		</c:forEach>
		<br>
	
	상영시간 : ${ movieInfo.showTime }<br>
	
		출연진 : 
		<c:forEach var="actor" items="${ movieInfo.actors }">
			${ actor },
		</c:forEach>
		<br>	
	
	
		역할 : 
		<c:forEach var="cast" items="${ movieInfo.cast }">
			${ cast },
		</c:forEach>
		<br>	
	연령제한 : ${ movieInfo.watchGradeNm }<br>
<script src="/resources/js/movie_info.js"></script>
</body>
</html>