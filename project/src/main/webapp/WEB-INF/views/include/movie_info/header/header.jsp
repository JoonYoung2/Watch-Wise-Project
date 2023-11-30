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
	<br><br><br><br>
   	<c:if test="${not empty msg }">
      <script>
         alert('${msg}');
      </script>
   	</c:if>
   	<div align="center" class="width-100-per">
   		<div class="width-80-per">
   			<hr style="border:1px solid rgba(0, 0, 0, 0.1)">
   		</div>
   	</div>

	<div align="center" class="width-100-per">
      	<div align="center" class="width-80-per display-flex">
      	
      		<div style="width:5%;"></div>
      		
   			<div align="center" style="width:30%; text-align:center;">
		   		<div style="text-align:center; display:flex; justify-content: center; align-items:center;">
		   			<!-- 위시리스트 저장 기능 -->
		    		<%@ include file="./header_wish.jsp" %>
		    		
		    		<!-- 코멘트 작성 기능 -->
		    		<%@ include file="./header_comment.jsp" %>
		    		
		    		
					<!-- 평점 기능 -->
		    		<%@ include file="./header_rating.jsp" %>
		   		</div>
		   	</div>
		   	
		   	<div style="width:9%;"></div>
		   	
      		<div style="width:56%">
      			<!-- 영화 제목 -->
      			<%@ include file="./header_title.jsp" %>
      		</div>
   		</div>
   	</div>
   	
   	<div align="center" style="width:100%;">
   		<div style="width:80%;">
   			<hr style="border:1px solid rgba(0, 0, 0, 0.1)">
   		</div>
   	</div>
</body>
</html>