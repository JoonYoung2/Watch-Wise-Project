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

<div style="margin-left:20px;">
	<a href="/memberInfo">
		<img src="resources/img/back.png" style="width:20px; margin-top:20px;">
	</a>
	<div align="center" style="margin-top:0px; margin-bottom:40px;">
		<h3>평가한 영화 목록</h3>
	</div>
</div>

<div>
	<c:forEach var="entry" items="${lists}">
		<c:choose>
			<c:when test="${entry.key == 'listFive'}">
				<c:set var="score" value="5.0"/>
			</c:when>
		    <c:when test="${entry.key == 'listFourPointFive'}">
		        <c:set var="score" value="4.5"/>
		    </c:when>
		    <c:when test="${entry.key == 'listFour'}">
		        <c:set var="score" value="4.0"/>
		    </c:when>
		    <c:when test="${entry.key == 'listThreePointFive'}">
		        <c:set var="score" value="3.5"/>
		    </c:when>
		    <c:when test="${entry.key == 'listThree'}">
		        <c:set var="score" value="3.0"/>
		    </c:when>
		    <c:when test="${entry.key == 'listTwoPointFive'}">
		        <c:set var="score" value="2.5"/>
		    </c:when>
		    <c:when test="${entry.key == 'listTwo'}">
		        <c:set var="score" value="2.0"/>
		    </c:when>
		    <c:when test="${entry.key == 'listOnePointFive'}">
		        <c:set var="score" value="1.5"/>
		    </c:when>
		    <c:when test="${entry.key == 'listOne'}">
		        <c:set var="score" value="1.0"/>
		    </c:when>
		    <c:when test="${entry.key == 'listZeroPointFive'}">
		        <c:set var="score" value="0.5"/>
		    </c:when>
		</c:choose>

		<table style="width:100%;">
			<tr>
				<th colspan="13" >
				
					<div style="margin-left:20px; margin-right:20px; display: flex; justify-content: space-between; align-items: center;">
    					<div style="display: flex;">
        					<h2 style="font-size:19px; margin-top:8px; margin-bottom:8px; margin-right: 10px;">
            					${score} 평가함
        					</h2>
        					<span style="font-size:15px; margin-top:10px; color: rgb(160, 160, 160);">
            					${fn:length(entry.value)}
        					</span>
    					</div>
    					<div style="margin:12px 0px;">
        					<a href="/viewMoviesIn${entry.key}" style="color:rgb(255, 47, 110); text-decoration: none;">
        						더보기
        					</a>
    					</div>
					</div>
				</th>
			</tr>
			<tr>
			<c:choose>
				<c:when test="${empty entry.value }">
					<td>
					<div align="center" style="height:200px;">
						<img src="resources/img/noResult.png" style="height:80%;">
					</div>
					</td>
				</c:when>
				<c:otherwise>
				<td style="width:10px;">
					&nbsp
				</td>
				<c:set var="cnt" value="0"/>
				<c:forEach var="item" items="${entry.value}">
				<c:if test="${cnt < 10 }">
					<td style="width:160px;">
						<div style="dispaly:flex;">
							<div>
								<a href="/movieInfo?movieId=${item.movieInfoDto.movieId }">
									<img style="width:auto; height:200px;" src="${item.movieInfoDto.posterUrl}">
								</a>
							</div>
							<div>
								${item.movieInfoDto.movieNm}
							</div>
						</div>
					</td>
				</c:if>
				<c:set var="cnt" value="${cnt + 1}"/>
				</c:forEach>
				<td>
				&nbsp	
				</td>
				<c:if test="${cnt >9 }">
				<td>
					<div style="text-align:right;">
						<a href="/viewMoviesIn${entry.key}">
							<img style="width:70px;margin-right:70px;" src="resources/img/more.png">
						</a>
					</div>
				</td>
				</c:if>
				</c:otherwise>
			</c:choose>
			</tr>
		</table>
		<br>
		<hr style="border:1px solid #ccc;">
		<br>
		
	</c:forEach>
</div>


<!-- 
	    scoreLists.put("listFive", listFive); //5.0
	    scoreLists.put("listFourPointFive", listFourPointFive); //4.5
	    scoreLists.put("listFour", listFour); //4.0
	    scoreLists.put("listThreePointFive", listThreePointFive); //3.5
	    scoreLists.put("listThree", listThree); //3.0
	    scoreLists.put("listTwoPointFive", listTwoPointFive); //2.5
	    scoreLists.put("listTwo", listTwo); //2.0
	    scoreLists.put("listOnePointFive", listOnePointFive); //1.5
	    scoreLists.put("listOne", listOne); //1.0
	    scoreLists.put("listZeroPointFive", listZeroPointFive); //0.5
 -->
	
<script src="/resources/js/common.js"></script>
<script src="/resources/js/member_info.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>