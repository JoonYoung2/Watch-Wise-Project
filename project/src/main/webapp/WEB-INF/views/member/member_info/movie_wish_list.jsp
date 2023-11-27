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
	<div style="display:flex;">
		<div>
			<h3>보고싶은 영화</h3>
		</div>
		<div style="margin-top:20px; margin-left:10px; color: rgb(160, 160, 160);">
			${fn:length(wishListDto)}
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
			<c:forEach var="list" items="${wishListDto }">
				<c:if test="${cnt%10==0 }">
					</tr>
					<tr>
				</c:if>
			
			<td style="width:160px; height:250px; text-align:center;">
				<div style="dispaly:flex;text-align:center;">
					<div>
						<a href="/movieInfo?movieId=${ list.movieId }">
							<img style="width:133.33px; height:200px;" src="${list.moviePoster}">
						</a>
					</div>
					<div align="center" style="width:150px; overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">
						<b>${list.movieNm}</b>
					</div>
				</div>
			</td>
			<c:set var="cnt" value="${cnt+1 }"/>
		</c:forEach>	
		</tr>
	</table>
</div>
</body>
</html>