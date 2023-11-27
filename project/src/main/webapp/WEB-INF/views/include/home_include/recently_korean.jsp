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
	<div align="center" class="start-page">
		<div align="center" class="page-width">
			<div align="left" class="title-div"><span class="title-font">최근 개봉된 한국 영화</span></div>
			<div class="movie-div">
			
				<button id="recentlyKoLeftBtn" class="leftBtn" onclick="recentlyKoLeftBtn();">◀</button>
				
				<c:forEach var="list" items="${ recentlyKo }">
				<% 
					if(cnt < 5){ 
				%>
				<a href="/movieInfo?movieId=${ list.movieId }" class="recentlyKo" style="all:unset; cursor:pointer; display:;">
					<div class="movie-location">
						<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
						<div align="left">
							<c:if test="${ list.movieNm.length() > 15 }">
								<span class="movie-title-font">${ list.movieNm.substring(0, 15) }...</span><br>	
							</c:if>
							<c:if test="${ list.movieNm.length() <= 15 }">
								<span class="movie-title-font">${ list.movieNm }</span><br>
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
				<a href="/movieInfo?movieId=${ list.movieId }" class="recentlyKo" style="all:unset; cursor:pointer; display:none;">
					<div class="movie-location">
						<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
						<div align="left">
							<c:if test="${ list.movieNm.length() > 18 }">
								<span class="movie-title-font">${ list.movieNm.substring(0, 18) }...</span><br>	
							</c:if>
							<c:if test="${ list.movieNm.length() <= 18 }">
								<span class="movie-title-font">${ list.movieNm }</span><br>
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
					}
				cnt++;
				%>
					
				</c:forEach>
				<% if(cnt > 5){ %>
				<button id="recentlyKoRightBtn" class="rightBtn" type="button" onclick="recentlyKoRightBtn('<%=cnt%>');">▶</button>
				<% } %>
			</div>
		</div>
	</div>
</body>
</html>