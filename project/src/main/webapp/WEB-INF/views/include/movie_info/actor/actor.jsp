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
	<c:if test="${ not empty peopleInfo }">
		<br><br><br>
		<div align="center" style="width:100%;">
			<div align="center" style="width:80%;">
            	<div align="left" style="width:100%; padding-bottom:10px;">
            		<span style="font-size:30px; font-weight:bold;">Actors</span>
            	</div>
               	<c:if test="${ (peopleInfo.end+1) % 4 eq 0 }">
              		<c:set var="pageNum" value="${ (peopleInfo.end+1) / 4 }" />
               	</c:if>
               	<c:if test="${ (peopleInfo.end+1) % 4 ne 0 }">
              		<c:set var="pageNum" value="${ ((peopleInfo.end+1) / 4) + 1 }" />
               	</c:if>
               	<c:forEach var="i" begin="1" end="${ pageNum }" step="1">
              		<div align="left" style="display:flex; padding-bottom:20px;">
                  		
                  		<!-- cast가 존재하는 경우 -->
                 		<%@ include file="./actor_cast_exist.jsp" %>
                     	
                     	<!-- cast가 없는 경우 -->
                     	<%@ include file="./actor_not_cast.jsp" %>
					</div>
				</c:forEach>
			</div>
		</div>
	</c:if>
</body>
</html>