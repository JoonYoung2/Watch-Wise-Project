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
		    		<!-------------------------------------------------------- 평점 END ----------------------------------------------------------->
		   		</div>
		   	</div>
		   	
		   	<div style="width:9%;"></div>
		   	
      		<div style="width:56%">
      			<!-- 영화 제목 -->
      			<%@ include file="./header_title.jsp" %>
      		</div>
   		</div>
   	</div>
</body>
</html>