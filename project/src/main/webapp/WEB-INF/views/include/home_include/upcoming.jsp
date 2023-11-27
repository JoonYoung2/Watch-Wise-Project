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
			<div align="left" class="title-div"><span class="title-font">Upcoming Movies</span></div>
			<div class="movie-div">
			
				<button id="upcomingLeftBtn" class="leftBtn" onclick="upcomingLeftBtn();">◀</button>
				
				<c:forEach var="list" items="${ upcoming }">
				<% 
					if(cnt < 5){ 
				%>
				<a href="/movieInfo?movieId=${ list.movieId }" class="upcoming" style="all:unset; cursor:pointer; display:;">
					<div class="movie-location">
						<c:if test="${ list.posterUrl ne 'nan' }">
							<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
						</c:if>
						<c:if test="${ list.posterUrl eq 'nan' }">
							<div style="width:${imgWidth}px; height:${ imgHeight }px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
						</c:if>
						<div align="left">
							<c:if test="${ list.movieNm.length() > 15 }">
								<span class="movie-title-font">${ list.movieNm.substring(0, 15) }...</span><br>	
							</c:if>
							<c:if test="${ list.movieNm.length() <= 15 }">
								<span class="movie-title-font">${ list.movieNm }</span><br>
							</c:if>
							${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
							<span style="color:blue;">D-${ list.getDDay() }</span>
						</div>
					</div>
				</a>
				<%
				}else{
				%>
				<a href="/movieInfo?movieId=${ list.movieId }" class="upcoming" style="all:unset; cursor:pointer; display:none;">
					<div class="movie-location">
						<c:if test="${ list.posterUrl ne 'nan' }">
							<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
						</c:if>
						<c:if test="${ list.posterUrl eq 'nan' }">
							<div style="width:${imgWidth}px; height:${ imgHeight }px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
						</c:if>
						<div align="left">
							<c:if test="${ list.movieNm.length() > 18 }">
								<span class="movie-title-font">${ list.movieNm.substring(0, 18) }...</span><br>	
							</c:if>
							<c:if test="${ list.movieNm.length() <= 18 }">
								<span class="movie-title-font">${ list.movieNm }</span><br>
							</c:if>
							${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
							<span style="color:blue;">D-${ list.getDDay() }</span>
						</div>
					</div>
				</a>
				<%
					}
				cnt++;
				%>
					
				</c:forEach>
				<% if(cnt > 5){ %>
				<button id="upcomingRightBtn" class="rightBtn" type="button" onclick="upcomingRightBtn('<%=cnt%>');">▶</button>
				<% } %>
			</div>
		</div>
	</div>
</body>
</html>