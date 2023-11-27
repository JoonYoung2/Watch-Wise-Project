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
			<div align="left" class="title-div"><span class="title-font">일간 Best 10</span></div>
			<div class="movie-div">
			
				<button id="dailyLeftBtn" class="leftBtn" type="button" onclick="dailyLeftBtn();">◀</button>
				
				<c:forEach var="list" items="${ daily }">
				<% if(cnt < 5){ %>
				<a class="dailyTop" href="/movieInfo?movieId=${ list.movieId }" style="all:unset; cursor:pointer; display:;">
					<div class="movie-top-location">
						<div class="rankDiv"><%= rankCnt++ %></div>
						<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
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
				}else{
				%>
				<a class="dailyTop" href="/movieInfo?movieId=${ list.movieId }" style="all:unset; cursor:pointer; display:none;">
					<div class="movie-top-location">
						<div class="rankDiv"><%= rankCnt++ %></div>
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
				<button id="dailyRightBtn" class="rightBtn" type="button" onclick="dailyRightBtn();">▶</button>
			</div>
		</div>
	</div>
</body>
</html>