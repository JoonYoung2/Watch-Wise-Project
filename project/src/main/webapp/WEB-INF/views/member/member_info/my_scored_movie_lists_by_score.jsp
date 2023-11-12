<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:0.5px solid #ccc; margin:0px;">

		<c:choose>
			<c:when test="${score == 'listFive'}">
				<c:set var="score" value="5.0"/>
			</c:when>
		    <c:when test="${score == 'listFourPointFive'}">
		        <c:set var="score" value="4.5"/>
		    </c:when>
		    <c:when test="${score == 'listFour'}">
		        <c:set var="score" value="4.0"/>
		    </c:when>
		    <c:when test="${score == 'listThreePointFive'}">
		        <c:set var="score" value="3.5"/>
		    </c:when>
		    <c:when test="${score == 'listThree'}">
		        <c:set var="score" value="3.0"/>
		    </c:when>
		    <c:when test="${score == 'listTwoPointFive'}">
		        <c:set var="score" value="2.5"/>
		    </c:when>
		    <c:when test="${score == 'listTwo'}">
		        <c:set var="score" value="2.0"/>
		    </c:when>
		    <c:when test="${score == 'listOnePointFive'}">
		        <c:set var="score" value="1.5"/>
		    </c:when>
		    <c:when test="${score == 'listOne'}">
		        <c:set var="score" value="1.0"/>
		    </c:when>
		    <c:when test="${score == 'listZeroPointFive'}">
		        <c:set var="score" value="0.5"/>
		    </c:when>
		</c:choose>

<div style="margin-left:20px;">
	<a href="/userMyScoredMovieList">
		<img src="resources/img/back.png" style="width:20px; margin-top:20px;">
	</a>
	<div style="display:flex;">
		<div>
			<h3>${score}점 준 영화</h3>
		</div>
		<div style="margin-top:20px; margin-left:10px; color: rgb(160, 160, 160);">
			${fn:length(listScore)}
		</div>
	</div>
</div>
<div>
	<table style="width:100%;" >
		<tr>
			<c:forEach var="i" begin="1" end="10">
            	<td style="width:160px; height:0px; text-align:center;"></td>
        	</c:forEach>
		</tr>
	<c:set var="cnt" value="0"/>
		<tr>
			<c:forEach var="list" items="${listScore }">
				<c:if test="${cnt%10==0 }">
					</tr>
					<tr>
				</c:if>
			
			<td style="width:160px; height:280px; text-align:center;">
				<div style="dispaly:flex;">
					<div>
						<a href="/movieInfo?movieId=${ list.movieInfoDto.movieId }">
							<img style="width:auto; height:200px;" src="${list.movieInfoDto.posterUrl}">
						</a>
					</div>
					<div>
						${list.movieInfoDto.movieNm}
					</div>
				</div>
			</td>
			<c:set var="cnt" value="${cnt+1 }"/>
		</c:forEach>	
		</tr>
	</table>
</div>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/member_info.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>