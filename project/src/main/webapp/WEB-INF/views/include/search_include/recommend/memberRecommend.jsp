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
	<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">회원님만을 위한 추천영화</span></div>
		<div style="display:flex; position:relative">
					
			<button id="memberCommendLeftBtn" class="leftBtn" onclick="memberCommendLeftBtn();">◀</button>
						
						
			<c:forEach var="list" items="${ memberCommend }">
			<% 
				if(cnt < 5){ 
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="memberCommend" style="all:unset; cursor:pointer; display:;">
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
			<a href="/movieInfo?movieId=${ list.movieId }" class="memberCommend" style="all:unset; cursor:pointer; display:none;">
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
				<button id="memberCommendRightBtn" class="rightBtn" type="button" onclick="memberCommendRightBtn('<%=cnt%>');">▶</button>
			<% } %>
		</div>
</body>
</html>